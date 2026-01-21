package com.miruni.feature.aiplanner.presentation.model

import com.miruni.feature.aiplanner.domain.model.PlanInput

data class PlanningFormItemUiModel(
    val id: String,
    val title: String,
    val placeholder: String,
    val value: PlanInput?,
    val visible: Boolean
)
