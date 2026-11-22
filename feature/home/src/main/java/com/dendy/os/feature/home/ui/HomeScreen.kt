package com.dendy.os.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dendy.os.core.designsystem.theme.DendyShapes
import com.dendy.os.core.designsystem.theme.DendyTheme
import com.dendy.os.core.media.MediaPlaybackState
import com.dendy.os.core.vehicle.model.VehicleState

@Composable
fun HomeScreen(
    vehicleState: VehicleState,
    mediaState: MediaPlaybackState,
    onPlayPause: () -> Unit,
    onNextTrack: () -> Unit,
    onPrevTrack: () -> Unit,
    onNavigateToSection: (String) -> Unit,
    onOpenAiAssistant: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DendyTheme.colors.background)
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Section: Clock & Weather + Quick Action Dock
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(104.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Clock & Weather banner (takes major width)
                ClockWeatherWidget(
                    modifier = Modifier.weight(1f),
                    outsideTemp = vehicleState.outsideTempCelsius
                )

                // Dedicated glowing DENDY AI button
                DendyAiTriggerPill(
                    onClick = onOpenAiAssistant,
                    modifier = Modifier.height(104.dp)
                )
            }

            // Central Dual-Pane Dashboard: Left (Navigation & Media), Right (Vehicle Diagnostics & Apps)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left Column: Navigation preview + Quick Media
                Column(
                    modifier = Modifier
                        .weight(1.1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    NavigationPreviewWidget(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = { onNavigateToSection("navigation") }
                    )

                    MediaWidget(
                        mediaState = mediaState,
                        onPlayPause = onPlayPause,
                        onNext = onNextTrack,
                        onPrev = onPrevTrack,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.1f),
                        onClick = { onNavigateToSection("music") }
                    )
                }

                // Right Column: Omoda C5 Vehicle Card + Quick Automotive Shortcuts
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    VehicleQuickCard(
                        vehicleState = vehicleState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.3f),
                        onClick = { onNavigateToSection("vehicle") }
                    )

                    // Quick App Shortcuts Shelf
                    QuickAppShelf(
                        onOpenMaps = { onNavigateToSection("navigation") },
                        onOpenMusic = { onNavigateToSection("music") },
                        onOpenClimate = { onNavigateToSection("climate") },
                        onOpenAllApps = { onNavigateToSection("applications") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.7f)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickAppShelf(
    onOpenMaps: () -> Unit,
    onOpenMusic: () -> Unit,
    onOpenClimate: () -> Unit,
    onOpenAllApps: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(DendyShapes.large)
            .background(DendyTheme.colors.surfaceGlass)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        QuickShelfItem(icon = Icons.Default.Map, label = "Карты", onClick = onOpenMaps)
        QuickShelfItem(icon = Icons.Default.MusicNote, label = "Музыка", onClick = onOpenMusic)
        QuickShelfItem(icon = Icons.Default.Radio, label = "Радио", onClick = onOpenMusic)
        QuickShelfItem(icon = Icons.Default.Call, label = "Телефон", onClick = onOpenAllApps)
        QuickShelfItem(icon = Icons.Default.Apps, label = "Все меню", onClick = onOpenAllApps)
    }
}

@Composable
private fun QuickShelfItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(DendyShapes.medium)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(DendyTheme.colors.surfaceHighlight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = DendyTheme.colors.iconPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = DendyTheme.typography.statusCaption,
            color = DendyTheme.colors.textSecondary
        )
    }
}

