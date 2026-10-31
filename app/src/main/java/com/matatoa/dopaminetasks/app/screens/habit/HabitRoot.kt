package com.matatoa.dopaminetasks.app.screens.habit

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matatoa.dopaminetasks.R.string
import com.matatoa.dopaminetasks.designsystem.InfoDialog
import com.matatoa.dopaminetasks.domain.model.HabitCompletion

@Composable
fun HabitRoot(
    modifier: Modifier = Modifier,
    viewModel: HabitViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    var completion by rememberSaveable { mutableStateOf<HabitCompletion?>(value = null) }

    LifecycleResumeEffect(Unit) {
        viewModel.refreshToday()
        onPauseOrDispose {}
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is HabitEvent.Completed -> completion = event.completion
                HabitEvent.Error -> Unit
            }
        }
    }

    completion?.let {
        InfoDialog(
            message = stringResource(id = it.messageRes()),
            onDismiss = { completion = null },
        )
    }

    HabitScreen(
        state = state,
        onTabClick = viewModel::selectTab,
        onTodayClick = viewModel::toggleToday,
        onAddHabit = viewModel::addHabit,
        onUpdateHabit = viewModel::updateHabit,
        onDeleteHabit = viewModel::deleteHabit,
        modifier = modifier,
    )
}

@StringRes
private fun HabitCompletion.messageRes(): Int = when (this) {
    HabitCompletion.DAY_CHECKED -> string.habit_day_checked
    HabitCompletion.ALL_DAYS_CHECKED -> string.habit_all_days_checked
    HabitCompletion.SOME_DAYS_MISSED -> string.habit_some_days_missed
}
