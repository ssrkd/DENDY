package com.dendy.os.feature.navigation

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE2E8F0), Color(0xFFCBD5E1))
                )
            )
    ) {
        // Map Surface Placeholder (Clean vector vector styling)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Top Navigation HUD Panel
            DendyGlassCard(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .width(420.dp),
                shape = DendyShapes.large,
                elevation = 6.dp
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(DendyTheme.colors.accentPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.NearMe,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
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

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DendyStatusBadge(
                            text = "Скорость 60 км/ч",
                            icon = Icons.Default.Speed,
                            textColor = DendyTheme.colors.accentPrimary
                        )
                        Text(
                            text = "Прибытие в 15:12",
                            style = DendyTheme.typography.labelBold,
                            color = DendyTheme.colors.textPrimary
                        )
                    }
                }
            }

            // Quick Points of Interest (Gas, Parking, Route)
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
                    size = 54.dp
                )
                DendyIconButton(
                    icon = Icons.Default.Remove,
                    contentDescription = "Zoom Out",
                    onClick = {},
                    size = 54.dp
                )
                DendyIconButton(
                    icon = Icons.Default.LocalGasStation,
                    contentDescription = "АЗС",
                    onClick = {},
                    size = 54.dp
                )
                DendyIconButton(
                    icon = Icons.Default.LocalParking,
                    contentDescription = "Парковки",
                    onClick = {},
                    size = 54.dp
                )
            }
        }
    }
}
