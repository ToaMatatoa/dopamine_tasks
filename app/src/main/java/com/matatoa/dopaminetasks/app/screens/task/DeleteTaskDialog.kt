package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.matatoa.dopaminetasks.R
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme

@Composable
fun DeleteTaskDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.task_delete_question),
                style = MaterialTheme.typography.titleMedium,
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
private fun DeleteTaskDialogPreview() {
    DopamineTasksTheme {
        DeleteTaskDialog(
            onConfirm = {},
            onDismiss = {},
        )
    }
}
