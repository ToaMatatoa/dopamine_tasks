package com.matatoa.dopaminetasks.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// One scheme for day and night: grey backgrounds, green foreground.
// tertiary is the "important" accent (important tasks, habits)
private val ColorScheme = darkColorScheme(
    primary = MutedTeal,
    onPrimary = Graphite,
    primaryContainer = JungleTeal,
    onPrimaryContainer = MintCream,
    inversePrimary = JungleTeal,
    secondary = FrozenWater,
    onSecondary = Graphite,
    secondaryContainer = GraphiteLight,
    onSecondaryContainer = FrozenWater,
    tertiary = CoralRed,
    onTertiary = MintCream,
    tertiaryContainer = CoralBlush,
    onTertiaryContainer = Graphite,
    background = Graphite,
    onBackground = MintCream,
    surface = Graphite,
    onSurface = MintCream,
    surfaceVariant = GraphiteLight,
    onSurfaceVariant = MutedTeal,
    surfaceTint = MutedTeal,
    inverseSurface = AzureMist,
    inverseOnSurface = Graphite,
    outline = MossGrey,
    outlineVariant = GraphiteLight,
    surfaceBright = GraphiteLight,
    surfaceDim = Graphite,
    surfaceContainerLowest = Graphite,
    surfaceContainerLow = Graphite,
    surfaceContainer = GraphiteLight,
    surfaceContainerHigh = GraphiteLight,
    surfaceContainerHighest = GraphiteLight,
)

@Composable
fun DopamineTasksTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content,
    )
}
