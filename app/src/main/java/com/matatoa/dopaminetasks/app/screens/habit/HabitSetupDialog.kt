package com.matatoa.dopaminetasks.app.screens.habit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matatoa.dopaminetasks.R
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme
import com.matatoa.dopaminetasks.domain.model.HabitModel
import java.time.LocalDate
import kotlin.math.roundToInt

private val DaysRange =
    HabitModel.MIN_DAYS_TO_COMPLETE.toFloat()..HabitModel.MAX_DAYS_TO_COMPLETE.toFloat()

// With a habit it edits that habit, whose days are fixed; without one it creates a new habit.
@Composable
fun HabitSetupDialog(
    habit: HabitModel?,
    onConfirm: (tabTitle: String, title: String, daysToComplete: Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var tabTitle by rememberSaveable { mutableStateOf(value = habit?.tabTitle.orEmpty()) }
    var title by rememberSaveable { mutableStateOf(value = habit?.title.orEmpty()) }
    var days by rememberSaveable {
        mutableIntStateOf(value = habit?.daysToComplete ?: HabitModel.MIN_DAYS_TO_COMPLETE)
    }
    val isConfirmEnabled = tabTitle.isNotBlank() && title.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(
                    id = if (habit == null) R.string.habit_setup else R.string.habit_edit,
                ),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState()),
            ) {
                HabitTextField(
                    value = tabTitle,
                    onValueChange = { tabTitle = it },
                    label = stringResource(id = R.string.habit_tab_title_label),
                    maxLength = HabitModel.TAB_TITLE_MAX_LENGTH,
                    singleLine = true,
                )
                HabitTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = stringResource(id = R.string.habit_text_label),
                    maxLength = HabitModel.TITLE_MAX_LENGTH,
                    singleLine = false,
                )
                if (habit == null) {
                    HabitDaysPicker(
                        days = days,
                        onDaysChange = { days = it },
                    )
                } else {
                    Text(
                        text = habitDaysText(days = days),
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(tabTitle.trim(), title.trim(), days) },
                enabled = isConfirmEnabled,
            ) {
                Text(
                    text = stringResource(
                        id = if (habit == null) R.string.action_add else R.string.action_save,
                    ),
                    color = if (isConfirmEnabled) {
                        MaterialTheme.colorScheme.tertiary
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
}

@Composable
private fun HabitTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLength: Int,
    singleLine: Boolean,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it.take(n = maxLength)) },
        label = { Text(text = label) },
        supportingText = {
            Text(
                text = stringResource(id = R.string.habit_text_counter, value.length, maxLength),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        },
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else 4,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.tertiary,
            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.tertiary,
        ),
        modifier = modifier
            .fillMaxWidth(),
    )
}

@Composable
private fun HabitDaysPicker(
    days: Int,
    onDaysChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = habitDaysText(days = days),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = HabitModel.MIN_DAYS_TO_COMPLETE.toString(),
                style = MaterialTheme.typography.labelMedium,
            )
            Slider(
                value = days.toFloat(),
                onValueChange = { onDaysChange(it.roundToInt()) },
                valueRange = DaysRange,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.tertiary,
                    activeTrackColor = MaterialTheme.colorScheme.tertiary,
                ),
                modifier = Modifier
                    .weight(1f),
            )
            Text(
                text = HabitModel.MAX_DAYS_TO_COMPLETE.toString(),
                style = MaterialTheme.typography.labelMedium,
            )
        }
        Text(
            text = stringResource(id = R.string.habit_days_helper),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

@Composable
private fun habitDaysText(days: Int): String =
    pluralStringResource(id = R.plurals.habit_days_count, count = days, days)

@Preview
@Composable
private fun HabitSetupDialogCreatePreview() {
    DopamineTasksTheme {
        HabitSetupDialog(
            habit = null,
            onConfirm = { _, _, _ -> },
            onDismiss = {},
        )
    }
}

@Preview
@Composable
private fun HabitSetupDialogEditPreview() {
    val habit = HabitModel(
        id = 1,
        tabNumber = 1,
        tabTitle = "Morning run",
        title = "Run for at least twenty minutes every morning",
        startDate = LocalDate.of(2026, 10, 31),
        daysToComplete = 21,
    )
    DopamineTasksTheme {
        HabitSetupDialog(
            habit = habit,
            onConfirm = { _, _, _ -> },
            onDismiss = {},
        )
    }
}
