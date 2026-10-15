package com.matatoa.dopaminetasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.matatoa.dopaminetasks.theme.DopamineTasksTheme
import com.matatoa.dopaminetasks.ui.TaskRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DopamineTasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskRoot(
                        modifier = Modifier
                            .padding(paddingValues = innerPadding),
                    )
                }
            }
        }
    }
}
