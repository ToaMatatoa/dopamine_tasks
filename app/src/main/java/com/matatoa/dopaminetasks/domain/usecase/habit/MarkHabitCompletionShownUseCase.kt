package com.matatoa.dopaminetasks.domain.usecase.habit

import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.HabitRepository
import javax.inject.Inject

interface MarkHabitCompletionShownUseCase {
    suspend fun execute(id: Long): DataResult<Unit>
}

class MarkHabitCompletionShownUseCaseImpl @Inject constructor(
    private val habitRepository: HabitRepository,
) : MarkHabitCompletionShownUseCase {
    override suspend fun execute(id: Long): DataResult<Unit> =
        habitRepository.markHabitCompletionShown(id = id)
}
