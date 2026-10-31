package com.matatoa.dopaminetasks.domain.model

import java.time.LocalDate

data class HabitModel(
    val id: Long,
    val tabNumber: Int,
    val tabTitle: String,
    val title: String,
    val startDate: LocalDate,
    val daysToComplete: Int,
    val isCompletionShown: Boolean = false,
) {
    val endDate: LocalDate = startDate.plusDays(daysToComplete - 1L)

    val dates: List<LocalDate> = List(size = daysToComplete) { index ->
        startDate.plusDays(index.toLong())
    }

    fun isFinished(today: LocalDate): Boolean = today.isAfter(endDate)

    fun isCheckable(today: LocalDate): Boolean = today in dates

    fun isMissedCompletionPending(today: LocalDate): Boolean =
        isFinished(today = today) && !isCompletionShown

    fun completionAfterCheck(today: LocalDate, checkedDates: List<LocalDate>): HabitCompletion {
        val checkedCount = (checkedDates + today).toSet().size
        return when {
            today != endDate -> HabitCompletion.DAY_CHECKED
            checkedCount >= daysToComplete -> HabitCompletion.ALL_DAYS_CHECKED
            else -> HabitCompletion.SOME_DAYS_MISSED
        }
    }

    companion object {
        const val MAX_COUNT = 3
        const val TAB_TITLE_MAX_LENGTH = 20
        const val TITLE_MAX_LENGTH = 150
        const val MIN_DAYS_TO_COMPLETE = 7
        const val MAX_DAYS_TO_COMPLETE = 60
    }
}

enum class HabitCompletion {
    DAY_CHECKED,
    ALL_DAYS_CHECKED,
    SOME_DAYS_MISSED,
}
