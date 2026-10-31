package com.matatoa.dopaminetasks.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matatoa.dopaminetasks.core.database.entity.HabitCheckEntity
import com.matatoa.dopaminetasks.core.database.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Insert
    suspend fun addHabit(habit: HabitEntity)

    @Query(
        "UPDATE habits SET tab_title = :tabTitle, title = :title, " +
            "days_to_complete = :daysToComplete WHERE id = :id",
    )
    suspend fun updateHabit(id: Long, tabTitle: String, title: String, daysToComplete: Int)

    @Query("DELETE FROM habits WHERE id = :id")
    suspend fun deleteHabit(id: Long)

    @Query("SELECT * FROM habits ORDER BY tab_number")
    fun fetchHabits(): Flow<List<HabitEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHabitCheck(check: HabitCheckEntity)

    @Query("DELETE FROM habit_checks WHERE habit_id = :habitId AND epoch_day = :epochDay")
    suspend fun deleteHabitCheck(habitId: Long, epochDay: Long)

    @Query("SELECT * FROM habit_checks WHERE habit_id = :habitId ORDER BY epoch_day")
    fun fetchHabitChecks(habitId: Long): Flow<List<HabitCheckEntity>>
}
