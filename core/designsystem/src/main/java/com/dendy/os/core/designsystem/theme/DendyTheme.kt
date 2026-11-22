package com.dendy.os.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp

object DendyShapes {
    val small = RoundedCornerShape(12.dp)
    val medium = RoundedCornerShape(20.dp)
    val large = RoundedCornerShape(28.dp)
    val full = RoundedCornerShape(999.dp)
}

@Composable
fun DendyTheme(
    darkTheme: Boolean = false, // Default is Light Theme as requested for DENDY OS
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DendyDarkColors else DendyLightColors

    CompositionLocalProvider(
        LocalDendyColors provides colors,
        LocalDendyTypography provides DendyTypography()
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme(
                primary = colors.accentPrimary,
                background = colors.background,
                surface = colors.surface
            ),
            content = content
        )
    }
}

object DendyTheme {
    val colors: DendyColorPalette
        @Composable
        @ReadOnlyComposable
        get() = LocalDendyColors.current

    val typography: DendyTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalDendyTypography.current

    val shapes: DendyShapes
        get() = DendyShapes
}
