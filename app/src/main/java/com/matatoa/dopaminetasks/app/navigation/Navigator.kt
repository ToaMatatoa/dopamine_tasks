package com.matatoa.dopaminetasks.app.navigation

import androidx.navigation3.runtime.NavKey

class Navigator(
    val state: NavigationState,
) {
    fun navigate(route: NavKey) {
        if (state.backStack.lastOrNull() != route) {
            state.backStack.add(route)
        }
    }

    /**
     * Switches bottom bar tabs. The back stack is reset to the start route with the
     * selected tab on top, so system back from any tab returns to the start tab and
     * tabs never pile up.
     */
    fun navigateToTab(route: NavKey) {
        if (state.currentRoute == route) return

        while (state.canGoBack) {
            state.backStack.removeLastOrNull()
        }
        if (route != state.startRoute) {
            state.backStack.add(route)
        }
    }

    fun goBack() {
        if (state.canGoBack) {
            state.backStack.removeLastOrNull()
        }
    }
}
