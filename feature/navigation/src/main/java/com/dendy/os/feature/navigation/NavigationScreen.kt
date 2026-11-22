package com.dendy.os.feature.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dendy.os.core.designsystem.components.DendyGlassCard
import com.dendy.os.core.designsystem.components.DendyIconButton
import com.dendy.os.core.designsystem.components.DendyStatusBadge
import com.dendy.os.core.designsystem.theme.DendyShapes
import com.dendy.os.core.designsystem.theme.DendyTheme

@Composable
fun NavigationScreen(
    onNavigateBack: () -> Unit,
    onLaunch2GisNativeApp: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var selectedProvider by remember { mutableStateOf("2GIS") } // "2GIS" or "DENDY"

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE2E8F0), Color(0xFFCBD5E1))
                )
            )
    ) {
        // Map Surface Layer
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // Top Navigation HUD Panel (2GIS Styled)
            DendyGlassCard(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .width(460.dp),
                shape = DendyShapes.large,
                elevation = 6.dp
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Header with 2GIS Badge & Direct Launch Icon
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(DendyShapes.small)
                                    .background(Color(0xFF00A859)) // 2GIS signature green
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "2ГИС Навигатор",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Алматы • Пробки 4 балла",
                                style = DendyTheme.typography.statusCaption,
                                color = DendyTheme.colors.textSecondary
                            )
                        }

                        if (onLaunch2GisNativeApp != null) {
                            Row(
                                modifier = Modifier
                                    .clip(DendyShapes.full)
                                    .background(DendyTheme.colors.surfaceHighlight)
                                    .clickable(onClick = onLaunch2GisNativeApp)
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Открыть в 2ГИС",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = DendyTheme.colors.accentPrimary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.OpenInNew,
                                    contentDescription = null,
                                    tint = DendyTheme.colors.accentPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Next Maneuver Info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF00A859)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.NearMe,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "Через 450 метров",
                                style = DendyTheme.typography.cardTitle,
                                color = DendyTheme.colors.textPrimary
                            )
                            Text(
                                text = "Поверните направо на Аль-Фараби",
                                style = DendyTheme.typography.bodyLarge,
                                color = DendyTheme.colors.textSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DendyStatusBadge(
                            text = "Камера 60 км/ч в спину",
                            icon = Icons.Default.Speed,
                            badgeColor = Color(0xFFFEF3C7),
                            textColor = Color(0xFFB45309)
                        )
                        Text(
                            text = "Прибытие в 15:12 • 12.4 км",
                            style = DendyTheme.typography.labelBold,
                            color = DendyTheme.colors.textPrimary
                        )
                    }
                }
            }

            // Quick 2GIS Search Shortcuts
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickLocationChip("⛽ АЗС QazaqOil (1.2 км)")
                QuickLocationChip("🅿️ Парковки (18 мест)")
                QuickLocationChip("🏢 Dostyk Plaza (4.1 км)")
                QuickLocationChip("🏔️ Медеу (16 км)")
            }

            // Right Map Controls
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DendyIconButton(
                    icon = Icons.Default.Add,
                    contentDescription = "Zoom In",
                    onClick = {},
                    size = 52.dp
                )
                DendyIconButton(
                    icon = Icons.Default.Remove,
                    contentDescription = "Zoom Out",
                    onClick = {},
                    size = 52.dp
                )
                DendyIconButton(
                    icon = Icons.Default.DirectionsCar,
                    contentDescription = "Мое авто",
                    onClick = {},
                    size = 52.dp,
                    isActive = true
                )
            }
        }
    }
}

@Composable
private fun QuickLocationChip(label: String) {
    DendyGlassCard(
        backgroundColor = Color(0xEAFFFFFF),
        shape = DendyShapes.full,
        onClick = {}
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = DendyTheme.colors.textPrimary,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}
