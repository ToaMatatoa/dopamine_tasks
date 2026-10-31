package com.matatoa.dopaminetasks.domain.usecase.habit

import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.HabitRepository
import java.time.LocalDate
import javax.inject.Inject

interface UncheckHabitTodayUseCase {
    suspend fun execute(habitId: Long): DataResult<Unit>
}

class UncheckHabitTodayUseCaseImpl @Inject constructor(
    private val habitRepository: HabitRepository,
) : UncheckHabitTodayUseCase {
    override suspend fun execute(habitId: Long): DataResult<Unit> =
        habitRepository.deleteHabitCheck(
            habitId = habitId,
            epochDay = LocalDate.now().toEpochDay(),
        )
}
