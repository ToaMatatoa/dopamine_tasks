package com.matatoa.dopaminetasks.domain.model

data class TaskModel(
    val id: Long,
    val title: String,
    val isImportant: Boolean,
    val isDone: Boolean = false,
    val date: Long,
) {
    companion object {
        const val TITLE_MAX_LENGTH = 120
        const val IMPORTANT_MAX_COUNT = 3
        const val COMMON_MAX_COUNT = 15
    }
}
