package com.matatoa.dopaminetasks.domain.usecase.habit

import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.HabitRepository
import javax.inject.Inject

interface DeleteHabitUseCase {
    suspend fun execute(id: Long): DataResult<Unit>
}

class DeleteHabitUseCaseImpl @Inject constructor(
    private val habitRepository: HabitRepository,
) : DeleteHabitUseCase {
    override suspend fun execute(id: Long): DataResult<Unit> =
        habitRepository.deleteHabit(id = id)
}
