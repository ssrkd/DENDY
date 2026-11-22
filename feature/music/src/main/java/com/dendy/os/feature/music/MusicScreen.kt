package com.dendy.os.feature.music

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import com.dendy.os.core.designsystem.theme.DendyShapes
import com.dendy.os.core.designsystem.theme.DendyTheme
import com.dendy.os.core.media.MediaPlaybackState
import com.dendy.os.core.media.MediaTrack
import java.util.Locale

@Composable
fun MusicScreen(
    mediaState: MediaPlaybackState,
    onPlayPause: () -> Unit,
    onNextTrack: () -> Unit,
    onPrevTrack: () -> Unit,
    onSeek: (Int) -> Unit,
    onVolumeChange: (Int) -> Unit,
    onLaunchYandexMusicNativeApp: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val track = mediaState.currentTrack

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(DendyTheme.colors.background)
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Left Column: Large Album Art & Full Controls
        DendyGlassCard(
            modifier = Modifier
                .weight(1.2f)
                .fillMaxHeight(),
            shape = DendyShapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Yandex Music Brand Pill & Direct Launch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(DendyShapes.small)
                                .background(Color(0xFFFFCC00)) // Yandex Yellow
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Яндекс Музыка",
                                color = Color.Black,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Поток «Моя волна»",
                            style = DendyTheme.typography.statusCaption,
                            color = DendyTheme.colors.textSecondary
                        )
                    }

                    if (onLaunchYandexMusicNativeApp != null) {
                        Row(
                            modifier = Modifier
                                .clip(DendyShapes.full)
                                .background(DendyTheme.colors.surfaceHighlight)
                                .clickable(onClick = onLaunchYandexMusicNativeApp)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Открыть приложение",
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

                // Artwork with Animated Gradient
                Box(
                    modifier = Modifier
                        .size(175.dp)
                        .clip(DendyShapes.large)
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFFFFCC00), Color(0xFFFF0033), Color(0xFF6366F1))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(68.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = track.title,
                        style = DendyTheme.typography.screenTitle.copy(fontSize = 22.sp),
                        color = DendyTheme.colors.textPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${track.artist} • ${track.album}",
                        style = DendyTheme.typography.bodyLarge,
                        color = DendyTheme.colors.textSecondary
                    )
                }

                // Progress Bar with Time Labels
                Column(modifier = Modifier.fillMaxWidth()) {
                    val progress = track.currentPositionSeconds.toFloat() / track.durationSeconds.coerceAtLeast(1)
                    Slider(
                        value = progress,
                        onValueChange = { frac ->
                            onSeek((frac * track.durationSeconds).toInt())
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFFFFCC00),
                            activeTrackColor = Color(0xFFFFCC00),
                            inactiveTrackColor = DendyTheme.colors.surfaceSubtle
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = formatTime(track.currentPositionSeconds),
                            style = DendyTheme.typography.statusCaption,
                            color = DendyTheme.colors.textTertiary
                        )
                        Text(
                            text = formatTime(track.durationSeconds),
                            style = DendyTheme.typography.statusCaption,
                            color = DendyTheme.colors.textTertiary
                        )
                    }
                }

                // Media Control Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DendyIconButton(
                        icon = Icons.Default.SkipPrevious,
                        contentDescription = "Prev",
                        onClick = onPrevTrack,
                        size = 56.dp,
                        iconSize = 26.dp
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    DendyIconButton(
                        icon = if (track.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        onClick = onPlayPause,
                        size = 68.dp,
                        iconSize = 34.dp,
                        isActive = true,
                        activeColor = Color(0xFFFFCC00),
                        iconTintActive = Color.Black
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    DendyIconButton(
                        icon = Icons.Default.SkipNext,
                        contentDescription = "Next",
                        onClick = onNextTrack,
                        size = 56.dp,
                        iconSize = 26.dp
                    )
                }
            }
        }

        // Right Column: Automotive Playlist / Queued Tracks
        DendyGlassCard(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = DendyShapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Яндекс Музыка • Плейлист",
                        style = DendyTheme.typography.cardTitle,
                        color = DendyTheme.colors.textPrimary
                    )
                    Text(
                        text = "Lossless HQ",
                        style = DendyTheme.typography.statusCaption,
                        color = DendyTheme.colors.accentPrimary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(mediaState.playlist) { item ->
                        val isCurrent = item.id == track.id
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(DendyShapes.medium)
                                .background(if (isCurrent) DendyTheme.colors.surfaceHighlight else Color.Transparent)
                                .clickable { /* select track */ }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(if (isCurrent) Color(0xFFFFCC00) else DendyTheme.colors.surfaceSubtle),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (isCurrent) "▶" else "♪",
                                    color = if (isCurrent) Color.Black else DendyTheme.colors.textSecondary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.title,
                                    style = DendyTheme.typography.bodyLarge.copy(
                                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isCurrent) DendyTheme.colors.textPrimary else DendyTheme.colors.textPrimary
                                )
                                Text(
                                    text = item.artist,
                                    style = DendyTheme.typography.statusCaption,
                                    color = DendyTheme.colors.textSecondary
                                )
                            }
                            Text(
                                text = formatTime(item.durationSeconds),
                                style = DendyTheme.typography.statusCaption,
                                color = DendyTheme.colors.textTertiary
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return String.format(Locale.US, "%d:%02d", m, s)
}
