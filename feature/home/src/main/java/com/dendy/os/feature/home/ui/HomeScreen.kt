package com.dendy.os.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dendy.os.core.designsystem.R
import com.dendy.os.core.designsystem.components.DendyGlassCard
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
 * DENDY OS — Apple Glass Automotive Home Screen for Omoda C5 (1920×720 px, 8:3):
 * - Top Bar: Avatar ("WELCOME", "srk"), DENDY AI Search Pill, Real-time Weather, iPhone Battery Sync, Clock.
 * - Center Stage (3 Cards):
 *   1. Applications Launcher ("Приложения Omoda C5", 2GIS, Yandex Maps, Yandex Music).
 *   2. Apple Weather Live Interactive Widget (City, Current Temp, Conditions, 5-hour forecast row, Wind, Humidity).
 *   3. Omoda C5 Audio Hub (Sony Sound / Bluetooth A2DP / Yandex Music).
 * - Zero Emojis: Strictly minimalist vector icons.
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
    onLaunch2Gis: () -> Unit = { onNavigateToSection("navigation") },
    onLaunchYandexMaps: () -> Unit = { onNavigateToSection("navigation") },
    onLaunchYandexMusic: () -> Unit = { onNavigateToSection("music") },
    modifier: Modifier = Modifier
) {
    var currentTime by remember { mutableStateOf("18:30") }

    LaunchedEffect(Unit) {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        while (true) {
            currentTime = timeFormat.format(Date())
            delay(1000)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F7))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // ═══════════════════════ TOP BAR ═══════════════════════
        DendyGlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color(0xEBFFFFFF),
            borderColor = Color(0x2EFFFFFF)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: Avatar + WELCOME srk
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.clickable { onNavigateToSection("settings") }
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, Color(0x66007AFF), CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.srk),
                            contentDescription = "srk",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(
                            text = "WELCOME",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF007AFF),
                            letterSpacing = 0.8.sp
                        )
                        Text(
                            text = "srk",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1D1D1F)
                        )
                    }
                }

                // Center: DENDY AI Search Capsule
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 18.dp)
                        .height(34.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color(0xA6FFFFFF))
                        .border(1.dp, Color(0xCCFFFFFF), RoundedCornerShape(999.dp))
                        .clickable(onClick = onOpenAiAssistant)
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(RoundedCornerShape(6.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dendy),
                                contentDescription = "DENDY AI",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(
                            text = "srk, куда поедем? Маршрут в Яндекс Картах...",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF86868B),
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color(0xFF86868B),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                // Right: Weather + Battery + Clock
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Weather Pill (No LIVE badge, clean and real)
                    Row(
                        modifier = Modifier
                            .height(32.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color(0xCCFFFFFF))
                            .border(1.dp, Color(0x99FFFFFF), RoundedCornerShape(999.dp))
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF34C759))
                        )
                        Icon(
                            imageVector = Icons.Default.WbCloudy,
                            contentDescription = "Weather",
                            tint = Color(0xFF007AFF),
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "${weatherInfo.cityName} ${if (weatherInfo.temperatureCelsius >= 0) "+" else ""}${weatherInfo.temperatureCelsius.toInt()}°C",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1D1D1F)
                        )
                        Text(
                            text = weatherInfo.conditionDescription,
                            fontSize = 10.sp,
                            color = Color(0xFF86868B)
                        )
                    }

                    // iPhone Sync Pill
                    Row(
                        modifier = Modifier
                            .height(32.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color(0xCCFFFFFF))
                            .border(1.dp, Color(0x99FFFFFF), RoundedCornerShape(999.dp))
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Smartphone,
                            contentDescription = "iPhone",
                            tint = Color(0xFF007AFF),
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "iPhone srk",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1D1D1F)
                        )
                        Text(text = "|", fontSize = 10.sp, color = Color(0xFF86868B))
                        Text(
                            text = "94%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1D1D1F),
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    // Clock
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color(0xCCFFFFFF))
                            .border(1.dp, Color(0x99FFFFFF), RoundedCornerShape(999.dp))
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentTime,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1D1D1F),
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        // ═══════════════════════ MAIN 3 CARDS GRID ═══════════════════════
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // CARD 1: DENDY APK APPLICATIONS LAUNCHER
            DendyGlassCard(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = Color(0xEBFFFFFF),
                borderColor = Color(0x33FFFFFF)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Card 1 Header: papka.webp centered vertically, slightly to the left
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .shadow(2.dp, RoundedCornerShape(8.dp))
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.papka),
                                    contentDescription = "Приложения",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Column {
                                Text(
                                    text = "Приложения Omoda C5",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF1D1D1F)
                                )
                                Text(
                                    text = "Официальные APK-пакеты",
                                    fontSize = 10.sp,
                                    color = Color(0xFF86868B)
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(Color(0x1A007AFF))
                                .clickable { onNavigateToSection("applications") }
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "APK Store",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF007AFF)
                            )
                        }
                    }

                    // Fast Launch 4 Grid Icons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // 2GIS
                        FastAppItem(
                            title = "2ГИС",
                            iconRes = R.drawable.gis2,
                            onClick = onLaunch2Gis,
                            modifier = Modifier.weight(1f)
                        )

                        // Yandex Maps (with yandexcard.webp)
                        FastAppItem(
                            title = "Карты",
                            iconRes = R.drawable.yandexcard,
                            onClick = onLaunchYandexMaps,
                            modifier = Modifier.weight(1f)
                        )

                        // Yandex Music
                        FastAppItem(
                            title = "Музыка",
                            iconRes = R.drawable.ymusic,
                            onClick = onLaunchYandexMusic,
                            modifier = Modifier.weight(1f)
                        )

                        // More APKs
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xB3FFFFFF))
                                .clickable { onNavigateToSection("applications") }
                                .padding(vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFE5E5EA)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "APK",
                                    tint = Color(0xFF86868B),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "APK",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF86868B)
                            )
                        }
                    }

                    // Footer Chip
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x10007AFF))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Пакет Omoda C5:",
                            fontSize = 10.sp,
                            color = Color(0xFF86868B)
                        )
                        Text(
                            text = "Android 10 (ARM64)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF007AFF)
                        )
                    }
                }
            }

            // CARD 2: APPLE WEATHER LIVE INTERACTIVE WIDGET
            DendyGlassCard(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = Color(0xEBFFFFFF),
                borderColor = Color(0x33FFFFFF)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Header: City, Condition, High/Low and Current Large Temp
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = weatherInfo.cityName,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF1D1D1F)
                                )
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Astana",
                                    tint = Color(0xFF007AFF),
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                            Text(
                                text = weatherInfo.conditionDescription,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF86868B)
                            )
                            Text(
                                text = "В:+8°  Н:+1°",
                                fontSize = 9.sp,
                                color = Color(0xFF86868B),
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "${if (weatherInfo.temperatureCelsius >= 0) "+" else ""}${weatherInfo.temperatureCelsius.toInt()}°",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1D1D1F),
                                fontFamily = FontFamily.SansSerif
                            )
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0x14007AFF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.WbCloudy,
                                    contentDescription = "Cloud",
                                    tint = Color(0xFF007AFF),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    // Hourly Forecast Strip (5 hours)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xB3FFFFFF))
                            .border(1.dp, Color(0xE6FFFFFF), RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HourlyColumn(label = "Сейчас", temp = "+6°", icon = Icons.Default.WbCloudy, iconTint = Color(0xFF86868B))
                        HourlyColumn(label = "+1ч", temp = "+6°", icon = Icons.Default.WbCloudy, iconTint = Color(0xFF86868B))
                        HourlyColumn(label = "+2ч", temp = "+5°", icon = Icons.Default.WbSunny, iconTint = Color(0xFFFF9500))
                        HourlyColumn(label = "+3ч", temp = "+4°", icon = Icons.Default.Nightlight, iconTint = Color(0xFF5856D6))
                        HourlyColumn(label = "+4ч", temp = "+3°", icon = Icons.Default.Nightlight, iconTint = Color(0xFF5856D6))
                    }

                    // Weather Footer: Wind & Humidity
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Air,
                                contentDescription = "Wind",
                                tint = Color(0xFF007AFF),
                                modifier = Modifier.size(12.dp)
                            )
                            Text(text = "Ветер: ", fontSize = 10.sp, color = Color(0xFF86868B))
                            Text(text = "${weatherInfo.windSpeedKmh.toInt()} км/ч", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1D1D1F))
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.WaterDrop,
                                contentDescription = "Humidity",
                                tint = Color(0xFF007AFF),
                                modifier = Modifier.size(12.dp)
                            )
                            Text(text = "Влажность: ", fontSize = 10.sp, color = Color(0xFF86868B))
                            Text(text = "${weatherInfo.humidityPercent}%", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1D1D1F))
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0x14007AFF))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Apple Weather",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF007AFF)
                            )
                        }
                    }
                }
            }

            // CARD 3: OMODA C5 AUDIO & MEDIA HUB
            DendyGlassCard(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = Color(0xEBFFFFFF),
                borderColor = Color(0x33FFFFFF)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(9.dp))
                                    .background(Color(0x1A007AFF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = "Music",
                                    tint = Color(0xFF007AFF),
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Аудиосистема Omoda",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF1D1D1F)
                                )
                                Text(
                                    text = "Bluetooth A2DP • iPhone srk",
                                    fontSize = 10.sp,
                                    color = Color(0xFF86868B)
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(Color(0x1F34C759))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "SONY SOUND",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF34C759)
                            )
                        }
                    }

                    // Track & Artist
                    Column {
                        Text(
                            text = mediaState.currentTrack.title.ifEmpty { "Скриптонит — Положение" },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1D1D1F),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = mediaState.currentTrack.artist.ifEmpty { "Праздник на улице 36" },
                            fontSize = 10.sp,
                            color = Color(0xFF86868B),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Progress Bar
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        LinearProgressIndicator(
                            progress = (mediaState.currentTrack.currentPositionSeconds.toFloat() / mediaState.currentTrack.durationSeconds.toFloat().coerceAtLeast(1f)).coerceIn(0f, 1f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(3.dp)
                                .clip(RoundedCornerShape(999.dp)),
                            color = Color(0xFF1D1D1F),
                            trackColor = Color(0x14000000)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "01:22",
                                fontSize = 9.sp,
                                color = Color(0xFF86868B),
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "03:24",
                                fontSize = 9.sp,
                                color = Color(0xFF86868B),
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    // Audio Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Prev
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(1.dp, Color(0x10000000), CircleShape)
                                    .clickable(onClick = onPrevTrack),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SkipPrevious,
                                    contentDescription = "Prev",
                                    tint = Color(0xFF1D1D1F),
                                    modifier = Modifier.size(14.dp)
                                )
                            }

                            // Play / Pause
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF1D1D1F))
                                    .clickable(onClick = onPlayPause),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (mediaState.currentTrack.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "PlayPause",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            // Next
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(1.dp, Color(0x10000000), CircleShape)
                                    .clickable(onClick = onNextTrack),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SkipNext,
                                    contentDescription = "Next",
                                    tint = Color(0xFF1D1D1F),
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FastAppItem(
    title: String,
    iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xB3FFFFFF))
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(10.dp))
                .shadow(1.dp, RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = title,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1D1D1F)
        )
    }
}

@Composable
private fun HourlyColumn(
    label: String,
    temp: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF86868B)
        )
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = iconTint,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = temp,
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF1D1D1F)
        )
    }
}
