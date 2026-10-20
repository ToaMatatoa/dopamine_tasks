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
fun TaskCompletedDialog(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.action_ok))
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun TaskCompletedDialogPreview() {
    DopamineTasksTheme {
        TaskCompletedDialog(
            message = "You've completed all tasks for today! That's real dopamine!",
            onDismiss = {},
        )
    }
}
