package com.matatoa.dopaminetasks.domain.usecase.habit

import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.HabitRepository
import com.matatoa.dopaminetasks.domain.mapper.toHabitModel
import com.matatoa.dopaminetasks.domain.model.HabitModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FetchHabitsUseCase {
    fun execute(): Flow<DataResult<List<HabitModel>>>
}

class FetchHabitsUseCaseImpl @Inject constructor(
    private val habitRepository: HabitRepository,
) : FetchHabitsUseCase {
    override fun execute(): Flow<DataResult<List<HabitModel>>> =
        habitRepository.fetchHabits().map { result ->
            when (result) {
                is DataResult.Success -> DataResult.Success(
                    data = result.data.map { habit -> habit.toHabitModel() },
                )

                is DataResult.Error -> result
            }
        }
}
