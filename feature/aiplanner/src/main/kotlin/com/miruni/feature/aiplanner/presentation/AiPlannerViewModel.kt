package com.miruni.feature.aiplanner.presentation

import androidx.lifecycle.viewModelScope
import com.miruni.feature.aiplanner.common.BaseViewModel
import com.miruni.feature.aiplanner.domain.repository.MainRepository
import com.miruni.core.domain.onboarding.OnboardingRepository
import com.miruni.core.domain.onboarding.OnboardingKey
import com.miruni.feature.aiplanner.domain.model.PlanInput
import com.miruni.feature.aiplanner.domain.repository.PlanningRepository
import com.miruni.feature.aiplanner.presentation.model.PlanningFormItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
            Triple("howMuch", "'얼만큼' 하실건가요?", "숫자 입력"),
            Triple("priority", "'우선순위'를 알려주세요", "우선순위"),
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
            AiPlannerContract.Event.CompleteOnboarding ->
                completeOnboarding()
            is AiPlannerContract.Event.InputText ->
                save(event.id, PlanInput.Text(event.text))
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
            is AiPlannerContract.Event.SelectOption ->
                save(event.id, PlanInput.Option(event.option))
        }
    }

    init {
        loadAiPlanner()
        observeValues()
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
            setState { copy(isLoading = true) }

            val aiPlans = mainRepository.getAiPlans()
            val remain = mainRepository.getRemain()
            setState {
                copy(
                    aiPlans = aiPlans,
                    remain = remain,
                    isLoading = false
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
}