package com.matatoa.dopaminetasks.domain.usecase.habit

import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.HabitRepository
import com.matatoa.dopaminetasks.domain.mapper.toHabitEntity
import com.matatoa.dopaminetasks.domain.model.HabitModel
import javax.inject.Inject

interface AddHabitUseCase {
    suspend fun execute(habit: HabitModel): DataResult<Unit>
}

class AddHabitUseCaseImpl @Inject constructor(
    private val habitRepository: HabitRepository,
) : AddHabitUseCase {
    override suspend fun execute(habit: HabitModel): DataResult<Unit> =
        habitRepository.addHabit(habit = habit.toHabitEntity())
}
