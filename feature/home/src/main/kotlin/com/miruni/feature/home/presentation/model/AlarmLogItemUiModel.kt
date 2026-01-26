package com.miruni.feature.home.presentation.model

sealed interface AlarmLogItemUiModel{
    val id: Int
    val planTitle: String
    val minutesAgo: Int

    data class Warn(
        override val id: Int,
        override val planTitle: String,
        override val minutesAgo: Int,
        val userName: String,
    ) : AlarmLogItemUiModel

    data class Alert(
        override val id: Int,
        override val planTitle: String,
        override val minutesAgo: Int
    ) : AlarmLogItemUiModel

}

