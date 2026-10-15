package com.matatoa.dopaminetasks.app.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.matatoa.dopaminetasks.R
import compose.icons.TablerIcons
import compose.icons.tablericons.ListCheck
import compose.icons.tablericons.Target

enum class BottomTab(
    val route: Route,
    @param:StringRes val labelRes: Int,
    val icon: ImageVector,
) {
    Tasks(
        route = Route.TaskScreen,
        labelRes = R.string.nav_tasks,
        icon = TablerIcons.ListCheck,
    ),
    Habits(
        route = Route.HabitScreen,
        labelRes = R.string.nav_habits,
        icon = TablerIcons.Target,
    ),
}
