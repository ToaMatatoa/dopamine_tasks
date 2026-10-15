package com.matatoa.dopaminetasks.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// One scheme for day and night; tertiary is the "important" accent (important tasks, habits)
private val ColorScheme = lightColorScheme(
    primary = JungleTeal,
    onPrimary = Color.White,
    primaryContainer = FrozenWater,
    onPrimaryContainer = DeepForest,
    inversePrimary = MutedTeal,
    secondary = MutedTeal,
    onSecondary = DeepForest,
    secondaryContainer = AzureMist,
    onSecondaryContainer = DeepForest,
    tertiary = CoralRed,
    onTertiary = Color.White,
    tertiaryContainer = CoralBlush,
    onTertiaryContainer = DeepForest,
    background = MintCream,
    onBackground = DeepForest,
    surface = MintCream,
    onSurface = DeepForest,
    surfaceVariant = AzureMist,
    onSurfaceVariant = MossGrey,
    surfaceTint = JungleTeal,
    inverseSurface = DeepForest,
    inverseOnSurface = AzureMist,
    outline = MossGrey,
    outlineVariant = FrozenWater,
    surfaceBright = MintCream,
    surfaceDim = FrozenWater,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = MintCream,
    surfaceContainer = AzureMist,
    surfaceContainerHigh = AzureMist,
    surfaceContainerHighest = FrozenWater,
)

@Composable
fun DopamineTasksTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content,
    )
}
