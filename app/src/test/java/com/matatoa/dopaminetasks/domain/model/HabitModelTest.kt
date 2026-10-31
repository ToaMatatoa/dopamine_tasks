package com.matatoa.dopaminetasks.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class HabitModelTest {
    private val start = LocalDate.of(2026, 10, 31)
    private val habit = HabitModel(
        id = 1,
        tabNumber = 1,
        tabTitle = "Tab",
        title = "Title",
        startDate = start,
        daysToComplete = 7,
    )

    @Test
    fun `start day counts as the first day`() {
        assertEquals(LocalDate.of(2026, 11, 6), habit.endDate)
        assertEquals(7, habit.dates.size)
        assertEquals(start, habit.dates.first())
        assertEquals(habit.endDate, habit.dates.last())
    }

    @Test
    fun `habit is finished only after the last day`() {
        assertFalse(habit.isFinished(today = habit.endDate))
        assertTrue(habit.isFinished(today = habit.endDate.plusDays(1)))
    }

    @Test
    fun `only days inside the habit are checkable`() {
        assertFalse(habit.isCheckable(today = start.minusDays(1)))
        assertTrue(habit.isCheckable(today = start))
        assertTrue(habit.isCheckable(today = habit.endDate))
        assertFalse(habit.isCheckable(today = habit.endDate.plusDays(1)))
    }

    @Test
    fun `missed completion is pending once finished and not shown`() {
        val afterEnd = habit.endDate.plusDays(1)

        assertFalse(habit.isMissedCompletionPending(today = habit.endDate))
        assertTrue(habit.isMissedCompletionPending(today = afterEnd))
        assertFalse(
            habit.copy(isCompletionShown = true).isMissedCompletionPending(today = afterEnd),
        )
    }

    @Test
    fun `check before the last day is a daily check`() {
        assertEquals(
            HabitCompletion.DAY_CHECKED,
            habit.completionAfterCheck(today = start, checkedDates = emptyList()),
        )
    }

    @Test
    fun `last day with every day checked completes the habit`() {
        assertEquals(
            HabitCompletion.ALL_DAYS_CHECKED,
            habit.completionAfterCheck(
                today = habit.endDate,
                checkedDates = habit.dates.dropLast(1),
            ),
        )
    }

    @Test
    fun `last day with a missed day reports missed days`() {
        assertEquals(
            HabitCompletion.SOME_DAYS_MISSED,
            habit.completionAfterCheck(
                today = habit.endDate,
                checkedDates = habit.dates.drop(1).dropLast(1),
            ),
        )
    }
}
