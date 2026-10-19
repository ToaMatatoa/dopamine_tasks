package com.matatoa.dopaminetasks.core.database.repository

import android.database.sqlite.SQLiteException
import com.matatoa.dopaminetasks.core.database.dao.TaskDao
import com.matatoa.dopaminetasks.core.database.dataresult.DataResult
import com.matatoa.dopaminetasks.core.database.entity.TaskEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: TaskEntity): DataResult<Unit>
    suspend fun deleteTask(id: Long): DataResult<Unit>
    fun fetchTasks(): Flow<List<TaskEntity>>
}

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
) : TaskRepository {
    override suspend fun addTask(task: TaskEntity): DataResult<Unit> =
        try {
            taskDao.addTask(task = task)
            DataResult.Success(data = Unit)
        } catch (e: SQLiteException) {
            DataResult.Error(error = e)
        }

    override suspend fun deleteTask(id: Long): DataResult<Unit> =
        try {
            taskDao.deleteTask(id = id)
            DataResult.Success(data = Unit)
        } catch (e: SQLiteException) {
            DataResult.Error(error = e)
        }

    override fun fetchTasks(): Flow<List<TaskEntity>> =
        taskDao.fetchTasks()
}
