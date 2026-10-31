package com.matatoa.dopaminetasks.app.screens.habit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matatoa.dopaminetasks.R
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme
import com.matatoa.dopaminetasks.domain.model.HabitModel
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HabitTabs(
    habits: List<HabitModel>,
    selectedTabNumber: Int,
    onTabClick: (tabNumber: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val density = LocalDensity.current
    val tabNumbers = remember { (1..HabitModel.MAX_COUNT).toList() }

    LaunchedEffect(selectedTabNumber) {
        // The viewport is 0 until the first layout, and item offsets are measured from the
        // end of the content padding, so the padding is part of the centering offset.
        val viewportWidth = snapshotFlow { listState.layoutInfo.viewportSize.width }
            .first { it > 0 }
        val tabWidth = with(density) { 176.dp.roundToPx() }
        val padding = with(density) { 16.dp.roundToPx() }
        listState.animateScrollToItem(
            index = tabNumbers.indexOf(selectedTabNumber),
            scrollOffset = padding - (viewportWidth - tabWidth) / 2,
        )
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(
            items = tabNumbers,
            key = { it },
        ) { tabNumber ->
            HabitTab(
                habit = habits.find { it.tabNumber == tabNumber },
                isSelected = tabNumber == selectedTabNumber,
                onClick = { onTabClick(tabNumber) },
            )
        }
    }
}

@Composable
private fun HabitTab(
    habit: HabitModel?,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val locale = LocalConfiguration.current.locales[0]
    val dateFormatter = remember(locale) { DateTimeFormatter.ofPattern("d MMM yyyy", locale) }
    val accentColor = if (habit != null) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.primary
    }
    val onAccentColor = if (habit != null) {
        MaterialTheme.colorScheme.onTertiary
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    Surface(
        selected = isSelected,
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) accentColor else MaterialTheme.colorScheme.surfaceContainer,
        contentColor = when {
            isSelected -> onAccentColor
            habit != null -> accentColor
            else -> MaterialTheme.colorScheme.onSurface
        },
        border = if (habit != null && !isSelected) {
            BorderStroke(width = 1.dp, color = accentColor)
        } else {
            null
        },
        modifier = modifier
            .width(width = 176.dp)
            .heightIn(min = 60.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp),
        ) {
            Text(
                text = habit?.tabTitle ?: stringResource(id = R.string.habit_tab_empty),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            habit?.let {
                Text(
                    text = it.startDate.format(dateFormatter),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E2523, widthDp = 600)
@Composable
private fun HabitTabsPreview() {
    val habit = HabitModel(
        id = 1,
        tabNumber = 1,
        tabTitle = "Morning run every da",
        title = "Run for at least twenty minutes every morning",
        startDate = LocalDate.of(2026, 10, 31),
        daysToComplete = 21,
    )
    DopamineTasksTheme {
        Column {
            HabitTabs(
                habits = emptyList(),
                selectedTabNumber = 1,
                onTabClick = {},
            )
            HabitTabs(
                habits = listOf(habit),
                selectedTabNumber = 1,
                onTabClick = {},
            )
            HabitTabs(
                habits = listOf(habit),
                selectedTabNumber = 2,
                onTabClick = {},
            )
        }
    }
}
