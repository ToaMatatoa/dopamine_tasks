package com.matatoa.dopaminetasks.designsystem

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
fun DeleteDialog(
    title: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    titleColor: Color = Color.Unspecified,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = titleColor,
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
private fun DeleteDialogImportantPreview() {
    DopamineTasksTheme {
        DeleteDialog(
            title = "Delete this habit?",
            titleColor = MaterialTheme.colorScheme.tertiary,
            onConfirm = {},
            onDismiss = {},
        )
    }
}

@Preview
@Composable
private fun DeleteDialogCommonPreview() {
    DopamineTasksTheme {
        DeleteDialog(
            title = "Delete this common task?",
            onConfirm = {},
            onDismiss = {},
        )
    }
}
