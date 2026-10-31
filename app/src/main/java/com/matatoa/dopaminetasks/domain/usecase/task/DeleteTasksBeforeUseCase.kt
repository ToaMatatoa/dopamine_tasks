package com.matatoa.dopaminetasks.domain.usecase.task

import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.TaskRepository
import com.matatoa.dopaminetasks.domain.mapper.toStartOfDayMillis
import java.time.LocalDate
import javax.inject.Inject

interface DeleteTasksBeforeUseCase {
    suspend fun execute(date: LocalDate): DataResult<Unit>
}

class DeleteTasksBeforeUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository,
) : DeleteTasksBeforeUseCase {
    override suspend fun execute(date: LocalDate): DataResult<Unit> =
        taskRepository.deleteTasksBefore(beforeMillis = date.toStartOfDayMillis())
}
