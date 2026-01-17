package com.miruni.feature.aiplanner.data.repository

import com.miruni.feature.aiplanner.data.api.AiPlannerApi
import com.miruni.feature.aiplanner.domain.repository.MainRepository
import com.miruni.feature.aiplanner.presentation.model.AiPlanUiModel
import com.miruni.feature.aiplanner.presentation.model.AiPlannerUiModel
import com.miruni.feature.aiplanner.presentation.model.PlanUiModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(
    private val api: AiPlannerApi
) : MainRepository {
    override suspend fun getAiPlans(): List<AiPlannerUiModel> {
//        val response = api.getAiPlans()
//        return response.result.map {
//            AiPlannerUiModel(
//                id = it.id,
//                title = it.title,
//                isDone = it.isDone,
//                doneCount = it.doneCount,
//                totalCount = it.totalCount,
//                progressRate = it.progressRate
//            )
//        }

        return listOf(
            AiPlannerUiModel(
                id = 1,
                title = "UMC",
                isDone = false,
                doneCount = 2,
                totalCount = 10,
                progressRate = 20
            ),
            AiPlannerUiModel(
                id = 2,
                title = "CMC",
                isDone = false,
                doneCount = 4,
                totalCount = 10,
                progressRate = 40
            )
        )
    }

    override suspend fun getRemain(): Int {
//        val response = api.getRemain()
//        return response.result.remain

        return 0
    }

    override suspend fun getSchedule(id: Long): PlanUiModel {
        val response = api.getSchedule(id)
        val result = response.result
        val plan = PlanUiModel(
            planId = result?.plan!!.planId,
            title = result.plan.title,
            deadline = result.plan.deadline,
            taskRange = result.plan.taskRange,
            priority = result.plan.priority,
            aiPlans = result.plan.aiPlans.map {
                AiPlanUiModel(
                    aiPlanId = it.aiPlanId,
                    scheduledDate = it.scheduledDate,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    content = it.subTitle,
                    expectedDuration = it.expectedDuration
                )
            }
        )

        return plan
    }
}