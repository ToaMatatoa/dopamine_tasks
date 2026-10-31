package com.matatoa.dopaminetasks.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "habits",
    indices = [Index(value = ["tab_number"], unique = true)],
)
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "tab_number")
    val tabNumber: Int,
    @ColumnInfo(name = "tab_title")
    val tabTitle: String,
    val title: String,
    @ColumnInfo(name = "start_epoch_day")
    val startEpochDay: Long,
    @ColumnInfo(name = "days_to_complete")
    val daysToComplete: Int,
)
