package com.matatoa.dopaminetasks.app.screens.habit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matatoa.dopaminetasks.R
import com.matatoa.dopaminetasks.designsystem.DeleteDialog
import com.matatoa.dopaminetasks.designsystem.PrimaryButton
import com.matatoa.dopaminetasks.designsystem.TopBar
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme
import com.matatoa.dopaminetasks.domain.model.HabitModel
import java.time.LocalDate

@Composable
fun HabitScreen(
    state: HabitScreenState,
    onTabClick: (tabNumber: Int) -> Unit,
    onTodayClick: () -> Unit,
    onAddHabit: (tabTitle: String, title: String, daysToComplete: Int) -> Unit,
    onUpdateHabit: (tabTitle: String, title: String) -> Unit,
    onDeleteHabit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isSetupDialogVisible by rememberSaveable { mutableStateOf(value = false) }
    var isDeleteDialogVisible by rememberSaveable { mutableStateOf(value = false) }
    val habit = state.selectedHabit

    if (isSetupDialogVisible) {
        HabitSetupDialog(
            habit = habit,
            onConfirm = { tabTitle, title, daysToComplete ->
                if (habit == null) {
                    onAddHabit(tabTitle, title, daysToComplete)
                } else {
                    onUpdateHabit(tabTitle, title)
                }
                isSetupDialogVisible = false
            },
            onDismiss = { isSetupDialogVisible = false },
        )
    }

    if (isDeleteDialogVisible) {
        DeleteDialog(
            title = stringResource(id = R.string.habit_delete_question),
            titleColor = MaterialTheme.colorScheme.tertiary,
            onConfirm = {
                onDeleteHabit()
                isDeleteDialogVisible = false
            },
            onDismiss = { isDeleteDialogVisible = false },
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        TopBar(title = stringResource(id = R.string.habits_title))

        HabitTabs(
            habits = state.habits,
            selectedTabNumber = state.selectedTabNumber,
            onTabClick = onTabClick,
        )

        when {
            state.isLoading -> ScreenLoadingState()

            state.isError -> ScreenErrorState()

            habit == null -> PrimaryButton(
                text = stringResource(id = R.string.habit_setup),
                onClick = { isSetupDialogVisible = true },
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            )

            else -> {
                HabitHeader(
                    title = habit.title,
                    isFinished = habit.isFinished(today = state.today),
                    onEditClick = { isSetupDialogVisible = true },
                    onDeleteClick = { isDeleteDialogVisible = true },
                )

                // Each habit gets its own grid, so its scroll position starts at its own today.
                key(habit.id) {
                    HabitDaysGrid(
                        days = state.days,
                        onTodayClick = onTodayClick,
                        modifier = Modifier
                            .weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun ScreenErrorState(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.fetch_habits_error),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ScreenLoadingState(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize(),
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
private fun HabitScreenEmptyPreview() {
    val today = LocalDate.of(2026, 10, 31)
    DopamineTasksTheme {
        HabitScreen(
            state = HabitScreenState(today = today),
            onTabClick = {},
            onTodayClick = {},
            onAddHabit = { _, _, _ -> },
            onUpdateHabit = { _, _ -> },
            onDeleteHabit = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HabitScreenLoadedPreview() {
    val today = LocalDate.of(2026, 10, 31)
    val habit = HabitModel(
        id = 1,
        tabNumber = 1,
        tabTitle = "Morning run",
        title = "Run for at least twenty minutes every morning",
        startDate = today.minusDays(2),
        daysToComplete = 7,
    )
    val days = habit.dates.map { date ->
        HabitDay(
            date = date,
            isChecked = date.isBefore(today),
            isCheckable = date == today,
        )
    }
    DopamineTasksTheme {
        HabitScreen(
            state = HabitScreenState(today = today, habits = listOf(habit), days = days),
            onTabClick = {},
            onTodayClick = {},
            onAddHabit = { _, _, _ -> },
            onUpdateHabit = { _, _ -> },
            onDeleteHabit = {},
        )
    }
}
