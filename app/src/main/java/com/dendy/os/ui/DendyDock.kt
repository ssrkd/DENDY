package com.dendy.os.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dendy.os.core.designsystem.theme.DendyShapes
import com.dendy.os.core.designsystem.theme.DendyTheme

/**
 * Ergonomic Left-hand Automotive Dock Bar:
 * Optimized for Left-Hand Drive (LHD) vehicles like the Omoda C5,
 * placing primary controls closest to the driver's hand (40-60 cm reaching distance).
 */
@Composable
fun DendyAutomotiveDock(
    currentSection: String,
    onSelectSection: (String) -> Unit,
    onTriggerAi: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(88.dp)
            .fillMaxHeight()
            .background(DendyTheme.colors.surface)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Primary Quick Docks
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DockItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = currentSection == "home",
                onClick = { onSelectSection("home") }
            )
            DockItem(
                icon = Icons.Default.Map,
                label = "Navigation",
                isSelected = currentSection == "navigation",
                onClick = { onSelectSection("navigation") }
            )
            DockItem(
                icon = Icons.Default.MusicNote,
                label = "Music",
                isSelected = currentSection == "music",
                onClick = { onSelectSection("music") }
            )
            DockItem(
                icon = Icons.Default.DirectionsCar,
                label = "Vehicle",
                isSelected = currentSection == "vehicle",
                onClick = { onSelectSection("vehicle") }
            )
            DockItem(
                icon = Icons.Default.AcUnit,
                label = "Climate",
                isSelected = currentSection == "climate",
                onClick = { onSelectSection("climate") }
            )
            DockItem(
                icon = Icons.Default.Apps,
                label = "Applications",
                isSelected = currentSection == "applications",
                onClick = { onSelectSection("applications") }
            )
        }

        // Bottom Utilities: DENDY AI + Settings
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Glowing DENDY AI Voice Trigger Orb
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF0284C7), Color(0xFF6366F1), Color(0xFF8B5CF6))
                        )
                    )
                    .clickable(onClick = onTriggerAi),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "DENDY AI",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            DockItem(
                icon = Icons.Default.Settings,
                label = "Settings",
                isSelected = currentSection == "settings",
                onClick = { onSelectSection("settings") }
            )
        }
    }
}

@Composable
private fun DockItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val activeColor = DendyTheme.colors.accentPrimary
    val inactiveBg = Color.Transparent
    val activeBg = DendyTheme.colors.surfaceHighlight

    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(DendyShapes.medium)
            .background(if (isSelected) activeBg else inactiveBg)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) activeColor else DendyTheme.colors.iconSecondary,
            modifier = Modifier.size(28.dp)
        )
    }
}
