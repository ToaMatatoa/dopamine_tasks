package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matatoa.dopaminetasks.R
import com.matatoa.dopaminetasks.designsystem.TopBar
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme
import com.matatoa.dopaminetasks.domain.model.TaskModel

@Composable
fun TaskScreen(
    state: TaskScreenState,
    onTaskClick: (TaskModel) -> Unit,
    onTaskDelete: (TaskModel) -> Unit,
    onAddTaskClick: (isImportant: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var taskToDeleteId by rememberSaveable { mutableStateOf<Long?>(value = null) }
    val (importantTasks, commonTasks) = remember(state.tasks) {
        state.tasks.partition { it.isImportant }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        TopBar(
            title = stringResource(id = R.string.tasks_title),
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(all = 16.dp),
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            item(key = "important_header") {
                if (importantTasks.isEmpty()) {
                    AddFirstTaskButton(
                        text = stringResource(id = R.string.tasks_important_empty),
                        onClick = { onAddTaskClick(true) },
                        contentColor = MaterialTheme.colorScheme.tertiary,
                    )
                } else {
                    TaskSectionHeader(
                        title = stringResource(
                            id = R.string.tasks_important_title,
                            importantTasks.size,
                            TaskModel.IMPORTANT_MAX_COUNT,
                        ),
                        isAddEnabled = importantTasks.size < TaskModel.IMPORTANT_MAX_COUNT,
                        onAddClick = { onAddTaskClick(true) },
                        contentColor = MaterialTheme.colorScheme.tertiary,
                    )
                }
            }

            items(
                items = importantTasks,
                key = { it.id },
            ) { task ->
                TaskListItem(
                    title = task.title,
                    isDone = task.isDone,
                    isImportant = task.isImportant,
                    onClick = { onTaskClick(task) },
                    onLongClick = { taskToDeleteId = task.id },
                )
            }

            item(key = "common_header") {
                if (commonTasks.isEmpty()) {
                    AddFirstTaskButton(
                        text = stringResource(id = R.string.tasks_common_empty),
                        onClick = { onAddTaskClick(false) },
                        modifier = Modifier
                            .padding(top = 8.dp),
                    )
                } else {
                    TaskSectionHeader(
                        title = stringResource(
                            id = R.string.tasks_common_title,
                            commonTasks.size,
                            TaskModel.COMMON_MAX_COUNT,
                        ),
                        isAddEnabled = commonTasks.size < TaskModel.COMMON_MAX_COUNT,
                        onAddClick = { onAddTaskClick(false) },
                        modifier = Modifier
                            .padding(top = 8.dp),
                    )
                }
            }

            items(
                items = commonTasks,
                key = { it.id },
            ) { task ->
                TaskListItem(
                    title = task.title,
                    isDone = task.isDone,
                    isImportant = task.isImportant,
                    onClick = { onTaskClick(task) },
                    onLongClick = { taskToDeleteId = task.id },
                )
            }
        }
    }

    state.tasks.find { it.id == taskToDeleteId }?.let { task ->
        DeleteTaskDialog(
            onConfirm = {
                onTaskDelete(task)
                taskToDeleteId = null
            },
            onDismiss = { taskToDeleteId = null },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskScreenPreview() {
    DopamineTasksTheme {
        TaskScreen(
            state = TaskScreenState(
                tasks = listOf(
                    TaskModel(
                        id = 0,
                        title = "First Task",
                        isImportant = true,
                        isDone = false,
                        date = 11031996,
                    ),
                    TaskModel(
                        id = 1,
                        title = "Second Task",
                        isImportant = true,
                        isDone = false,
                        date = 11031996,
                    ),
                    TaskModel(
                        id = 2,
                        title = "Third Task",
                        isImportant = true,
                        isDone = false,
                        date = 11031996,
                    ),
                    TaskModel(
                        id = 3,
                        title = "Fourth Task",
                        isImportant = false,
                        isDone = true,
                        date = 11031996,
                    ),
                    TaskModel(
                        id = 4,
                        title = "Fifth Task",
                        isImportant = false,
                        isDone = false,
                        date = 11031996,
                    ),
                ),
            ),
            onTaskClick = {},
            onTaskDelete = {},
            onAddTaskClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskScreenEmptyPreview() {
    DopamineTasksTheme {
        TaskScreen(
            state = TaskScreenState(),
            onTaskClick = {},
            onTaskDelete = {},
            onAddTaskClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskScreenOnlyImportantPreview() {
    DopamineTasksTheme {
        TaskScreen(
            state = TaskScreenState(
                tasks = listOf(
                    TaskModel(
                        id = 0,
                        title = "First Task",
                        isImportant = true,
                        isDone = false,
                        date = 11031996,
                    ),
                    TaskModel(
                        id = 1,
                        title = "Second Task",
                        isImportant = true,
                        isDone = true,
                        date = 11031996,
                    ),
                ),
            ),
            onTaskClick = {},
            onTaskDelete = {},
            onAddTaskClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskScreenOnlyCommonPreview() {
    DopamineTasksTheme {
        TaskScreen(
            state = TaskScreenState(
                tasks = listOf(
                    TaskModel(
                        id = 0,
                        title = "First Task",
                        isImportant = false,
                        isDone = false,
                        date = 11031996,
                    ),
                    TaskModel(
                        id = 1,
                        title = "Second Task",
                        isImportant = false,
                        isDone = true,
                        date = 11031996,
                    ),
                    TaskModel(
                        id = 2,
                        title = "Third Task",
                        isImportant = false,
                        isDone = false,
                        date = 11031996,
                    ),
                ),
            ),
            onTaskClick = {},
            onTaskDelete = {},
            onAddTaskClick = {},
        )
    }
}
