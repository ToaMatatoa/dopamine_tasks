package com.matatoa.dopaminetasks.domain.usecase

import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.TaskRepository
import com.matatoa.dopaminetasks.domain.mapper.toTaskModel
import com.matatoa.dopaminetasks.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FetchTasksUseCase {
    fun execute(): Flow<DataResult<List<TaskModel>>>
}

class FetchTasksUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository,
) : FetchTasksUseCase {
    override fun execute(): Flow<DataResult<List<TaskModel>>> =
        taskRepository.fetchTasks().map { result ->
            when (result) {
                is DataResult.Success -> DataResult.Success(
                    data = result.data.map { task -> task.toTaskModel() },
                )

                is DataResult.Error -> result
            }
        }
}
