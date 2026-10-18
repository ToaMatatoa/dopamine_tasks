package com.matatoa.dopaminetasks.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

/**
 * Holds the single back stack the app navigates within: [startRoute] sits at the
 * bottom of it and other routes are pushed on top.
 */
class NavigationState(
    val startRoute: NavKey,
    val backStack: NavBackStack<NavKey>,
) {
    /** False when [startRoute] is the only entry left, i.e. there is nothing to pop. */
    val canGoBack: Boolean
        get() = backStack.size > 1

    val currentRoute: NavKey?
        get() = backStack.lastOrNull()
}

// On Android routes are restored after process death through their @Serializable
// serializers, so there is no SerializersModule to register them in (unlike KMP).
@Composable
fun rememberNavigationState(startRoute: NavKey): NavigationState {
    val backStack = rememberNavBackStack(startRoute)

    return remember(startRoute, backStack) {
        NavigationState(
            startRoute = startRoute,
            backStack = backStack,
        )
    }
}
