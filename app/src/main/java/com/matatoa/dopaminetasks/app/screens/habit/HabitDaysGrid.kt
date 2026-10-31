package com.matatoa.dopaminetasks.app.screens.habit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val DAYS_PER_ROW = 3

@Composable
fun HabitDaysGrid(
    days: List<HabitDay>,
    onTodayClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()
    val todayIndex = days.indexOfFirst { it.isCheckable }

    LaunchedEffect(todayIndex) {
        if (todayIndex >= 0) {
            // Keeps the previous row above today visible, so past days have context.
            gridState.scrollToItem(index = (todayIndex - DAYS_PER_ROW).coerceAtLeast(0))
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = DAYS_PER_ROW),
        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 16.dp),
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(
            items = days,
            key = { it.date.toEpochDay() },
        ) { day ->
            HabitDayItem(
                day = day,
                onClick = onTodayClick,
            )
        }
    }
}

@Composable
private fun HabitDayItem(
    day: HabitDay,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val locale = LocalConfiguration.current.locales[0]
    val weekdayFormatter = remember(locale) { DateTimeFormatter.ofPattern("ccc", locale) }
    val monthFormatter = remember(locale) { DateTimeFormatter.ofPattern("LLL", locale) }

    Surface(
        checked = day.isChecked,
        onCheckedChange = { onClick() },
        enabled = day.isCheckable,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = if (day.isCheckable || day.isChecked) {
            MaterialTheme.colorScheme.onSurface
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        border = if (day.isCheckable) {
            BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.tertiary)
        } else {
            null
        },
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 8.dp),
        ) {
            Text(
                text = day.date.format(weekdayFormatter),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = day.date.dayOfMonth.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = day.date.format(monthFormatter),
                style = MaterialTheme.typography.labelSmall,
            )
            Checkbox(
                checked = day.isChecked,
                onCheckedChange = null,
                enabled = day.isCheckable,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.tertiary,
                    uncheckedColor = MaterialTheme.colorScheme.tertiary,
                    checkmarkColor = MaterialTheme.colorScheme.onTertiary,
                    disabledCheckedColor = MaterialTheme.colorScheme.tertiary,
                    disabledUncheckedColor = MaterialTheme.colorScheme.outline,
                ),
                modifier = Modifier
                    .padding(vertical = 12.dp),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E2523)
@Composable
private fun HabitDaysGridPreview() {
    val startDate = LocalDate.of(2026, 10, 29)
    val today = startDate.plusDays(4)
    val days = List(size = 9) { index ->
        val date = startDate.plusDays(index.toLong())
        HabitDay(
            date = date,
            isChecked = date.isBefore(today) && index != 1,
            isCheckable = date == today,
        )
    }
    DopamineTasksTheme {
        HabitDaysGrid(
            days = days,
            onTodayClick = {},
        )
    }
}
