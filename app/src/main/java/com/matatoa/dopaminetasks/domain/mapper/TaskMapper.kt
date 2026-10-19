package com.matatoa.dopaminetasks.domain.mapper

import com.matatoa.dopaminetasks.core.database.entity.TaskEntity
import com.matatoa.dopaminetasks.domain.model.TaskModel

fun TaskModel.toTaskEntity(): TaskEntity =
    TaskEntity(
        id = id,
        title = title,
        isImportant = isImportant,
        isDone = isDone,
        date = date,
    )

fun TaskEntity.toTaskModel(): TaskModel =
    TaskModel(
        id = id,
        title = title,
        isImportant = isImportant,
        isDone = isDone,
        date = date,
    )
