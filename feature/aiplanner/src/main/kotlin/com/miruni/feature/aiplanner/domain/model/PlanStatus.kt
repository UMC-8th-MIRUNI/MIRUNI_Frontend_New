package com.miruni.feature.aiplanner.domain.model

enum class PlanStatus(
    val server: String,
    val ui: String
) {
    TODO("TODO", "예정"),
    IN_PROGRESS("IN_PROGRESS", "진행 중"),
    DONE("DONE", "완료");

    companion object {
        /** server -> ui */
        fun fromServer(value: String?): PlanStatus = entries.find {
            it.server == value
        } ?: TODO

        /** ui -> server */
        fun fromUi(label: String): PlanStatus = entries.find {
            it.ui == label
        } ?: TODO
    }
}