package com.matatoa.dopaminetasks.app.screens.task

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matatoa.dopaminetasks.R.string
import com.matatoa.dopaminetasks.designsystem.InfoDialog

@Composable
fun TaskRoot(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val resources by rememberUpdatedState(newValue = LocalResources.current)
    var milestone by rememberSaveable { mutableStateOf<TaskCompletion?>(value = null) }

    LifecycleResumeEffect(Unit) {
        viewModel.refreshToday()
        onPauseOrDispose {}
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is TaskEvent.TaskAdded -> Toast.makeText(
                    context,
                    resources.getString(string.you_added_task, event.title),
                    Toast.LENGTH_SHORT,
                ).show()

                is TaskEvent.TaskDeleted -> Toast.makeText(
                    context,
                    resources.getString(string.you_deleted_task, event.title),
                    Toast.LENGTH_SHORT,
                ).show()

                is TaskEvent.TaskCompleted -> if (event.completion.isMilestone) {
                    milestone = event.completion
                } else {
                    Toast.makeText(
                        context,
                        resources.getString(event.completion.messageRes()),
                        Toast.LENGTH_LONG,
                    ).show()
                }

                TaskEvent.Error -> Unit
            }
        }
    }

    milestone?.let { completion ->
        InfoDialog(
            message = stringResource(id = completion.messageRes()),
            onDismiss = { milestone = null },
        )
    }

    TaskScreen(
        state = state,
        onDateClick = viewModel::selectDate,
        onTaskClick = viewModel::toggleTaskDone,
        onTaskDelete = viewModel::deleteTask,
        onAddTask = viewModel::addTask,
        modifier = modifier,
    )
}

@StringRes
private fun TaskCompletion.messageRes(): Int = when (this) {
    TaskCompletion.IMPORTANT_TASK -> string.important_task_completed
    TaskCompletion.COMMON_TASK -> string.common_task_completed
    TaskCompletion.COMMON_TASK_NO_IMPORTANT_LEFT -> string.common_task_completed_keep_going
    TaskCompletion.ALL_IMPORTANT_TASKS -> string.all_important_tasks_completed
    TaskCompletion.ALL_COMMON_TASKS -> string.all_common_tasks_completed
    TaskCompletion.ALL_TASKS -> string.all_tasks_completed
}
