package com.matatoa.dopaminetasks.app.screens.habits

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HabitRoot(
    modifier: Modifier = Modifier,
    viewModel: HabitViewModel = hiltViewModel(),
) {

    HabitScreen(
        modifier = modifier,
    )
}
