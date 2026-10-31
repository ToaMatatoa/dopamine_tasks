package com.matatoa.dopaminetasks.domain.usecase.task

import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.TaskRepository
import com.matatoa.dopaminetasks.domain.mapper.toTaskEntity
import com.matatoa.dopaminetasks.domain.model.TaskModel
import javax.inject.Inject

interface AddTaskUseCase {
    suspend fun execute(task: TaskModel): DataResult<Unit>
}

class AddTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository,
) : AddTaskUseCase {
    override suspend fun execute(task: TaskModel): DataResult<Unit> =
        taskRepository.addTask(task = task.toTaskEntity())
}
