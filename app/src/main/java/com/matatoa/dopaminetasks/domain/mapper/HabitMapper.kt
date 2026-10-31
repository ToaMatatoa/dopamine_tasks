package com.matatoa.dopaminetasks.domain.mapper

import com.matatoa.dopaminetasks.core.database.entity.HabitCheckEntity
import com.matatoa.dopaminetasks.core.database.entity.HabitEntity
import com.matatoa.dopaminetasks.domain.model.HabitModel
import java.time.LocalDate

fun HabitModel.toHabitEntity(): HabitEntity =
    HabitEntity(
        id = id,
        tabNumber = tabNumber,
        tabTitle = tabTitle,
        title = title,
        startEpochDay = startDate.toEpochDay(),
        daysToComplete = daysToComplete,
    )

fun HabitEntity.toHabitModel(): HabitModel =
    HabitModel(
        id = id,
        tabNumber = tabNumber,
        tabTitle = tabTitle,
        title = title,
        startDate = LocalDate.ofEpochDay(startEpochDay),
        daysToComplete = daysToComplete,
    )

fun HabitCheckEntity.toCheckedDate(): LocalDate =
    LocalDate.ofEpochDay(epochDay)
