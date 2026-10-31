package com.matatoa.dopaminetasks.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "habit_checks",
    primaryKeys = ["habit_id", "epoch_day"],
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habit_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class HabitCheckEntity(
    @ColumnInfo(name = "habit_id")
    val habitId: Long,
    @ColumnInfo(name = "epoch_day")
    val epochDay: Long,
)
