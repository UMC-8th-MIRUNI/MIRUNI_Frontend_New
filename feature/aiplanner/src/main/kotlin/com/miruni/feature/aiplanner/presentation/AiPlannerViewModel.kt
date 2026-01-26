package com.miruni.feature.aiplanner.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
import com.miruni.core.common.DateTimeHelper
import com.miruni.feature.aiplanner.domain.repository.MainRepository
import com.miruni.core.domain.onboarding.OnboardingRepository
import com.miruni.core.domain.onboarding.OnboardingKey
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.aiplanner.domain.model.PlanInput
import com.miruni.feature.aiplanner.domain.model.PlanPriority
import com.miruni.feature.aiplanner.domain.model.PlanTimePeriod
import com.miruni.feature.aiplanner.domain.repository.PlanningRepository
import com.miruni.feature.aiplanner.domain.repository.ScheduleRepository
import com.miruni.feature.aiplanner.presentation.model.PlanningFormItemUiModel
import com.miruni.feature.aiplanner.presentation.model.ScheduleSource
import com.miruni.feature.aiplanner.presentation.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AiPlannerViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val mainRepository: MainRepository,
    private val planningRepository: PlanningRepository,
    private val scheduleRepository: ScheduleRepository
) : BaseViewModel<AiPlannerContract.Event, AiPlannerContract.State, AiPlannerContract.Effect>() {

    override fun setInitialState(): AiPlannerContract.State {
        val formDefinitions = listOf(
            Triple("what", "'어떤 일'을 하실건가요?", "텍스트를 입력하세요"),
            Triple("until", "'언제까지' 하실건가요?", "날짜 선택"),
            Triple("when", "'언제' 하실건가요?", "옵션 선택"),
            Triple("howMuch", "'얼만큼' 하실건가요?", "내용을 입력하세요"),
            Triple("priority", "'우선순위'를 알려주세요", "상/중/하"),
            Triple("extra", "'추가 요청사항'을 알려주세요", "내용을 입력하세요")
        )

        return AiPlannerContract.State(
            forms = formDefinitions.mapIndexed { idx, def ->
                PlanningFormItemUiModel(
                    id = def.first,
                    title = def.second,
                    placeholder = def.third,
                    value = null,
                    visible = idx == 0
                )
            }
        )
    }

    override fun handleEvents(event: AiPlannerContract.Event) {
        when (event) {
            AiPlannerContract.Event.ClickBack -> onBack() // 뒤로 가기
            AiPlannerContract.Event.OnMain -> toMain()
            /** AI 플래너 온보딩 */
            AiPlannerContract.Event.CompleteOnboarding -> completeOnboarding() // 온보딩 완료

            /** AI 플래너 사용자 입력 */
            AiPlannerContract.Event.ClearForm -> clearForm()
            AiPlannerContract.Event.ClickSubmit -> submitPlan() // 사용자 입력 제출
            is AiPlannerContract.Event.InputText -> save(event.id, PlanInput.Text(event.text))
            is AiPlannerContract.Event.SelectDate -> {
                save(
                    event.id,
                    PlanInput.Date(
                        event.startDate,
                        event.endDate,
                        event.startTime,
                        event.endTime
                    )
                )
            }
            is AiPlannerContract.Event.SelectOption -> save(event.id, PlanInput.Option(event.option))

            /** AI 플래너 로딩 */
            AiPlannerContract.Event.ShowPlanningLoading -> showPlanningLoading() // 플래닝 로딩 화면 출력
            AiPlannerContract.Event.ClickConfirm -> onConfirm() // 플래닝 로딩 확인 클릭

            /** AI 플래너 스케줄표  */
            AiPlannerContract.Event.ClickDeleteAll -> deletePlan()
            AiPlannerContract.Event.ClickEdit -> onEdit()
            AiPlannerContract.Event.ClickMenu -> showMenu()
            is AiPlannerContract.Event.ClickCompleteEdit -> updatePlan(event)
            is AiPlannerContract.Event.EnterSchedule -> enterSchedule(event.from, event.planId)
            is AiPlannerContract.Event.ClickDeleteItem -> deleteAiPlans(event)
        }
    }

    init {
        loadMain()
        observeValues()
    }

    /**
     * 뒤로 가기 클릭 이벤트
     */
    private fun onBack() {
        setEffect { AiPlannerContract.Effect.PopBack }
    }

    /**
     * 메인으로 가기
     */
    private fun toMain() {
        viewModelScope.launch {
            setEffect { AiPlannerContract.Effect.Navigation.ToMain }
        }
    }

    /**
     * AI 플래너 온보딩: 온보딩 완료 처리
     */
    private fun completeOnboarding() {
        viewModelScope.launch {
            onboardingRepository.completeOnboarding(OnboardingKey.AI_PLANNER)

            loadMain()
        }
    }

    /**
     * AI 플래너 메인: AI 플래너 메인 스크린에 출력할 데이터(AI 일정, 잔여 횟수) 로드
     */
    private fun loadMain() =
        viewModelScope.launch {
            setState { copy(isMainLoading = true) }

            // API 호출
            val result = mainRepository.getAiPlans()
            setState { copy(isMainLoading = false) }

            // 결과 처리
            when (result) {
                is DataResult.Success -> {
                    setState {
                        copy(
                            remain = result.data.remainingAiCnt,
                            aiPlans = result.data.plans
                        )
                    }
                }

                is DataResult.Error -> {
                    showErrorMessage(result.error)
                }
            }
        }

    /**
     * AI 플래너 플래닝: 폼 초기화
     */
    private fun clearForm() {
        viewModelScope.launch {
            planningRepository.clear() // Repository 데이터 삭제

            val clearForms = setInitialState().forms // state의 forms 초기화
            setState { copy(forms = clearForms) }
        }
    }

    /**
     * AI 플래너 플래닝: 사용자가 입력한 폼 관찰
     */
    private fun observeValues() {
        viewModelScope.launch {
            planningRepository.observeValues().collect { map ->
                setState {
                    copy(
                        forms = forms.map { it.copy(value = map[it.id]) }
                    )
                }
            }
        }
    }

    /**
     * AI 플래너 플래닝: 사용자 입력 저장 및 다음 입력 폼 호출
     */
    private fun save(id: String, value: PlanInput) {
        viewModelScope.launch {
            planningRepository.setValue(id, value)
            revealNext(id)
        }
    }

    private fun revealNext(id: String) {
        setState {
            val idx = forms.indexOfFirst { it.id == id }

            if (idx >= 0 && idx < forms.lastIndex) {
                copy(
                    forms = forms.mapIndexed { next, form ->
                        if (next == idx + 1) form.copy(visible = true) else form
                    }
                )
            } else {
                this
            }
        }
    }
    /**
     * AI 플래너 플래닝: 사용자 입력 전송
     */
    private fun submitPlan() {
        viewModelScope.launch {
            setState { copy(isPlanningLoading = true) }

            // 입력값 수집
            val forms = viewState.value.forms.associateBy { it.id }

            val title = (forms["what"]?.value as? PlanInput.Text)
                ?.text
                .orEmpty()

            val dateInput = (forms["until"]?.value as? PlanInput.Date) ?: return@launch
            val startDateTime = DateTimeHelper.toServerDateTime(dateInput.startDate, dateInput.startTime)
            val endDateTime = DateTimeHelper.toServerDateTime(dateInput.endDate, dateInput.endTime)

            val timePeriod = (forms["when"]?.value as? PlanInput.Option)
                ?.option
                ?.let { PlanTimePeriod.fromUi(it) }
                ?: PlanTimePeriod.RANDOM
            val taskRange = (forms["howMuch"]?.value as? PlanInput.Text)
                ?.text
                .orEmpty()
            val priority = (forms["priority"]?.value as? PlanInput.Option)
                ?.option
                ?.let { PlanPriority.fromUi(it) }
                ?: PlanPriority.LOW
            val detailRequest = (forms["extra"]?.value as? PlanInput.Text)
                ?.text
                .orEmpty()

            // API 호출
            val result = planningRepository.postAiPlan(
                title = title,
                startDateTime = startDateTime,
                endDateTime = endDateTime,
                timePeriod = timePeriod,
                taskRange = taskRange,
                priority = priority,
                detailRequest = detailRequest
            )
            setState { copy(isPlanningLoading = false) }

            // 결과 처리
            when (result) {
                is DataResult.Success -> {
                    setEffect { AiPlannerContract.Effect.Navigation.ToLoading }
                    setState { copy(plan = result.data.toUiModel()) }
                    Log.d("Plan/AiPlannerViewModel", "submitPlan: ${result.data.toUiModel()}")
                }
                is DataResult.Error -> {
                    showErrorMessage(result.error)
                }
            }
        }
    }

    /**
     * AI 플래너 로딩: 로딩 화면 출력
     */
    private fun showPlanningLoading() {
        viewModelScope.launch {
            delay(2500)

            setState {
                copy(
                    isPlanningLoading = false,
                    isFinishedPlanningLoading = true
                )
            }
        }
    }

    /**
     * AI 플래너 로딩: 완료 버튼 클릭 (플래닝 -> 스케줄 표)
     */
    private fun onConfirm() {
        setEffect { AiPlannerContract.Effect.Navigation.ToSchedule }
    }

    /** AI 플래너 스케줄표 */
    private fun enterSchedule(
        from: ScheduleSource,
        planId: Int?
    ) {
        viewModelScope.launch {
            if (from == ScheduleSource.FROM_MAIN) { // 메인에서 오면
                if (planId == null) {
                    setEffect { AiPlannerContract.Effect.PopBack }
                    return@launch
                }

                // API 호출
                setState { copy(isPlanningLoading = true) }
                val result = scheduleRepository.getScheduleTable(planId)
                setState { copy(isPlanningLoading = false) }

                // 결괏값 처리
                when (result) {
                    is DataResult.Success -> {
                        setState { copy(plan = result.data.toUiModel()) }
                    }
                    is DataResult.Error -> {
                        showErrorMessage(result.error)
                        setEffect { AiPlannerContract.Effect.PopBack } // 실패 시 뒤로 가기
                    }
                }
            } else { // 로딩에서 온 경우
                if (viewState.value.plan == null) { // plan null이면 메인으로 이동
                    setEffect { AiPlannerContract.Effect.Navigation.ToMain }
                }
            }
        }
    }

    /**
     * AI 플래너 스케줄 표: 메뉴 노출
     */
    private fun showMenu() {
        setState { copy(showMenu = !showMenu) }
    }

    /**
     * AI 플래너 스케줄 표: 수정 모드 전환
     */
    private fun onEdit() {
        setState { copy(isEditMode = true, showMenu = false) }
    }

    /**
     * AI 플래너 스케줄 표: 일정 수정
     */
    private fun updatePlan(event: AiPlannerContract.Event.ClickCompleteEdit) {
        viewModelScope.launch {
            // 현재 보여지는 플랜
            val currentPlan = viewState.value.plan ?: return@launch
            setState { copy(isEditMode = false) }

            // API 호출
            val requestPlan = currentPlan.toDomain().copy(
                planId = currentPlan.planId,
                title = event.title,
                deadline = event.deadline,
                taskRange = event.taskRange,
                priority = event.priority.let { PlanPriority.fromUi(it) },
                aiPlans = event.aiPlans.map { it.toDomain() }
            )

            val result = scheduleRepository.updateScheduleTable(requestPlan)

            // 결과 처리
            when (result) {
                is DataResult.Success -> {
                    setState { copy(plan = result.data.toUiModel()) }
                    setEffect { AiPlannerContract.Effect.ShowToast("일정이 수정되었습니다.") }
                }

                is DataResult.Error -> {
                    showErrorMessage(result.error)
//                    setState { copy(isEditMode = true) } // 실패시 수정 모드 유지
                }
            }
        }
    }

    /**
     * AI 플래너 스케줄 표: 일정 삭제 (전체 삭제)
     */
    private fun deletePlan() {
        viewModelScope.launch {
            // API 호출
            val planId = viewState.value.plan?.planId ?: return@launch
            val result = scheduleRepository.deleteScheduleAll(planId)

            when (result) {
                is DataResult.Success -> {
                    setEffect { AiPlannerContract.Effect.ShowToast("일정이 삭제되었습니다.") }
                    setEffect { AiPlannerContract.Effect.Navigation.ToMain }
                }
                is DataResult.Error -> {
                    showErrorMessage(result.error)
                }
            }

        }
    }

    /**
     * AI 플래너 스케줄 표: 일정 삭제 (개별 삭제)
     */
    private fun deleteAiPlans(event: AiPlannerContract.Event.ClickDeleteItem) {
        viewModelScope.launch {
            val currentPlan = viewState.value.plan ?: return@launch
            val planId = currentPlan.planId ?: return@launch

            // API 호출
            val result = scheduleRepository.deleteScheduleItem(
                planId = planId,
                aiPlanIds = event.aiPlanIds
            )

            when (result) {
                is DataResult.Success -> {
                    val updatedAiPlans = currentPlan.aiPlans.filterNot {
                        event.aiPlanIds.contains(it.aiPlanId)
                    }

                    setState { copy(plan = currentPlan.copy(aiPlans = updatedAiPlans)) }
                    setEffect { AiPlannerContract.Effect.ShowToast("선택한 일정이 삭제되었습니다.") }
                }
                is DataResult.Error -> {
                    showErrorMessage(result.error)
                }
            }
        }
    }

    private fun showErrorMessage(error: DataError?) {
        val message = when(error) {
            is DataError.CustomError -> error.msg
            is DataError.Unknown -> error.errorMessage
            else -> "네트워크 연결을 확인해주세요."
        }

        setEffect { AiPlannerContract.Effect.ShowToast(message) }
    }
}