package com.matatoa.dopaminetasks.domain.usecase.task

import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.TaskRepository
import javax.inject.Inject

interface UpdateTaskDoneUseCase {
    suspend fun execute(id: Long, isDone: Boolean): DataResult<Unit>
}

class UpdateTaskDoneUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository,
) : UpdateTaskDoneUseCase {
    override suspend fun execute(id: Long, isDone: Boolean): DataResult<Unit> =
        taskRepository.updateTaskDone(id = id, isDone = isDone)
}
