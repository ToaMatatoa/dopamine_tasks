package com.matatoa.dopaminetasks.core.repository

import android.database.sqlite.SQLiteException
import com.matatoa.dopaminetasks.core.database.dao.HabitDao
import com.matatoa.dopaminetasks.core.database.entity.HabitCheckEntity
import com.matatoa.dopaminetasks.core.database.entity.HabitEntity
import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.dataresult.asResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface HabitRepository {
    suspend fun addHabit(habit: HabitEntity): DataResult<Unit>
    suspend fun updateHabit(
        id: Long,
        tabTitle: String,
        title: String,
        daysToComplete: Int,
    ): DataResult<Unit>
    suspend fun deleteHabit(id: Long): DataResult<Unit>
    fun fetchHabits(): Flow<DataResult<List<HabitEntity>>>
    suspend fun addHabitCheck(check: HabitCheckEntity): DataResult<Unit>
    suspend fun deleteHabitCheck(habitId: Long, epochDay: Long): DataResult<Unit>
    fun fetchHabitChecks(habitId: Long): Flow<DataResult<List<HabitCheckEntity>>>
}

class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao,
) : HabitRepository {
    override suspend fun addHabit(habit: HabitEntity): DataResult<Unit> = try {
        habitDao.addHabit(habit = habit)
        DataResult.Success(data = Unit)
    } catch (e: SQLiteException) {
        DataResult.Error(error = e)
    }

    override suspend fun updateHabit(
        id: Long,
        tabTitle: String,
        title: String,
        daysToComplete: Int,
    ): DataResult<Unit> = try {
        habitDao.updateHabit(
            id = id,
            tabTitle = tabTitle,
            title = title,
            daysToComplete = daysToComplete,
        )
        DataResult.Success(data = Unit)
    } catch (e: SQLiteException) {
        DataResult.Error(error = e)
    }

    override suspend fun deleteHabit(id: Long): DataResult<Unit> = try {
        habitDao.deleteHabit(id = id)
        DataResult.Success(data = Unit)
    } catch (e: SQLiteException) {
        DataResult.Error(error = e)
    }

    override fun fetchHabits(): Flow<DataResult<List<HabitEntity>>> =
        habitDao.fetchHabits().asResult()

    override suspend fun addHabitCheck(check: HabitCheckEntity): DataResult<Unit> = try {
        habitDao.addHabitCheck(check = check)
        DataResult.Success(data = Unit)
    } catch (e: SQLiteException) {
        DataResult.Error(error = e)
    }

    override suspend fun deleteHabitCheck(habitId: Long, epochDay: Long): DataResult<Unit> = try {
        habitDao.deleteHabitCheck(habitId = habitId, epochDay = epochDay)
        DataResult.Success(data = Unit)
    } catch (e: SQLiteException) {
        DataResult.Error(error = e)
    }

    override fun fetchHabitChecks(habitId: Long): Flow<DataResult<List<HabitCheckEntity>>> =
        habitDao.fetchHabitChecks(habitId = habitId).asResult()
}
