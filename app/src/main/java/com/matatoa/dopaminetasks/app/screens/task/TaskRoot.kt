package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TaskRoot(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    TaskScreen(
        state = state,
        onTaskClick = {},
        onTaskDelete = {},
        onAddTaskClick = {},
        modifier = modifier,
    )
}
