package com.matatoa.dopaminetasks.domain.usecase.habit

import com.matatoa.dopaminetasks.core.database.entity.HabitCheckEntity
import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.HabitRepository
import java.time.LocalDate
import javax.inject.Inject

interface CheckHabitTodayUseCase {
    suspend fun execute(habitId: Long): DataResult<Unit>
}

class CheckHabitTodayUseCaseImpl @Inject constructor(
    private val habitRepository: HabitRepository,
) : CheckHabitTodayUseCase {
    override suspend fun execute(habitId: Long): DataResult<Unit> =
        habitRepository.addHabitCheck(
            check = HabitCheckEntity(
                habitId = habitId,
                epochDay = LocalDate.now().toEpochDay(),
            ),
        )
}
