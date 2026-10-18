package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.matatoa.dopaminetasks.domain.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class TaskViewModel @Inject constructor() : ViewModel() {
    val state: StateFlow<TaskScreenState>
        field = MutableStateFlow(value = TaskScreenState())
}

@Immutable
data class TaskScreenState(
    val tasks: List<TaskModel> = emptyList(),
)
