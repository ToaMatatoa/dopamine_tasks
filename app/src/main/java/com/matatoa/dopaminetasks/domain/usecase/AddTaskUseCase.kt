package com.matatoa.dopaminetasks.domain.usecase

import com.matatoa.dopaminetasks.core.database.dataresult.DataResult
import com.matatoa.dopaminetasks.core.database.repository.TaskRepository
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
