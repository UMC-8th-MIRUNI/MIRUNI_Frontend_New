package com.miruni.feature.calendar.domain.repository

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.calendar.domain.model.BasicPlan
import com.miruni.feature.calendar.domain.model.DailyPlans
import com.miruni.feature.calendar.domain.model.Day
import com.miruni.feature.calendar.domain.model.FinishPlan
import com.miruni.feature.calendar.domain.model.Plan
import com.miruni.feature.calendar.domain.model.PlanDraft
import com.miruni.feature.calendar.domain.model.PlanType

interface CalendarRepository {
    /** 일정 완료 */
    suspend fun postPlanFinish(
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
    ): DataResult<List<Day>, DataError>

    /** 특정 날짜의 완료/미완료 일정 조회 */
    suspend fun getDailyPlans(
        year: Int,
        month: Int,
        day: Int
    ): DataResult<DailyPlans, DataError>

    /** 일반 일정 생성 */
    suspend fun postPlan(
        draft: PlanDraft
    ): DataResult<BasicPlan, DataError>

    /** 일반 일정 삭제 */
    suspend fun deletePlan(
        basicPlanId: Int
    ): DataResult<Int, DataError>

    /** 일반 일정 수정 */
    suspend fun editPlan(
        basicPlanId: Int,
        draft: PlanDraft
    ): DataResult<BasicPlan, DataError>
}