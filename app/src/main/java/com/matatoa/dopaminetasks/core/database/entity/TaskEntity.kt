package com.matatoa.dopaminetasks.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    indices = [Index(value = ["date"])],
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    @ColumnInfo(name = "is_important")
    val isImportant: Boolean,
    @ColumnInfo(name = "is_done")
    val isDone: Boolean = false,
    val date: Long,
)
