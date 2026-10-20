package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        },
        confirmButton = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.6f),
                ) {
                    Text(
                        text = stringResource(id = R.string.action_ok),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
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
