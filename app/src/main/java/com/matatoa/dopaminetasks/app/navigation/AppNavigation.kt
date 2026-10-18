package com.matatoa.dopaminetasks.app.navigation

import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.matatoa.dopaminetasks.app.screens.habits.HabitRoot
import com.matatoa.dopaminetasks.app.screens.task.TaskRoot

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navigationState = rememberNavigationState(startRoute = Route.TaskScreen)
    val navigator = remember(navigationState) { Navigator(state = navigationState) }

    // No top inset here: each screen's TopBar draws behind the status bar itself.
    // The bottom one is taken by BottomBar.
    Scaffold(
        bottomBar = {
            BottomBar(
                currentRoute = navigationState.currentRoute,
                onTabClick = navigator::navigateToTab,
            )
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets
            .only(sides = WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom),
        modifier = modifier
            .fillMaxSize(),
    ) { innerPadding ->
        NavDisplay(
            backStack = navigationState.backStack,
            onBack = navigator::goBack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                entry<Route.TaskScreen> {
                    TaskRoot()
                }

                entry<Route.HabitScreen> {
                    HabitRoot()
                }
            },
            modifier = Modifier
                .padding(paddingValues = innerPadding),
        )
    }
}
