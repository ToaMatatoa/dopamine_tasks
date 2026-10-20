package com.matatoa.dopaminetasks.domain.usecase

import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.TaskRepository
import javax.inject.Inject

interface DeleteTaskUseCase {
    suspend fun execute(id: Long): DataResult<Unit>
}

class DeleteTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository,
) : DeleteTaskUseCase {
    override suspend fun execute(id: Long): DataResult<Unit> =
        taskRepository.deleteTask(id = id)
}
