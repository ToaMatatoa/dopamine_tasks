package com.matatoa.dopaminetasks.app.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.matatoa.dopaminetasks.R
import com.matatoa.dopaminetasks.designsystem.TopBar
import com.matatoa.dopaminetasks.designsystem.theme.DopamineTasksTheme

@Composable
fun TaskScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        TopBar(
            title = stringResource(id = R.string.tasks_title),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskScreenPreview() {
    DopamineTasksTheme {
        TaskScreen()
    }
}
