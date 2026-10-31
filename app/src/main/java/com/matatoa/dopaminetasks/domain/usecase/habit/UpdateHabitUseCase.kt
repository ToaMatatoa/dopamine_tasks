package com.matatoa.dopaminetasks.domain.usecase.habit

import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.HabitRepository
import com.matatoa.dopaminetasks.domain.model.HabitModel
import javax.inject.Inject

interface UpdateHabitUseCase {
    suspend fun execute(habit: HabitModel): DataResult<Unit>
}

class UpdateHabitUseCaseImpl @Inject constructor(
    private val habitRepository: HabitRepository,
) : UpdateHabitUseCase {
    override suspend fun execute(habit: HabitModel): DataResult<Unit> =
        habitRepository.updateHabit(
            id = habit.id,
            tabTitle = habit.tabTitle,
            title = habit.title,
            daysToComplete = habit.daysToComplete,
        )
}
