package com.miruni.feature.aiplanner.data.api

import com.miruni.core.network.ApiResponse
import com.miruni.feature.aiplanner.data.dto.request.PostAiPlansRequest
import com.miruni.feature.aiplanner.data.dto.response.DeleteResponse
import com.miruni.feature.aiplanner.data.dto.response.GetAiPlansResponse
import com.miruni.feature.aiplanner.data.dto.response.ScheduleResponse
import com.miruni.feature.aiplanner.data.dto.response.PlanDto
import com.miruni.feature.aiplanner.data.dto.response.PostAiPlansResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AiPlannerApi {

    /** AI 상위 일정 조회 */
    @GET("api/ai-plans")
    suspend fun getAiPlans(): ApiResponse<GetAiPlansResponse>

    /** AI 플래닝 */
    @POST("api/ai-plans")
    suspend fun postAiPlans(
        @Body request: PostAiPlansRequest
    ): ApiResponse<List<PostAiPlansResponse>>

    /** AI 플래닝 스케줄표 조회 */
    @GET("api/ai-plans/table/{plan_id}")
    suspend fun getScheduleTable(
        @Path("plan_id") planId: Int
    ): ApiResponse<ScheduleResponse>

    /** AI 플래닝 스케줄표 수정 */
    @PATCH("api/ai-plans/table/{plan_id}")
    suspend fun patchScheduleTable(
        @Path("plan_id") planId: Int,
        @Body request: PlanDto
    ): ApiResponse<ScheduleResponse>

    /** AI 플래닝 스케줄표 삭제 (스케줄표 전체 삭제) */
    @DELETE("api/ai-plans/table/{plan_id}")
    suspend fun deleteScheduleTable(
        @Path("plan_id") planId: Int
    ): ApiResponse<DeleteResponse>

    /** AI 플래닝 스케줄표 선택 삭제 (스케줄표 아이템 삭제) */
    @DELETE("api/ai-plans/table/items/{plan_id}")
    suspend fun deleteScheduleItem(
        @Path("plan_id") planId: Int,
        @Body request: List<Int> // 삭제할 AI 플랜 ID 리스트
    ): ApiResponse<DeleteResponse>
}