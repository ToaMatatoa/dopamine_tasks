package com.matatoa.dopaminetasks.app.screens.habit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matatoa.dopaminetasks.R
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme
import compose.icons.TablerIcons
import compose.icons.tablericons.Edit
import compose.icons.tablericons.Trash

@Composable
fun HabitHeader(
    title: String,
    isFinished: Boolean,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val finishedMark = stringResource(id = R.string.habit_finished_mark)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = buildAnnotatedString {
                append(text = title)
                if (isFinished) {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(text = " $finishedMark")
                    }
                }
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .weight(1f),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (!isFinished) {
                HabitActionButton(
                    icon = TablerIcons.Edit,
                    contentDescription = stringResource(id = R.string.action_edit),
                    color = MaterialTheme.colorScheme.primary,
                    onClick = onEditClick,
                )
            }
            HabitActionButton(
                icon = TablerIcons.Trash,
                contentDescription = stringResource(id = R.string.action_delete),
                color = MaterialTheme.colorScheme.tertiary,
                onClick = onDeleteClick,
            )
        }
    }
}

@Composable
private fun HabitActionButton(
    icon: ImageVector,
    contentDescription: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedIconButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = color),
        border = BorderStroke(width = 1.dp, color = color),
        modifier = modifier
            .size(36.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(18.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E2523)
@Composable
private fun HabitHeaderPreview() {
    DopamineTasksTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            HabitHeader(
                title = "Run for at least twenty minutes every morning",
                isFinished = false,
                onEditClick = {},
                onDeleteClick = {},
            )
            HabitHeader(
                title = "Read at least ten pages of a book every evening before going to " +
                    "sleep, without a phone nearby, and write down one thought from those pages",
                isFinished = false,
                onEditClick = {},
                onDeleteClick = {},
            )
            HabitHeader(
                title = "A finished habit can only be deleted",
                isFinished = true,
                onEditClick = {},
                onDeleteClick = {},
            )
        }
    }
}
