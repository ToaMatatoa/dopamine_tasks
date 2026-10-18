package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme
import compose.icons.TablerIcons
import compose.icons.tablericons.Plus

@Composable
fun AddFirstTaskButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.primary,
) {
    OutlinedButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor,
        ),
        border = BorderStroke(width = 1.dp, color = contentColor),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Icon(
            imageVector = TablerIcons.Plus,
            contentDescription = null,
            modifier = Modifier
                .size(18.dp),
        )
        Spacer(
            modifier = Modifier
                .width(8.dp),
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E2523)
@Composable
private fun AddFirstTaskButtonPreview() {
    DopamineTasksTheme {
        AddFirstTaskButton(
            text = "Add an important task",
            onClick = {},
            contentColor = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(16.dp),
        )
    }
}
