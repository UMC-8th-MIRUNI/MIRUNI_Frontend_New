package com.miruni.feature.calendar.data.repository

import android.util.Log
import com.miruni.core.common.mapper.toDomainError
import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.calendar.data.api.CalendarApi
import com.miruni.feature.calendar.data.dto.request.PatchPlanRequest
import com.miruni.feature.calendar.data.dto.request.PostPlanFinishRequest
import com.miruni.feature.calendar.data.dto.request.PostPlanRequest
import com.miruni.feature.calendar.domain.model.DailyPlans
import com.miruni.feature.calendar.domain.model.DayInfo
import com.miruni.feature.calendar.domain.model.FinishPlan
import com.miruni.feature.calendar.domain.model.Plan
import com.miruni.feature.calendar.domain.model.PlanDraft
import com.miruni.feature.calendar.domain.model.PlanType
import com.miruni.feature.calendar.domain.repository.CalendarRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarRepositoryImpl @Inject constructor(
  private val api: CalendarApi
) : CalendarRepository {
    override suspend fun postPlanFinish(
        planId: Int,
        planType: PlanType,
        expectedTime: String
    ): DataResult<FinishPlan, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.postPlanFinish(
                planType = planType.server,
                planId = planId,
                request = PostPlanFinishRequest(
                    expectedTime = expectedTime
                )
            )
        }

        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
                    val domainItem = serverResult.toDomain()

                    DataResult.Success(domainItem)
                } else {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }

            is NetworkResult.Failure -> { // 통신 실패
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }

    override suspend fun getPlan(
        planId: Int,
        planType: PlanType
    ): DataResult<Plan, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.getPlan(
                planId = planId,
                planType = planType.server
            )
        }

        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
                    val domainItem = serverResult.toDomain()

                    DataResult.Success(domainItem)
                } else {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }

            is NetworkResult.Failure -> { // 통신 실패
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }

    override suspend fun getExpectedDuration(
        planId: Int,
        planType: PlanType
    ): DataResult<Int, DataError> {
        val networkResult = executeApiRequest {
            api.getExpectedDuration(
                planId = planId,
                planType = planType.server
            )
        }

        return when (networkResult) {
            is NetworkResult.Success -> {
                val response = networkResult.data
                val serverResult = response.result

                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    val domainItem = serverResult.expectedDuration

                    DataResult.Success(domainItem)
                } else {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }
            is NetworkResult.Failure -> {
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }

    override suspend fun getMonthlyPlanCount(
        year: Int,
        month: Int
    ): DataResult<List<DayInfo>, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.getMonthlyPlans(
                year = year,
                month = month
            )
        }

        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
                    val domainItem = serverResult.map { it.toDomain() }

                    DataResult.Success(domainItem)
                } else {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }

            is NetworkResult.Failure -> { // 통신 실패
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }

    override suspend fun getDailyPlans(
        year: Int,
        month: Int,
        day: Int
    ): DataResult<DailyPlans, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.getDailyPlans(
                year = year,
                month = month,
                day = day
            )
        }

        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
                    val domainItem = serverResult.toDomain()

                    DataResult.Success(domainItem)
                } else {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }

            is NetworkResult.Failure -> { // 통신 실패
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }

    override suspend fun deletePlan(basicPlanId: Int): DataResult<Int, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.deletePlan(
                basicPlanId = basicPlanId
            )
        }

        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
                    val domainItem = serverResult.deletedPlanId

                    DataResult.Success(domainItem)
                } else {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }

            is NetworkResult.Failure -> { // 통신 실패
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }

    // 특정 일자 plan 캐시: 날짜 - DailyPlans
    private val _dailyCache: MutableStateFlow<Map<LocalDate, DailyPlans>> = MutableStateFlow(emptyMap())
    private val dailyCache: StateFlow<Map<LocalDate, DailyPlans>> = _dailyCache.asStateFlow()

    // plan 캐시: (planId, planType) - Plan
    private val _planCache: MutableStateFlow<Map<Pair<Int, PlanType>, Plan>> = MutableStateFlow(emptyMap())
    private val planCache: StateFlow<Map<Pair<Int, PlanType>, Plan>> = _planCache.asStateFlow()

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override fun observeDailyPlans(date: LocalDate): Flow<DailyPlans> {
        return dailyCache
            .map { cache ->
                Log.d("PLAN_DEBUG", "7. observeDailyPlans emission: ${cache[date]}")
                cache[date] ?: DailyPlans(unfinishedPlan = emptyList(), finishedPlan = emptyList())
            }
            .distinctUntilChanged()
    }

    override fun observePlan(planId: Int, planType: PlanType): Flow<Plan?> {
        val key = Pair(planId, planType)
        return planCache
            .map { cache -> cache[key] }
            .distinctUntilChanged()
    }

    override suspend fun refreshDailyPlans(date: LocalDate): DataResult<DailyPlans, DataError> {
        val networkResult = executeApiRequest {
            api.getDailyPlans(
                year = date.year,
                month = date.monthValue,
                day = date.dayOfMonth
            )
        }

        return when (networkResult) {
            is NetworkResult.Success -> {
                val response = networkResult.data
                val serverResult = response.result

                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    val domainItem = serverResult.toDomain()

                    // 캐시 업데이트
                    _dailyCache.update { old ->
                        old + (date to domainItem)
                    }

                    ioScope.launch {
                        populatePlanCacheFromDaily(domainItem)
                    }
                    DataResult.Success(domainItem)
                } else {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }

            is NetworkResult.Failure -> {
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }

    override suspend fun refreshPlan(
        planId: Int,
        planType: PlanType
    ): DataResult<Plan, DataError> {
        val planNetworkResult = executeApiRequest {
            api.getPlan(
                planId = planId,
                planType = planType.server
            )
        }

        return when (planNetworkResult) {
            is NetworkResult.Success -> {
                val response = planNetworkResult.data
                val serverResult = response.result

                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    val plan = serverResult.toDomain()

                    // 예상 소요 시간 업데이트 시도
                    val expNetworkResult = executeApiRequest {
                        api.getExpectedDuration(
                            planId = planId,
                            planType = planType.server
                        )
                    }
                    val merged = when (expNetworkResult) {
                        is NetworkResult.Success -> {
                            val expResponse = expNetworkResult.data
                            val expServerResult = expResponse.result

                            if (expResponse.errorCode.isNullOrBlank() && expServerResult != null) {
                                plan.copy(expectedDuration = expServerResult.expectedDuration)
                            } else {
                                plan
                            }
                        }
                        is NetworkResult.Failure -> {
                            plan
                        }
                    }

                    // plan 캐시 업데이트
                    _planCache.update { old ->
                        old + (Pair(planId, planType) to merged)
                    }

                    DataResult.Success(merged)
                } else {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }
            is NetworkResult.Failure -> {
                DataResult.Error(planNetworkResult.error.toDomainError())
            }
        }
    }

    override suspend fun createPlan(
        draft: PlanDraft
    ): DataResult<List<Plan>, DataError> {
        Log.d("PLAN_DEBUG", "4. Repository.createPlan 진입")

        val networkResult = executeApiRequest {
            api.postPlan(
                request = PostPlanRequest(
                    title = draft.title,
                    description = draft.description,
                    startDate = draft.startDate,
                    endDate = draft.endDate,
                    startTime = draft.startTime,
                    endTime = draft.endTime,
                    priority = draft.priority.ui
                )
            )
        }

        Log.d("PLAN_DEBUG", "5. 서버 응답 성공 여부: $networkResult")

        return when (networkResult) {
            is NetworkResult.Success -> {
                val response = networkResult.data
                val serverResult = response.result

                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    val domainList = serverResult.map { it.toDomain() }

                    val date = runCatching {
                        LocalDate.parse(draft.startDate, dateFormatter)
                    }.getOrNull()

                    // 캐시 업데이트
                    if (date != null) {
                        // DailyPlans 캐시 갱신
                        _dailyCache.update { old ->
                            val current = old[date]

                            val updatedDaily = if (current == null) {
                                DailyPlans(
                                    unfinishedPlan = domainList,
                                    finishedPlan = emptyList()
                                )
                            } else {
                                current.copy(
                                    unfinishedPlan = current.unfinishedPlan + domainList
                                )
                            }

                            old + (date to updatedDaily)
                        }
                        // Plan 캐시 갱신
                        _planCache.update { old ->
                            val updated = old.toMutableMap()
                            domainList.forEach { plan ->
                                updated[plan.planId to plan.planType] = plan
                            }
                            updated
                        }

                        // 서버의 최신 목록으로 덮어쓰기 시도
                        // - 한 번 더 해서 UI 안전성 확보 및 서버와의 일치성 확보
                        try {
                            refreshDailyPlans(date)
                        } catch (_: Throwable) {}
                    } else {
                        _planCache.update { old ->
                            val updated = old.toMutableMap()
                            domainList.forEach { plan ->
                                updated[plan.planId to plan.planType] = plan
                            }
                            updated
                        }
                    }

                    DataResult.Success(domainList)
                } else {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }
            is NetworkResult.Failure -> {
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }

    override suspend fun finishPlan(
        planId: Int,
        planType: PlanType,
        expectedTime: String,
        date: LocalDate
    ): DataResult<FinishPlan, DataError> {
        val networkResult = executeApiRequest {
            api.postPlanFinish(
                planId = planId,
                planType = planType.server,
                request = PostPlanFinishRequest(
                    expectedTime = expectedTime
                )
            )
        }

        return when (networkResult) {
            is NetworkResult.Success -> {
                val response = networkResult.data
                val serverResult = response.result

                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    val domainItem = serverResult.toDomain()

                    // DailyPlans 캐시 업데이트
                    _dailyCache.update { old ->
                        val current = old[date]
                        if (current == null) old
                        else {
                            val unfinished = current.unfinishedPlan.toMutableList()
                            val finished = current.finishedPlan.toMutableList()
                            val idx =
                                unfinished.indexOfFirst { it.planId == planId && it.planType == planType }

                            if (idx >= 0) {
                                val moved = unfinished.removeAt(idx).copy(isDone = true)
                                finished.add(moved)
                            } else {
                                // unfinished에 plan이 없는 경우
                            }
                            old + (date to DailyPlans(
                                unfinishedPlan = unfinished.toList(),
                                finishedPlan = finished.toList()
                            ))
                        }
                    }

                    // Plans 캐시 업데이트
                    _planCache.update { old ->
                        val key = Pair(planId, planType)
                        val current = old[key]
                        if (current != null) {
                            old + (key to current.copy(isDone = true, status = domainItem.status))
                        } else old
                    }

                    DataResult.Success(domainItem)
                } else {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }
            is NetworkResult.Failure -> DataResult.Error(networkResult.error.toDomainError())
        }
    }

    override suspend fun deletePlan(
        planId: Int,
        date: LocalDate
    ): DataResult<Int, DataError> {
        val networkResult = executeApiRequest {
            api.deletePlan(basicPlanId = planId)
        }

        return when (networkResult) {
            is NetworkResult.Success -> {
                val response = networkResult.data
                val serverResult = response.result

                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    val deleteId = serverResult.deletedPlanId

                    // 캐시 업데이트
                    _dailyCache.update { old ->
                        val current = old[date]
                        if (current == null) old
                        else {
                            val unfinished =
                                current.unfinishedPlan.filterNot { it.planId == deleteId }
                            val finished = current.finishedPlan.filterNot { it.planId == deleteId }
                            old + (date to DailyPlans(unfinishedPlan = unfinished, finishedPlan = finished))
                        }
                    }

                    _planCache.update { old ->
                        old.filterKeys { it.first != deleteId }
                    }

                    DataResult.Success(deleteId)
                } else {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }
            is NetworkResult.Failure -> {
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }

    /** DailyPlans 받아올 때 plan 캐시 채우기  */
    private suspend fun populatePlanCacheFromDaily(
//        date: LocalDate,
        daily: DailyPlans
    ) {
        val combined = (daily.unfinishedPlan + daily.finishedPlan).map { p ->
            val plan = Plan(
                planId = p.planId,
                planType = p.planType,
                title = p.title,
                subTitle = p.subTitle,
                description = null,
                startDateTime = null,
                endDateTime = null,
                startTime = p.startTime,
                endTime = p.endTime,
                expectedDuration = null,
                priority = p.priority,
                status = null,
                isDone = p.isDone
            )

            Pair(p.planId, p.planType) to plan
        }.toMap()

        _planCache.update { old ->
            old + combined
        }
    }

    override suspend fun editPlan(
        basicPlanId: Int,
        draft: PlanDraft
    ): DataResult<Plan, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.patchPlan(
                basicPlanId = basicPlanId,
                request = PatchPlanRequest(
                    title = draft.title,
                    description = draft.description,
                    date = draft.startDate,
                    startTime = draft.startTime,
                    endTime = draft.endTime,
                    priority = draft.priority.server
                )
            )
        }

        return when (networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
                    val domainItem = serverResult.toDomain()

                    val parsedDate = LocalDate.parse(draft.startDate, dateFormatter)
                    refreshDailyPlans(parsedDate)

                    DataResult.Success(domainItem)
                } else {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }

            is NetworkResult.Failure -> { // 통신 실패
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }
}