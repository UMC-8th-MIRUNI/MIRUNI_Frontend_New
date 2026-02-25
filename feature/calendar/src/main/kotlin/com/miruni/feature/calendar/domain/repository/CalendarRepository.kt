package com.miruni.feature.calendar.domain.repository

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.calendar.domain.model.DailyPlans
import com.miruni.feature.calendar.domain.model.DayInfo
import com.miruni.feature.calendar.domain.model.FinishPlan
import com.miruni.feature.calendar.domain.model.Plan
import com.miruni.feature.calendar.domain.model.PlanDraft
import com.miruni.feature.calendar.domain.model.PlanType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CalendarRepository {
    /** ---------- 관찰 API ---------- */
    fun observeDailyPlans(
        date: LocalDate
    ): Flow<DailyPlans>

    fun observePlan(
        planId: Int,
        planType: PlanType
    ): Flow<Plan?>

    /** ---------- 갱신 API ---------- */
    suspend fun refreshDailyPlans(
        date: LocalDate
    ): DataResult<DailyPlans, DataError>

    suspend fun refreshPlan(
        planId: Int,
        planType: PlanType
    ): DataResult<Plan, DataError>

    /** ---------- 일정 CRUD API ---------- */
    /** 일반 일정 생성 */
    suspend fun createPlan(
        draft: PlanDraft
    ): DataResult<List<Plan>, DataError>
    /** 일정 완료 (변경) */
    suspend fun finishPlan(
        planId: Int,
        planType: PlanType,
        expectedTime: String,
        date: LocalDate
    ): DataResult<FinishPlan, DataError>
    /** 일정 완료 (이전) */
    suspend fun postPlanFinish(
        planId: Int,
        planType: PlanType,
        expectedTime: String
    ): DataResult<FinishPlan, DataError>
    /** 특정 일정 조회 */
    suspend fun getPlan(
        planId: Int,
        planType: PlanType
    ): DataResult<Plan, DataError>

    /** 캘린더 조회
     * 특정 연월의 날짜별 미완료 일정 개수 조회
     */
    suspend fun getMonthlyPlanCount(
        year: Int,
        month: Int
    ): DataResult<List<DayInfo>, DataError>
    /** 특정 날짜의 완료/미완료 일정 조회 */
    suspend fun getDailyPlans(
        year: Int,
        month: Int,
        day: Int
    ): DataResult<DailyPlans, DataError>

    /** 일반 일정 수정 */
    suspend fun editPlan(
        basicPlanId: Int,
        draft: PlanDraft
    ): DataResult<Plan, DataError>

    /** 일반 일정 삭제 (변경) */
    suspend fun deletePlan(
        planId: Int,
        date: LocalDate
    ): DataResult<Int, DataError>
    /** 일반 일정 삭제 (이전) */
    suspend fun deletePlan(
        basicPlanId: Int
    ): DataResult<Int, DataError>
}