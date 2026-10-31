package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TaskDateRow(
    dates: List<LocalDate>,
    today: LocalDate,
    selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val density = LocalDensity.current
    val selectedIndex = dates.indexOf(selectedDate)
    var isInitialScrollDone by remember { mutableStateOf(value = false) }

    LaunchedEffect(selectedIndex) {
        // The viewport is 0 until the first layout, and item offsets are measured from the
        // end of the content padding, so the padding is part of the centering offset.
        val viewportWidth = snapshotFlow { listState.layoutInfo.viewportSize.width }
            .first { it > 0 }
        val itemWidth = with(receiver = density) { 72.dp.roundToPx() }
        val padding = with(receiver = density) { 16.dp.roundToPx() }
        val centerOffset = padding - (viewportWidth - itemWidth) / 2

        if (isInitialScrollDone) {
            listState.animateScrollToItem(index = selectedIndex, scrollOffset = centerOffset)
        } else {
            listState.scrollToItem(index = selectedIndex, scrollOffset = centerOffset)
            isInitialScrollDone = true
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(
            items = dates,
            key = { it.toEpochDay() },
        ) { date ->
            DateItem(
                date = date,
                isToday = date == today,
                isSelected = date == selectedDate,
                onClick = { onDateClick(date) },
            )
        }
    }
}

@Composable
private fun DateItem(
    date: LocalDate,
    isToday: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val locale = LocalConfiguration.current.locales[0]
    val weekdayFormatter = remember(locale) { DateTimeFormatter.ofPattern("ccc", locale) }
    val monthFormatter = remember(locale) { DateTimeFormatter.ofPattern("LLL", locale) }

    Surface(
        selected = isSelected,
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceContainer
        },
        contentColor = if (isSelected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurface
        },
        border = if (isToday && !isSelected) {
            BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary)
        } else {
            null
        },
        modifier = modifier
            .width(width = 72.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 8.dp),
        ) {
            Text(
                text = date.format(weekdayFormatter),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = date.format(monthFormatter),
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E2523)
@Composable
private fun TaskDateRowPreview() {
    val today = LocalDate.of(2026, 11, 1)
    DopamineTasksTheme {
        TaskDateRow(
            dates = List(size = 7) { index -> today.plusDays(index - 2L) },
            today = today,
            selectedDate = today.plusDays(1),
            onDateClick = {},
        )
    }
}
