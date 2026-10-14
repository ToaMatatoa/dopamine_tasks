package com.matatoa.funtasks.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.matatoa.funtasks.theme.FunTasksTheme

@Composable
fun TaskScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) { }
}

@Preview(showBackground = true)
@Composable
private fun TaskScreenPreview() {
    FunTasksTheme {
        TaskScreen()
    }
}
