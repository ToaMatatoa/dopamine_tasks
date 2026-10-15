package com.matatoa.dopaminetasks.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.matatoa.dopaminetasks.core.database.dao.TaskDao
import com.matatoa.dopaminetasks.core.database.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class DopamineTaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}
