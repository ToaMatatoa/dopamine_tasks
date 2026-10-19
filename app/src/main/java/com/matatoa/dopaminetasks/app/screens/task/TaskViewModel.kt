package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matatoa.dopaminetasks.core.database.dataresult.DataResult
import com.matatoa.dopaminetasks.domain.model.TaskModel
import com.matatoa.dopaminetasks.domain.usecase.AddTaskUseCase
import com.matatoa.dopaminetasks.domain.usecase.DeleteTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : ViewModel() {
    val state: StateFlow<TaskScreenState>
        field = MutableStateFlow(value = TaskScreenState())

    private val _events = Channel<TaskEvent>(capacity = Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun addTask(task: TaskModel) {
        viewModelScope.launch {
            state.update { current -> current.copy(isLoading = true) }
            val event = when (addTaskUseCase.execute(task = task)) {
                is DataResult.Success -> TaskEvent.TaskAdded(title = task.title)
                is DataResult.Error -> TaskEvent.Error
            }
            state.update { current -> current.copy(isLoading = false) }
            _events.send(element = event)
        }
    }

    fun deleteTask(task: TaskModel) {
        viewModelScope.launch {
            state.update { current -> current.copy(isLoading = true) }
            val event = when (deleteTaskUseCase.execute(id = task.id)) {
                is DataResult.Success -> TaskEvent.TaskDeleted(title = task.title)
                is DataResult.Error -> TaskEvent.Error
            }
            state.update { current -> current.copy(isLoading = false) }
            _events.send(element = event)
        }
    }
}

@Immutable
data class TaskScreenState(
    val isLoading: Boolean = false,
    val tasks: List<TaskModel> = emptyList(),
)

sealed interface TaskEvent {
    data class TaskAdded(
        val title: String,
    ) : TaskEvent
    data class TaskDeleted(
        val title: String,
    ) : TaskEvent
    data object Error : TaskEvent
}
