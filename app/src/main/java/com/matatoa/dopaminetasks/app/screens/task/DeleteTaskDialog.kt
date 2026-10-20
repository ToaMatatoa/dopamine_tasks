package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.matatoa.dopaminetasks.R
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme

@Composable
fun DeleteTaskDialog(
    isImportant: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(
                    id = if (isImportant) {
                        R.string.task_delete_important_question
                    } else {
                        R.string.task_delete_common_question
                    },
                ),
                style = MaterialTheme.typography.titleMedium,
                color = if (isImportant) MaterialTheme.colorScheme.tertiary else Color.Unspecified,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.action_delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.action_cancel))
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun DeleteTaskDialogImportantPreview() {
    DopamineTasksTheme {
        DeleteTaskDialog(
            isImportant = true,
            onConfirm = {},
            onDismiss = {},
        )
    }
}

@Preview
@Composable
private fun DeleteTaskDialogCommonPreview() {
    DopamineTasksTheme {
        DeleteTaskDialog(
            isImportant = false,
            onConfirm = {},
            onDismiss = {},
        )
    }
}
