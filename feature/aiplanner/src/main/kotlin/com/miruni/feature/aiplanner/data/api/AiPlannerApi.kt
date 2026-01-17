package com.miruni.feature.aiplanner.data.api

import com.miruni.core.network.ApiResponse
import com.miruni.feature.aiplanner.data.dto.request.PostAiPlansRequest
import com.miruni.feature.aiplanner.data.dto.response.GetAiPlansResponse
import com.miruni.feature.aiplanner.data.dto.response.GetScheduleResponse
import com.miruni.feature.aiplanner.data.dto.response.PlanModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AiPlannerApi {

    /** AI 상위 일정 조회 */
    @GET("api/ai-plans")
    suspend fun getAiPlans(): ApiResponse<List<GetAiPlansResponse>>

    /** AI 플래닝 */
    @POST("api/ai-plans")
    suspend fun postAiPlans(
        @Body request: PostAiPlansRequest
    ): ApiResponse<List<PlanModel>>

    /** AI 플래닝 스케줄표 조회 */
    @GET("api/ai-plans/table/{plan_id}")
    suspend fun getSchedule(
        @Path("plan_id") planId: Long
    ): ApiResponse<GetScheduleResponse>

    /** AI 플래닝 스케줄표 삭제 (스케줄표 전체 삭제) */
    @DELETE("api/ai-plans/table/{plan_id}")
    suspend fun deleteScheduleTable(
        @Path("plan_id") planId: Long
    ): ApiResponse<Boolean>

    /** AI 플래닝 스케줄표 선택 삭제 (스케줄표 아이템 삭제) */
    @DELETE("api/ai-plans/table/items/{plan-id}")
    suspend fun deleteScheduleItem(
        @Path("plan-id") planId: Long,
        @Body request: List<Long> // 삭제할 AI 플랜 ID 리스트
    ): ApiResponse<Boolean>

    @GET("/")
    suspend fun getRemain(): ApiResponse<AiPlannerRemainResponse>
}

data class AiPlannerRemainResponse(
    val remain: Int // 잔여 횟수
)