package com.matatoa.dopaminetasks.core.repository

import android.database.sqlite.SQLiteException
import com.matatoa.dopaminetasks.core.database.dao.TaskDao
import com.matatoa.dopaminetasks.core.database.entity.TaskEntity
import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.core.dataresult.asResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TaskRepository {
    suspend fun addTask(task: TaskEntity): DataResult<Unit>
    suspend fun deleteTask(id: Long): DataResult<Unit>
    suspend fun deleteTasksBefore(beforeMillis: Long): DataResult<Unit>
    suspend fun updateTaskDone(id: Long, isDone: Boolean): DataResult<Unit>
    fun fetchTasks(startMillis: Long, endMillis: Long): Flow<DataResult<List<TaskEntity>>>
}

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
) : TaskRepository {
    override suspend fun addTask(task: TaskEntity): DataResult<Unit> = try {
        taskDao.addTask(task = task)
        DataResult.Success(data = Unit)
    } catch (e: SQLiteException) {
        DataResult.Error(error = e)
    }

    override suspend fun deleteTask(id: Long): DataResult<Unit> = try {
        taskDao.deleteTask(id = id)
        DataResult.Success(data = Unit)
    } catch (e: SQLiteException) {
        DataResult.Error(error = e)
    }

    override suspend fun deleteTasksBefore(beforeMillis: Long): DataResult<Unit> = try {
        taskDao.deleteTasksBefore(beforeMillis = beforeMillis)
        DataResult.Success(data = Unit)
    } catch (e: SQLiteException) {
        DataResult.Error(error = e)
    }

    override suspend fun updateTaskDone(id: Long, isDone: Boolean): DataResult<Unit> = try {
        taskDao.updateTaskDone(id = id, isDone = isDone)
        DataResult.Success(data = Unit)
    } catch (e: SQLiteException) {
        DataResult.Error(error = e)
    }

    override fun fetchTasks(
        startMillis: Long,
        endMillis: Long,
    ): Flow<DataResult<List<TaskEntity>>> =
        taskDao.fetchTasks(startMillis = startMillis, endMillis = endMillis).asResult()
}
