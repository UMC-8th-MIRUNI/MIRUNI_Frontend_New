package com.miruni.feature.home.domain.model

enum class PlanType(
    val server: String, // 서버에서 받는 값
    val ui: String // UI에서 받는 값
) {
    BASIC("BASIC", "일반"),
    AI("AI", "ai");

    companion object {
        /** server -> PlanType */
        fun fromServer(server: String?): PlanType = entries.find {
            it.server == server
        } ?: BASIC

        /** ui -> PlanType */
        fun fromUi(ui: String?): PlanType = entries.find {
            it.ui == ui
        } ?: BASIC
    }
}