package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matatoa.dopaminetasks.R
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme
import compose.icons.TablerIcons
import compose.icons.tablericons.Plus

@Composable
fun TaskSectionHeader(
    title: String,
    isAddEnabled: Boolean,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.primary,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = contentColor,
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
        )

        OutlinedIconButton(
            onClick = onAddClick,
            enabled = isAddEnabled,
            shape = MaterialTheme.shapes.medium,
            colors = IconButtonDefaults.outlinedIconButtonColors(
                contentColor = contentColor,
                disabledContentColor = MaterialTheme.colorScheme.outline,
            ),
            border = BorderStroke(
                width = 1.dp,
                color = if (isAddEnabled) contentColor else MaterialTheme.colorScheme.outline,
            ),
        ) {
            Icon(
                imageVector = TablerIcons.Plus,
                contentDescription = stringResource(id = R.string.action_add),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E2523)
@Composable
private fun TaskSectionHeaderPreview() {
    DopamineTasksTheme {
        TaskSectionHeader(
            title = "Important (max 3)",
            isAddEnabled = true,
            onAddClick = {},
        )
    }
}
