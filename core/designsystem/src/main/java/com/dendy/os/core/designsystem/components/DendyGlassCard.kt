package com.dendy.os.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dendy.os.core.designsystem.theme.DendyShapes
import com.dendy.os.core.designsystem.theme.DendyTheme

/**
 * Premium automotive frosted-glass card.
 * High glare resistance with a subtle solid-alpha surface, hairline border, and clean corner curves.
 */
@Composable
fun DendyGlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = DendyShapes.medium,
    backgroundColor: Color = DendyTheme.colors.surfaceGlass,
    borderColor: Color = DendyTheme.colors.border,
    elevation: Dp = 2.dp,
    borderWidth: Dp = 1.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val clickableModifier = if (onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = shape,
                ambientColor = Color(0x0A000000),
                spotColor = Color(0x14000000)
            )
            .clip(shape)
            .background(backgroundColor)
            .border(
                border = BorderStroke(borderWidth, borderColor),
                shape = shape
            )
            .then(clickableModifier)
    ) {
        content()
    }
}
