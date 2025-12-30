package com.miruni.feature.aiplanner.data.repository

import com.miruni.feature.aiplanner.data.api.AiPlannerApi
import com.miruni.feature.aiplanner.domain.AiPlannerRepository
import com.miruni.feature.aiplanner.domain.AiPlannerUiModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiPlannerRepositoryImpl @Inject constructor(
//    private val api: AiPlannerApi
) : AiPlannerRepository {
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
}