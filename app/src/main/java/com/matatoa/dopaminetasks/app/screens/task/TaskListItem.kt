package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme

@Composable
fun TaskListItem(
    title: String,
    isDone: Boolean,
    isImportant: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val titleColor by animateColorAsState(
        targetValue = if (isDone) {
            MaterialTheme.colorScheme.onSurfaceVariant
        } else {
            MaterialTheme.colorScheme.onSurface
        },
        label = "titleColor",
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        border = if (isImportant) {
            BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.tertiary)
        } else {
            null
        },
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = titleColor,
            textDecoration = if (isDone) TextDecoration.LineThrough else null,
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick,
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E2523)
@Composable
private fun TaskListItemPreview() {
    DopamineTasksTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp),
        ) {
            TaskListItem(
                title = "Important task",
                isDone = false,
                isImportant = true,
                onClick = {},
                onLongClick = {},
            )
            TaskListItem(
                title = "Common task with a long title that goes on to a second line " +
                    "because there is no max lines limit",
                isDone = false,
                isImportant = false,
                onClick = {},
                onLongClick = {},
            )
            TaskListItem(
                title = "Completed task",
                isDone = true,
                isImportant = false,
                onClick = {},
                onLongClick = {},
            )
        }
    }
}
