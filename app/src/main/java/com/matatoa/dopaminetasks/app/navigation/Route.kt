package com.matatoa.dopaminetasks.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object TaskScreen : Route

    @Serializable
    data object HabitScreen : Route
}
