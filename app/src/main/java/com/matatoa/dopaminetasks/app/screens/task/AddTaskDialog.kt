package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.matatoa.dopaminetasks.R
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme
import com.matatoa.dopaminetasks.domain.model.TaskModel
import kotlinx.coroutines.delay

@Composable
fun AddTaskDialog(
    isImportant: Boolean,
    onConfirm: (title: String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var title by rememberSaveable { mutableStateOf(value = "") }
    val focusRequester = remember { FocusRequester() }
    val accentColor = if (isImportant) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.primary
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(
                    id = if (isImportant) {
                        R.string.tasks_important_empty
                    } else {
                        R.string.tasks_common_empty
                    },
                ),
                style = MaterialTheme.typography.titleMedium,
                color = accentColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        },
        text = {
            OutlinedTextField(
                value = title,
                onValueChange = { newTitle ->
                    title = newTitle.take(n = TaskModel.TITLE_MAX_LENGTH)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (title.isNotBlank()) onConfirm(title.trim())
                    },
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    cursorColor = accentColor,
                ),
                modifier = Modifier
                    .focusRequester(focusRequester = focusRequester),
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(title.trim()) },
                enabled = title.isNotBlank(),
            ) {
                Text(
                    text = stringResource(id = R.string.action_add),
                    color = if (title.isNotBlank()) {
                        accentColor
                    } else {
                        MaterialTheme.colorScheme.outline
                    },
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.action_cancel))
            }
        },
        modifier = modifier,
    )

    LaunchedEffect(Unit) {
        delay(timeMillis = 100L)
        focusRequester.requestFocus()
    }
}

@Preview
@Composable
private fun AddTaskDialogImportantPreview() {
    DopamineTasksTheme {
        AddTaskDialog(
            isImportant = true,
            onConfirm = {},
            onDismiss = {},
        )
    }
}

@Preview
@Composable
private fun AddTaskDialogCommonPreview() {
    DopamineTasksTheme {
        AddTaskDialog(
            isImportant = false,
            onConfirm = {},
            onDismiss = {},
        )
    }
}
