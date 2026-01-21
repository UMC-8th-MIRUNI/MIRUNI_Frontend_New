package com.miruni.feature.aiplanner.domain.model

enum class PlanTimePeriod(
    val server: String,
    val ui: String
) {
    RANDOM("RANDOM", "랜덤으로 설정"),
    MORNING("MORNING", "아침 시간(6~9시"),
    FOCUS_MORNING("FOCUS_MORNING", "오전 집중 시간 (9~12시"),
    AFTERNOON("AFTERNOON", "오후 느슨한 시간 (13~17시"),
    EVENING("EVENING", "저녁 시간 (18~21시"),
    NIGHT("NIGHT", "밤 시간 (22~24시"),
    DAWN("DAWN", "새벽 (0~6시)");

    companion object {
        /** server -> PlanTimePeriod */
        fun fromServer(value: String?): PlanTimePeriod = entries.find {
            it.server == value
        } ?: RANDOM

        /** ui -> PlanTimePeriod */
        fun fromUi(label: String): PlanTimePeriod = entries.find {
            it.ui == label
        } ?: RANDOM
    }
}