package com.matatoa.dopaminetasks.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.matatoa.dopaminetasks.core.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun addTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTask(id: Long)

    @Query("DELETE FROM tasks WHERE date < :beforeMillis")
    suspend fun deleteTasksBefore(beforeMillis: Long)

    @Query("UPDATE tasks SET is_done = :isDone WHERE id = :id")
    suspend fun updateTaskDone(id: Long, isDone: Boolean)

    @Query("SELECT * FROM tasks WHERE date >= :startMillis AND date < :endMillis ORDER BY id")
    fun fetchTasks(startMillis: Long, endMillis: Long): Flow<List<TaskEntity>>
}
