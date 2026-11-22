package com.dendy.os.feature.home.ui

import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dendy.os.core.designsystem.components.DendyGlassCard
import com.dendy.os.core.designsystem.components.DendyIconButton
import com.dendy.os.core.designsystem.theme.DendyShapes
import com.dendy.os.core.designsystem.theme.DendyTheme
import com.dendy.os.core.media.MediaPlaybackState
import com.dendy.os.core.vehicle.model.DriveMode
import com.dendy.os.core.vehicle.model.VehicleState
import com.dendy.os.core.weather.model.WeatherInfo
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Premium Apple & Tesla Edition Home Screen:
 * Left Stage: Cinematic Interactive Omoda C5 3D Silhouette Canvas with TPMS & AWD Modes.
 * Right Stage: Fluid Apple CarPlay Cards for 2GIS (Astana) and Yandex Music ("Моя волна").
 * Bottom Dock: Tesla-style climate and media controls.
 */
@Composable
fun HomeScreen(
    vehicleState: VehicleState,
    mediaState: MediaPlaybackState,
    weatherInfo: WeatherInfo,
    onPlayPause: () -> Unit,
    onNextTrack: () -> Unit,
    onPrevTrack: () -> Unit,
    onSetDriveMode: (DriveMode) -> Unit,
    onNavigateToSection: (String) -> Unit,
    onOpenAiAssistant: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentTime by remember { mutableStateOf("15:25") }
    var currentDate by remember { mutableStateOf("Воскресенье, 22 ноября") }

    LaunchedEffect(Unit) {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale("ru"))
        while (true) {
            val now = Date()
            currentTime = timeFormat.format(now)
            currentDate = dateFormat.format(now).replaceFirstChar { it.uppercase() }
            delay(1000)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DendyTheme.colors.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // TOP APPLE ISLAND: Clock, Exact Geolocation (Astana), Weather & Phone Status
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Clock & Exact Geolocation
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = currentTime,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = DendyTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "•",
                    color = DendyTheme.colors.textTertiary
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${weatherInfo.cityName}, ${weatherInfo.country}",
                    style = DendyTheme.typography.labelBold,
                    color = DendyTheme.colors.textSecondary
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "•",
                    color = DendyTheme.colors.textTertiary
                )
                Spacer(modifier = Modifier.width(10.dp))
                // Live Weather Badge
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.WbCloudy,
                        contentDescription = null,
                        tint = DendyTheme.colors.accentPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "+${weatherInfo.temperatureCelsius.toInt()}°C ${weatherInfo.conditionDescription}",
                        style = DendyTheme.typography.labelBold,
                        color = DendyTheme.colors.textPrimary
                    )
                }
            }

            // Center AWD Mode Pill
            Box(
                modifier = Modifier
                    .clip(DendyShapes.full)
                    .background(DendyTheme.colors.surfaceHighlight)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "${vehicleState.driveMode.name} AWD",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DendyTheme.colors.accentPrimary
                )
            }

            // Right Connectivity
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "iPhone 15 Pro • 92%",
                    style = DendyTheme.typography.statusCaption,
                    color = DendyTheme.colors.textSecondary
                )
                Text(
                    text = "5G LTE Онлайн",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DendyTheme.colors.success
                )
            }
        }

        // MAIN CENTER STAGE: Left (Tesla 3D Car Visualizer) + Right (2GIS & Yandex Music Cards)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // LEFT: TESLA VEHICLE CANVAS
            DendyGlassCard(
                modifier = Modifier
                    .weight(1.1f)
                    .fillMaxHeight(),
                shape = DendyShapes.large
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Omoda C5 AWD",
                            style = DendyTheme.typography.cardTitle,
                            color = DendyTheme.colors.textPrimary
                        )
                        Text(
                            text = "Запас ${vehicleState.rangeRemainingKm} км • Бак ${vehicleState.fuelLevelPercent}%",
                            style = DendyTheme.typography.statusCaption,
                            color = DendyTheme.colors.textSecondary
                        )
                    }

                    // Vector Canvas of Omoda C5 with Wheels & Dynamic Ambient Glow
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val accentColor = when (vehicleState.driveMode) {
                            DriveMode.SPORT -> Color(0xFFEF4444)
                            DriveMode.ECO -> Color(0xFF10B981)
                            else -> DendyTheme.colors.accentPrimary
                        }

                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height

                            // Road Ground Glow
                            drawOval(
                                brush = Brush.radialGradient(
                                    colors = listOf(accentColor.copy(alpha = 0.35f), Color.Transparent),
                                    center = Offset(w * 0.5f, h * 0.82f),
                                    radius = w * 0.45f
                                ),
                                topLeft = Offset(w * 0.1f, h * 0.65f),
                                size = Size(w * 0.8f, h * 0.35f)
                            )

                            // Omoda C5 Silhouette Path
                            val carPath = Path().apply {
                                moveTo(w * 0.15f, h * 0.68f)
                                cubicTo(w * 0.20f, h * 0.68f, w * 0.22f, h * 0.45f, w * 0.35f, h * 0.42f)
                                cubicTo(w * 0.45f, h * 0.40f, w * 0.52f, h * 0.22f, w * 0.68f, h * 0.22f)
                                cubicTo(w * 0.80f, h * 0.22f, w * 0.85f, h * 0.38f, w * 0.90f, h * 0.50f)
                                cubicTo(w * 0.94f, h * 0.62f, w * 0.95f, h * 0.68f, w * 0.85f, h * 0.68f)
                                close()
                            }
                            drawPath(
                                path = carPath,
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFFE2E8F0), Color(0xFF94A3B8)),
                                    start = Offset(0f, 0f),
                                    end = Offset(w, h)
                                )
                            )

                            // Glass Coupe Window
                            val windowPath = Path().apply {
                                moveTo(w * 0.38f, h * 0.42f)
                                cubicTo(w * 0.48f, h * 0.40f, w * 0.55f, h * 0.26f, w * 0.68f, h * 0.26f)
                                cubicTo(w * 0.76f, h * 0.26f, w * 0.82f, h * 0.38f, w * 0.86f, h * 0.48f)
                                lineTo(w * 0.38f, h * 0.48f)
                                close()
                            }
                            drawPath(windowPath, color = Color(0xFF0F172A))

                            // Front Wheel (Left side in visual)
                            drawCircle(color = Color(0xFF1E293B), radius = 28f, center = Offset(w * 0.28f, h * 0.72f))
                            drawCircle(color = Color(0xFF0F172A), radius = 16f, center = Offset(w * 0.28f, h * 0.72f))

                            // Rear Wheel
                            drawCircle(color = Color(0xFF1E293B), radius = 28f, center = Offset(w * 0.78f, h * 0.72f))
                            drawCircle(color = Color(0xFF0F172A), radius = 16f, center = Offset(w * 0.78f, h * 0.72f))
                        }

                        // Floating TPMS badges around car
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(DendyShapes.small)
                                    .background(Color(0xE6FFFFFF))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "ПЕРЕД: ${vehicleState.frontLeftTire.pressureBar} bar",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DendyTheme.colors.success
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(DendyShapes.small)
                                    .background(Color(0xE6FFFFFF))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "ЗАД: ${vehicleState.rearLeftTire.pressureBar} bar",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DendyTheme.colors.success
                                )
                            }
                        }
                    }

                    // Drive Mode Switcher Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DriveModeButton(
                            name = "ECO",
                            isSelected = vehicleState.driveMode == DriveMode.ECO
                        ) { onSetDriveMode(DriveMode.ECO) }

                        DriveModeButton(
                            name = "NORMAL",
                            isSelected = vehicleState.driveMode == DriveMode.NORMAL
                        ) { onSetDriveMode(DriveMode.NORMAL) }

                        DriveModeButton(
                            name = "SPORT",
                            isSelected = vehicleState.driveMode == DriveMode.SPORT
                        ) { onSetDriveMode(DriveMode.SPORT) }
                    }
                }
            }

            // RIGHT: 2GIS ASTANA CARD & YANDEX MUSIC "МОЯ ВОЛНА" CARD
            Column(
                modifier = Modifier
                    .weight(1.3f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // 1. 2GIS Live Navigation Card (Astana)
                DendyGlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = DendyShapes.large,
                    onClick = { onNavigateToSection("navigation") }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(DendyShapes.small)
                                        .background(Color(0xFF00A859))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "2ГИС АСТАНА",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "просп. Мангилик Ел",
                                    style = DendyTheme.typography.labelBold,
                                    color = DendyTheme.colors.textPrimary
                                )
                            }

                            Text(
                                text = "14 мин • 9.8 км",
                                style = DendyTheme.typography.cardTitle.copy(fontSize = 14.sp),
                                color = DendyTheme.colors.textPrimary
                            )
                        }

                        // Maneuver & Lane Guidance Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(DendyShapes.medium)
                                .background(DendyTheme.colors.surfaceHighlight)
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF00A859)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.NearMe,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Через 350 м направо к ТРЦ Хан Шатыр",
                                    style = DendyTheme.typography.bodyLarge.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                                    color = DendyTheme.colors.textPrimary
                                )
                                Text(
                                    text = "Полосы: прямо, прямо, направо | Сергек 60",
                                    style = DendyTheme.typography.statusCaption,
                                    color = DendyTheme.colors.textSecondary
                                )
                            }
                        }
                    }
                }

                // 2. Yandex Music "Моя волна" Card
                DendyGlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.1f),
                    shape = DendyShapes.large,
                    onClick = { onNavigateToSection("music") }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(DendyShapes.small)
                                        .background(Color(0xFFFFCC00))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "ЯНДЕКС МУЗЫКА",
                                        color = Color.Black,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Поток «Моя волна»",
                                    style = DendyTheme.typography.statusCaption,
                                    color = DendyTheme.colors.textSecondary
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.GraphicEq,
                                contentDescription = null,
                                tint = Color(0xFFFFCC00),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Track title & play controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(DendyShapes.medium)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(Color(0xFFFFCC00), Color(0xFFFF0033), Color(0xFF6366F1))
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Я",
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = mediaState.currentTrack.title,
                                    style = DendyTheme.typography.cardTitle.copy(fontSize = 15.sp),
                                    color = DendyTheme.colors.textPrimary,
                                    maxLines = 1
                                )
                                Text(
                                    text = mediaState.currentTrack.artist,
                                    style = DendyTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                                    color = DendyTheme.colors.textSecondary,
                                    maxLines = 1
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                DendyIconButton(
                                    icon = Icons.Default.SkipPrevious,
                                    contentDescription = "Prev",
                                    onClick = onPrevTrack,
                                    size = 40.dp,
                                    iconSize = 18.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                DendyIconButton(
                                    icon = if (mediaState.currentTrack.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    onClick = onPlayPause,
                                    size = 48.dp,
                                    iconSize = 24.dp,
                                    isActive = true,
                                    activeColor = Color(0xFFFFCC00),
                                    iconTintActive = Color.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                DendyIconButton(
                                    icon = Icons.Default.SkipNext,
                                    contentDescription = "Next",
                                    onClick = onNextTrack,
                                    size = 40.dp,
                                    iconSize = 18.dp
                                )
                            }
                        }

                        // Progress line
                        LinearProgressIndicator(
                            progress = 0.4f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(DendyShapes.full),
                            color = Color(0xFFFFCC00),
                            trackColor = DendyTheme.colors.surfaceSubtle
                        )
                    }
                }
            }
        }

        // BOTTOM TESLA CLIMATE & APP DOCK
        DendyGlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            shape = DendyShapes.large
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Driver Climate Controls
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "♨ ${vehicleState.climate.driverSeatHeatingLevel}",
                        style = DendyTheme.typography.labelBold,
                        color = Color(0xFFF97316)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "${vehicleState.climate.driverTempCelsius.toInt()}°C",
                        style = DendyTheme.typography.cardTitle.copy(fontSize = 18.sp),
                        color = DendyTheme.colors.textPrimary
                    )
                }

                // Center Icons: 2GIS, Yandex Music, DENDY AI Orb, 360 Camera, Settings
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // 2GIS Shortcut
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(DendyShapes.medium)
                            .background(Color(0xFF00A859))
                            .clickable { onNavigateToSection("navigation") },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "2Г", color = Color.White, fontWeight = FontWeight.Black, fontSize = 14.sp)
                    }

                    // Yandex Music Shortcut
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(DendyShapes.medium)
                            .background(Color(0xFFFFCC00))
                            .clickable { onNavigateToSection("music") },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Я", color = Color.Black, fontWeight = FontWeight.Black, fontSize = 16.sp)
                    }

                    // DENDY AI Center Glowing Orb
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF0284C7), Color(0xFF6366F1), Color(0xFFA855F7))
                                )
                            )
                            .clickable(onClick = onOpenAiAssistant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "DENDY AI",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Vehicle Telemetry
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(DendyShapes.medium)
                            .background(DendyTheme.colors.surfaceHighlight)
                            .clickable { onNavigateToSection("vehicle") },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🚘", fontSize = 18.sp)
                    }

                    // Apps Drawer
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(DendyShapes.medium)
                            .background(DendyTheme.colors.surfaceHighlight)
                            .clickable { onNavigateToSection("applications") },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "📱", fontSize = 18.sp)
                    }
                }

                // Passenger Climate Controls
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${vehicleState.climate.passengerTempCelsius.toInt()}°C",
                        style = DendyTheme.typography.cardTitle.copy(fontSize = 18.sp),
                        color = DendyTheme.colors.textPrimary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "♨ ${vehicleState.climate.passengerSeatHeatingLevel}",
                        style = DendyTheme.typography.labelBold,
                        color = DendyTheme.colors.textTertiary
                    )
                }
            }
        }
    }
}

@Composable
private fun DriveModeButton(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(DendyShapes.full)
            .background(if (isSelected) DendyTheme.colors.accentPrimary else DendyTheme.colors.surfaceHighlight)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = name,
            style = DendyTheme.typography.labelBold,
            color = if (isSelected) Color.White else DendyTheme.colors.textPrimary
        )
    }
}
