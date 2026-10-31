package com.matatoa.dopaminetasks.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.matatoa.dopaminetasks.core.database.dao.HabitDao
import com.matatoa.dopaminetasks.core.database.dao.TaskDao
import com.matatoa.dopaminetasks.core.database.entity.HabitCheckEntity
import com.matatoa.dopaminetasks.core.database.entity.HabitEntity
import com.matatoa.dopaminetasks.core.database.entity.TaskEntity

@Database(
    entities = [TaskEntity::class, HabitEntity::class, HabitCheckEntity::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
    ],
)
abstract class DopamineTaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    abstract fun habitDao(): HabitDao
}
