package com.dendy.os.feature.home.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Wifi
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
import androidx.compose.ui.draw.scale
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
import com.dendy.os.core.media.MediaPlaybackState
import com.dendy.os.core.vehicle.model.VehicleState
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ClockWeatherWidget(
    modifier: Modifier = Modifier,
    outsideTemp: Int = 18
) {
    var currentTime by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf("") }

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

    DendyGlassCard(
        modifier = modifier.fillMaxWidth(),
        shape = DendyShapes.large,
        elevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = currentTime.ifEmpty { "14:45" },
                    style = DendyTheme.typography.clockHero,
                    color = DendyTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = currentDate.ifEmpty { "Воскресенье, 22 ноября" },
                    style = DendyTheme.typography.bodyMedium,
                    color = DendyTheme.colors.textSecondary
                )
            }

            // Weather Pill
            Row(
                modifier = Modifier
                    .clip(DendyShapes.full)
                    .background(DendyTheme.colors.surfaceHighlight)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = null,
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "+$outsideTemp°C",
                        style = DendyTheme.typography.cardTitle,
                        color = DendyTheme.colors.textPrimary
                    )
                    Text(
                        text = "Алматы, Ясно",
                        style = DendyTheme.typography.statusCaption,
                        color = DendyTheme.colors.textSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationPreviewWidget(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    DendyGlassCard(
        modifier = modifier,
        shape = DendyShapes.large,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFE0F2FE),
                            Color(0xFFBAE6FD),
                            Color(0xFFF0FDF4)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            // Simulated Map Route Card Overlay
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DendyStatusBadge(
                        text = "DENDY MAPS",
                        icon = Icons.Default.Navigation,
                        badgeColor = Color(0xCCFFFFFF),
                        textColor = DendyTheme.colors.accentPrimary
                    )
                    Text(
                        text = "18 мин • 12.4 км",
                        style = DendyTheme.typography.labelBold,
                        color = DendyTheme.colors.textPrimary
                    )
                }

                // Next Maneuver Card
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(DendyShapes.medium)
                        .background(Color(0xE6FFFFFF))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(DendyTheme.colors.accentPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.NearMe,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Через 450 м поворот направо",
                            style = DendyTheme.typography.cardTitle.copy(fontSize = 16.sp),
                            color = DendyTheme.colors.textPrimary
                        )
                        Text(
                            text = "Проспект Аль-Фараби",
                            style = DendyTheme.typography.bodyMedium,
                            color = DendyTheme.colors.textSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MediaWidget(
    mediaState: MediaPlaybackState,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val track = mediaState.currentTrack
    val progress = track.currentPositionSeconds.toFloat() / track.durationSeconds.coerceAtLeast(1)

    DendyGlassCard(
        modifier = modifier,
        shape = DendyShapes.large,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Album Art Placeholder (Stylized Apple/Tesla Gradient)
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(DendyShapes.medium)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF6366F1), Color(0xFFEC4899))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "♪",
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = track.title,
                        style = DendyTheme.typography.cardTitle,
                        color = DendyTheme.colors.textPrimary,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${track.artist} — ${track.album}",
                        style = DendyTheme.typography.bodyMedium,
                        color = DendyTheme.colors.textSecondary,
                        maxLines = 1
                    )
                }
            }

            // Progress Bar
            Column(modifier = Modifier.fillMaxWidth()) {
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(DendyShapes.full),
                    color = DendyTheme.colors.accentPrimary,
                    trackColor = DendyTheme.colors.surfaceSubtle
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatDuration(track.currentPositionSeconds),
                        style = DendyTheme.typography.statusCaption,
                        color = DendyTheme.colors.textTertiary
                    )
                    Text(
                        text = formatDuration(track.durationSeconds),
                        style = DendyTheme.typography.statusCaption,
                        color = DendyTheme.colors.textTertiary
                    )
                }
            }

            // Playback Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DendyIconButton(
                    icon = Icons.Default.SkipPrevious,
                    contentDescription = "Предыдущий",
                    onClick = onPrev,
                    size = 48.dp,
                    iconSize = 22.dp
                )
                Spacer(modifier = Modifier.width(20.dp))
                DendyIconButton(
                    icon = if (track.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Воспроизведение",
                    onClick = onPlayPause,
                    size = 56.dp,
                    iconSize = 30.dp,
                    isActive = true
                )
                Spacer(modifier = Modifier.width(20.dp))
                DendyIconButton(
                    icon = Icons.Default.SkipNext,
                    contentDescription = "Следующий",
                    onClick = onNext,
                    size = 48.dp,
                    iconSize = 22.dp
                )
            }
        }
    }
}

@Composable
fun VehicleQuickCard(
    vehicleState: VehicleState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    DendyGlassCard(
        modifier = modifier,
        shape = DendyShapes.large,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = null,
                        tint = DendyTheme.colors.accentPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Omoda C5 AWD",
                        style = DendyTheme.typography.cardTitle,
                        color = DendyTheme.colors.textPrimary
                    )
                }

                DendyStatusBadge(
                    text = "${vehicleState.driveMode.name} MODE",
                    badgeColor = DendyTheme.colors.surfaceHighlight,
                    textColor = DendyTheme.colors.accentPrimary
                )
            }

            // Fuel & Range Display
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${vehicleState.fuelLevelPercent}%",
                        style = DendyTheme.typography.screenTitle.copy(fontSize = 24.sp),
                        color = DendyTheme.colors.textPrimary
                    )
                    Text(
                        text = "Топливо",
                        style = DendyTheme.typography.statusCaption,
                        color = DendyTheme.colors.textSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(36.dp)
                        .background(DendyTheme.colors.borderLight)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${vehicleState.rangeRemainingKm} км",
                        style = DendyTheme.typography.screenTitle.copy(fontSize = 24.sp),
                        color = DendyTheme.colors.textPrimary
                    )
                    Text(
                        text = "Запас хода",
                        style = DendyTheme.typography.statusCaption,
                        color = DendyTheme.colors.textSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(36.dp)
                        .background(DendyTheme.colors.borderLight)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "2.3 bar",
                        style = DendyTheme.typography.screenTitle.copy(fontSize = 24.sp),
                        color = DendyTheme.colors.success
                    )
                    Text(
                        text = "Шины TPMS",
                        style = DendyTheme.typography.statusCaption,
                        color = DendyTheme.colors.textSecondary
                    )
                }
            }

            // Connectivity Pill
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(DendyShapes.small)
                    .background(DendyTheme.colors.surfaceHighlight)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Bluetooth,
                        contentDescription = null,
                        tint = DendyTheme.colors.accentPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "iPhone 15 Pro • 92%",
                        style = DendyTheme.typography.statusCaption,
                        color = DendyTheme.colors.textPrimary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Wifi,
                        contentDescription = null,
                        tint = DendyTheme.colors.success,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LTE Онлайн",
                        style = DendyTheme.typography.statusCaption,
                        color = DendyTheme.colors.textSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun DendyAiTriggerPill(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ai_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = modifier
            .clip(DendyShapes.full)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF0284C7),
                        Color(0xFF6366F1),
                        Color(0xFF8B5CF6)
                    )
                )
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "DENDY AI",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .scale(pulseScale)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "DENDY AI",
                style = DendyTheme.typography.cardTitle.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
    }
}

private fun formatDuration(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return String.format(Locale.US, "%d:%02d", m, s)
}
