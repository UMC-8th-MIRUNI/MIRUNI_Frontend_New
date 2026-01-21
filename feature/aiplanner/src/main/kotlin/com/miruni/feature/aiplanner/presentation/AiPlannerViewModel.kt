package com.miruni.feature.aiplanner.presentation

import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
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
import com.miruni.feature.aiplanner.presentation.model.AiPlanUiModel
import com.miruni.feature.aiplanner.presentation.model.PlanningFormItemUiModel
import com.miruni.feature.aiplanner.presentation.model.ScheduleSource
import com.miruni.feature.aiplanner.presentation.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
            val plansDeferred = async { mainRepository.getAiPlans() }
            val remainDeferred = async { mainRepository.getRemain() }

            val plansResult = plansDeferred.await()
            val remainResult = remainDeferred.await()

            setState { copy(isMainLoading = false) }

            // 결과 처리
            if (plansResult is DataResult.Success && remainResult is DataResult.Success) {
                setState {
                    copy(
                        aiPlans = plansResult.data,
                        remain = remainResult.data
                    )
                }
            } else {
                val error = (plansResult as? DataResult.Error)?.error
                    ?: (remainResult as? DataResult.Error)?.error

                showErrorMessage(error)
            }

            setState {
                copy(
                    aiPlans = aiPlans,
                    remain = remain,
                    isMainLoading = false
                )
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

            val title = (forms["what"]?.value as? PlanInput.Text)?.text.orEmpty()
            val deadline = (forms["until"]?.value as? PlanInput.Date)?.let { "${it.endDate}" }

            // API 호출
            val result = planningRepository.postAiPlan(
                title = title,
                timePeriod = PlanTimePeriod.fromUi(timePeriod),
                taskRange = taskRange,
                priority = PlanPriority.fromUi(priority),
                detailRequest = extra
            )
            setState { copy(isPlanningLoading = false) }

            // 결과 처리
            when (result) {
                is DataResult.Success -> {
                    setEffect { AiPlannerContract.Effect.Navigation.ToLoading }
                    setState { copy(plan = result.data.first().toUiModel()) }
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
        planId: Long?
    ) {
        viewModelScope.launch {
            when (from) {
                ScheduleSource.FROM_MAIN -> { // 메인에서 오면
                    requireNotNull(planId)

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
                }
                ScheduleSource.FROM_LOADING -> { // 로딩 화면에서 오면
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

            // API 호출
            val requestPlan = currentPlan.toDomain().copy(
                title = event.title,
                deadline = event.deadline,
                taskRange = event.taskRange,
                priority = event.priority.let { PlanPriority.fromUi(it) },
                aiPlans = event.aiPlans.map { it.toDomain() }
            )
            setState { copy(isEditMode = false) }

            val result = scheduleRepository.updateScheduleTable(requestPlan)

            // 결과 처리
            when (result) {
                is DataResult.Success -> {
                    setState { copy(plan = result.data.toUiModel()) }
                    setEffect { AiPlannerContract.Effect.showToast("일정이 수정되었습니다.") }
                }

                is DataResult.Error -> {
                    showErrorMessage(result.error)
                    setState { copy(isEditMode = true) }
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
                    setEffect { AiPlannerContract.Effect.showToast("일정이 삭제되었습니다.") }
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

                    setState {
                        copy(plan = currentPlan.copy(aiPlans = updatedAiPlans))
                    }
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

        setEffect { AiPlannerContract.Effect.showToast(message) }
    }
}