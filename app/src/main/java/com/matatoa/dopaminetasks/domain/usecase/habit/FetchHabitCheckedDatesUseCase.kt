package com.matatoa.dopaminetasks.domain.usecase.habit

import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.repository.HabitRepository
import com.matatoa.dopaminetasks.domain.mapper.toCheckedDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

interface FetchHabitCheckedDatesUseCase {
    fun execute(habitId: Long): Flow<DataResult<List<LocalDate>>>
}

class FetchHabitCheckedDatesUseCaseImpl @Inject constructor(
    private val habitRepository: HabitRepository,
) : FetchHabitCheckedDatesUseCase {
    override fun execute(habitId: Long): Flow<DataResult<List<LocalDate>>> =
        habitRepository.fetchHabitChecks(habitId = habitId).map { result ->
            when (result) {
                is DataResult.Success -> DataResult.Success(
                    data = result.data.map { check -> check.toCheckedDate() },
                )

                is DataResult.Error -> result
            }
        }
}
