package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.domain.mapper.toStartOfDayMillis
import com.matatoa.dopaminetasks.domain.model.TaskModel
import com.matatoa.dopaminetasks.domain.usecase.task.AddTaskUseCase
import com.matatoa.dopaminetasks.domain.usecase.task.DeleteTaskUseCase
import com.matatoa.dopaminetasks.domain.usecase.task.DeleteTasksBeforeUseCase
import com.matatoa.dopaminetasks.domain.usecase.task.FetchTasksUseCase
import com.matatoa.dopaminetasks.domain.usecase.task.UpdateTaskDoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val deleteTasksBeforeUseCase: DeleteTasksBeforeUseCase,
    private val updateTaskDoneUseCase: UpdateTaskDoneUseCase,
    fetchTasksUseCase: FetchTasksUseCase,
) : ViewModel() {

    private val today = MutableStateFlow(value = LocalDate.now())
    private val selectedDate = MutableStateFlow(value = today.value)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<TaskScreenState> = combine(
        today,
        selectedDate,
        selectedDate.flatMapLatest { date -> fetchTasksUseCase.execute(date = date) },
    ) { today, selectedDate, result ->
        when (result) {
            is DataResult.Success -> TaskScreenState(
                today = today,
                selectedDate = selectedDate,
                tasks = result.data,
            )

            is DataResult.Error -> TaskScreenState(
                today = today,
                selectedDate = selectedDate,
                isError = true,
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = TaskScreenState(
            today = today.value,
            selectedDate = selectedDate.value,
            isLoadingTasks = true,
        ),
    )

    private val _events = Channel<TaskEvent>(capacity = Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            today.collect { date ->
                deleteTasksBeforeUseCase.execute(
                    date = date.minusDays(TaskScreenState.DAYS_BEFORE_TODAY),
                )
            }
        }
    }

    fun selectDate(date: LocalDate) {
        selectedDate.value = date
    }

    // The ViewModel outlives midnight while the app is in the background, so the date row
    // has to move on. The selection resets because the old one may have left the row.
    fun refreshToday() {
        val now = LocalDate.now()
        if (now != today.value) {
            today.value = now
            selectedDate.value = now
        }
    }

    fun addTask(title: String, isImportant: Boolean) {
        val task = TaskModel(
            id = 0,
            title = title,
            isImportant = isImportant,
            date = selectedDate.value.toStartOfDayMillis(),
        )
        viewModelScope.launch {
            val event = when (addTaskUseCase.execute(task = task)) {
                is DataResult.Success -> TaskEvent.TaskAdded(title = task.title)
                is DataResult.Error -> TaskEvent.Error
            }
            _events.send(element = event)
        }
    }

    fun deleteTask(task: TaskModel) {
        viewModelScope.launch {
            val event = when (deleteTaskUseCase.execute(id = task.id)) {
                is DataResult.Success -> TaskEvent.TaskDeleted(title = task.title)
                is DataResult.Error -> TaskEvent.Error
            }
            _events.send(element = event)
        }
    }

    fun toggleTaskDone(task: TaskModel) {
        val isDone = !task.isDone
        val completion = if (isDone) taskCompletion(task = task) else null
        viewModelScope.launch {
            val event = when (updateTaskDoneUseCase.execute(id = task.id, isDone = isDone)) {
                is DataResult.Success -> completion?.let { completion ->
                    TaskEvent.TaskCompleted(completion = completion)
                }

                is DataResult.Error -> TaskEvent.Error
            }
            event?.let { _events.send(element = it) }
        }
    }

    private fun taskCompletion(task: TaskModel): TaskCompletion {
        val undoneTasks = state.value.tasks.filter { !it.isDone && it.id != task.id }
        val hasImportantLeft = undoneTasks.any { it.isImportant }
        val hasCommonLeft = undoneTasks.any { !it.isImportant }
        return when {
            !hasImportantLeft && !hasCommonLeft -> TaskCompletion.ALL_TASKS
            task.isImportant && hasImportantLeft -> TaskCompletion.IMPORTANT_TASK
            task.isImportant -> TaskCompletion.ALL_IMPORTANT_TASKS
            !hasCommonLeft -> TaskCompletion.ALL_COMMON_TASKS
            hasImportantLeft -> TaskCompletion.COMMON_TASK
            else -> TaskCompletion.COMMON_TASK_NO_IMPORTANT_LEFT
        }
    }
}

@Immutable
data class TaskScreenState(
    val today: LocalDate = LocalDate.now(),
    val selectedDate: LocalDate = today,
    val isLoadingTasks: Boolean = false,
    val isError: Boolean = false,
    val tasks: List<TaskModel> = emptyList(),
) {
    val dates: List<LocalDate> = (-DAYS_BEFORE_TODAY..DAYS_AFTER_TODAY).map { offset ->
        today.plusDays(offset)
    }

    companion object {
        const val DAYS_BEFORE_TODAY = 2L
        const val DAYS_AFTER_TODAY = 4L
    }
}

sealed interface TaskEvent {
    data class TaskAdded(
        val title: String,
    ) : TaskEvent

    data class TaskDeleted(
        val title: String,
    ) : TaskEvent

    data class TaskCompleted(
        val completion: TaskCompletion,
    ) : TaskEvent

    data object Error : TaskEvent
}

enum class TaskCompletion(
    val isMilestone: Boolean,
) {
    IMPORTANT_TASK(isMilestone = false),
    COMMON_TASK(isMilestone = false),
    COMMON_TASK_NO_IMPORTANT_LEFT(isMilestone = false),
    ALL_IMPORTANT_TASKS(isMilestone = true),
    ALL_COMMON_TASKS(isMilestone = true),
    ALL_TASKS(isMilestone = true),
}
