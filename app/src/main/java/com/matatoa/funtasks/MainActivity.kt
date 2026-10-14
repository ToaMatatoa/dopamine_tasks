package com.matatoa.funtasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.matatoa.funtasks.theme.FunTasksTheme
import com.matatoa.funtasks.ui.TaskRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FunTasksTheme {
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
