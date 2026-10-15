package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun TaskRoot(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = hiltViewModel(),
) {
    TaskScreen(
        modifier = modifier,
    )
}
