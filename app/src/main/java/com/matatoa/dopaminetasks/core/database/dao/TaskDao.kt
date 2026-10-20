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

    @Query("UPDATE tasks SET is_done = :isDone WHERE id = :id")
    suspend fun updateTaskDone(id: Long, isDone: Boolean)

    @Query("SELECT * FROM tasks ORDER BY date")
    fun fetchTasks(): Flow<List<TaskEntity>>
}
