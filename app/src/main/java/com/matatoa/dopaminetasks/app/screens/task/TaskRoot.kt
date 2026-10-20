package com.matatoa.dopaminetasks.app.screens.task

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matatoa.dopaminetasks.R.string

@Composable
fun TaskRoot(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val resources by rememberUpdatedState(newValue = LocalResources.current)

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

                TaskEvent.Error -> Unit
            }
        }
    }

    TaskScreen(
        state = state,
        onTaskClick = {},
        onTaskDelete = viewModel::deleteTask,
        onAddTask = viewModel::addTask,
        modifier = modifier,
    )
}
