package com.matatoa.dopaminetasks.domain.model

import java.time.LocalDate

data class HabitModel(
    val id: Long,
    val tabNumber: Int,
    val tabTitle: String,
    val title: String,
    val startDate: LocalDate,
    val daysToComplete: Int,
) {
    val endDate: LocalDate = startDate.plusDays(daysToComplete - 1L)

    companion object {
        const val MAX_COUNT = 3
        const val TAB_TITLE_MAX_LENGTH = 20
        const val TITLE_MAX_LENGTH = 150
        const val MIN_DAYS_TO_COMPLETE = 7
        const val MAX_DAYS_TO_COMPLETE = 60
    }
}
