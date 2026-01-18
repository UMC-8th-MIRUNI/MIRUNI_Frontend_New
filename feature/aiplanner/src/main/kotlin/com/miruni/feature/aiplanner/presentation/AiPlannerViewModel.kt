package com.miruni.feature.aiplanner.presentation

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miruni.core.common.BaseViewModel
import com.miruni.feature.aiplanner.domain.repository.MainRepository
import com.miruni.core.domain.onboarding.OnboardingRepository
import com.miruni.core.domain.onboarding.OnboardingKey
import com.miruni.feature.aiplanner.domain.model.PlanInput
import com.miruni.feature.aiplanner.domain.repository.PlanningRepository
import com.miruni.feature.aiplanner.presentation.model.AiPlanUiModel
import com.miruni.feature.aiplanner.presentation.model.PlanUiModel
import com.miruni.feature.aiplanner.presentation.model.PlanningFormItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AiPlannerViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
    private val mainRepository: MainRepository,
    private val planningRepository: PlanningRepository
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
            AiPlannerContract.Event.ClickDelete -> deletePlan()
            AiPlannerContract.Event.ClickEdit -> onEdit()
            AiPlannerContract.Event.ClickMenu -> showMenu()
            is AiPlannerContract.Event.ClickCompleteEdit -> updatePlan(event)
        }
    }

    init {
        loadAiPlanner()
        observeValues()
        loadDummyAiPlan()
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

            loadAiPlanner()
        }
    }

    /**
     * AI 플래너 메인: AI 플래너 메인 스크린에 출력할 데이터(AI 일정, 잔여 횟수) 로드
     */
    private fun loadAiPlanner() =
        viewModelScope.launch {
            setState { copy(isMainLoading = true) }

            val aiPlans = mainRepository.getAiPlans()
            val remain = mainRepository.getRemain()
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

            // 현재 State의 forms 데이터를 모아서 API 전송
            val currentState = viewState.value
            val inputs = currentState.forms.associate { it.id to it.value }

            // Validation 체크
            if (inputs.values.any { it == null }) {
                // 필요 시 에러 이펙트
                setState { copy(isPlanningLoading = false) }
                return@launch
            }

            // Repository 호출 (API 연결)
            // val result = planningRepository.submitPlan(inputs)

            // 결과 처리 후 네비게이션 Effect 발생
             setEffect { AiPlannerContract.Effect.Navigation.ToLoading }
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

    /** AI 플래너 일정 쪼개기(데이터 로드) */
    private fun loadDummyAiPlan() {
        viewModelScope.launch {
            // API 연동
            val plan = PlanUiModel(
                planId = 1,
                title = "기말고사 준비",
                deadline = "2026-01-25",
                taskRange = "1장부터 3장까지",
                priority = "중",
                aiPlans = listOf(
                    AiPlanUiModel(
                        aiPlanId = 1,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 2,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 3,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 4,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 5,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 6,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 7,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 8,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 9,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 10,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 11,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 12,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 13,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 14,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 15,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 16,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 17,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 18,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 19,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 20,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 21,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),AiPlanUiModel(
                        aiPlanId = 22,
                        scheduledDate = "2026-01-20",
                        startTime = "10:00",
                        endTime = "11:00",
                        content = "자료정리 및 요약",
                        expectedDuration = 120
                    ),
                )
            )

            setState { copy(plan = plan) }
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
            setState { copy(isEditMode = false) }

            // 일정 수정 API - 요청 전송
            // 일정 수정 API - 응답 받기
            // val response = repository.updatePlan()

            setState {
                copy(
                    plan = plan?.copy(
                        title = event.title,
                        deadline = event.deadline,
                        taskRange = event.taskRange,
                        priority = event.priority,
                        aiPlans = event.aiPlans
                    )
                )
            }
        }
    }

    /**
     * AI 플래너 스케줄 표: 일정 삭제
     */
    private fun deletePlan() {
        // 일정 삭제 API - 요청
        setEffect { AiPlannerContract.Effect.Navigation.ToMain }
    }
}