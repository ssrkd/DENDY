package com.dendy.os.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dendy.os.core.ai.AssistantLanguage
import com.dendy.os.core.ai.DendyAiEngine
import com.dendy.os.core.designsystem.theme.DendyTheme
import com.dendy.os.core.media.MediaControllerRepository
import com.dendy.os.core.vehicle.repository.VehicleRepository
import com.dendy.os.feature.apps.AppsScreen
import com.dendy.os.feature.assistant.DendyAssistantOverlay
import com.dendy.os.feature.climate.ClimateScreen
import com.dendy.os.feature.home.ui.HomeScreen
import com.dendy.os.feature.music.MusicScreen
import com.dendy.os.feature.navigation.NavigationScreen
import com.dendy.os.feature.settings.SettingsScreen
import com.dendy.os.feature.vehicle.VehicleScreen

@Composable
fun DendyMainContainer(
    vehicleRepository: VehicleRepository,
    mediaRepository: MediaControllerRepository,
    aiEngine: DendyAiEngine,
    isDarkTheme: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit
) {
    var activeSection by remember { mutableStateOf("home") }
    var isAiOverlayVisible by remember { mutableStateOf(false) }

    val vehicleState by vehicleRepository.vehicleState.collectAsState()
    val mediaState by mediaRepository.playbackState.collectAsState()
    val aiStatus by aiEngine.status.collectAsState()
    val aiLanguage by aiEngine.currentLanguage.collectAsState()
    val aiMessages by aiEngine.conversation.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(DendyTheme.colors.background)
        ) {
            // Automotive Ergonomic Dock Bar (LHD Left Position)
            DendyAutomotiveDock(
                currentSection = activeSection,
                onSelectSection = { activeSection = it },
                onTriggerAi = { isAiOverlayVisible = true }
            )

            // Dynamic Main Screen Area with Fluid Crossfade
            Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                Crossfade(
                    targetState = activeSection,
                    label = "section_crossfade"
                ) { section ->
                    when (section) {
                        "home" -> HomeScreen(
                            vehicleState = vehicleState,
                            mediaState = mediaState,
                            onPlayPause = { mediaRepository.togglePlayPause() },
                            onNextTrack = { mediaRepository.nextTrack() },
                            onPrevTrack = { mediaRepository.previousTrack() },
                            onNavigateToSection = { activeSection = it },
                            onOpenAiAssistant = { isAiOverlayVisible = true }
                        )

                        "navigation" -> NavigationScreen(
                            onNavigateBack = { activeSection = "home" }
                        )

                        "music" -> MusicScreen(
                            mediaState = mediaState,
                            onPlayPause = { mediaRepository.togglePlayPause() },
                            onNextTrack = { mediaRepository.nextTrack() },
                            onPrevTrack = { mediaRepository.previousTrack() },
                            onSeek = { mediaRepository.seekTo(it) },
                            onVolumeChange = { mediaRepository.setVolume(it) }
                        )

                        "vehicle" -> VehicleScreen(
                            vehicleState = vehicleState,
                            onSelectDriveMode = { vehicleRepository.setDriveMode(it) }
                        )

                        "climate" -> ClimateScreen(
                            climate = vehicleState.climate,
                            onAdjustDriverTemp = { dt ->
                                vehicleRepository.setClimateTemperature(dt, vehicleState.climate.passengerTempCelsius)
                            },
                            onAdjustPassengerTemp = { pt ->
                                vehicleRepository.setClimateTemperature(vehicleState.climate.driverTempCelsius, pt)
                            },
                            onToggleAc = { vehicleRepository.toggleAc() },
                            onToggleRecirculation = { vehicleRepository.toggleRecirculation() },
                            onAdjustFanSpeed = { vehicleRepository.setFanSpeed(it) },
                            onSetSeatHeating = { isDriver, lvl ->
                                vehicleRepository.setSeatHeating(isDriver, lvl)
                            }
                        )

                        "applications" -> AppsScreen(
                            onLaunchApp = { appItem ->
                                if (appItem.id == "2") activeSection = "music"
                                if (appItem.id == "9") activeSection = "settings"
                            }
                        )

                        "settings" -> SettingsScreen(
                            isDarkTheme = isDarkTheme,
                            onToggleDarkTheme = onToggleDarkTheme,
                            currentLanguage = aiLanguage,
                            onSelectLanguage = { aiEngine.setLanguage(it) }
                        )

                        else -> HomeScreen(
                            vehicleState = vehicleState,
                            mediaState = mediaState,
                            onPlayPause = { mediaRepository.togglePlayPause() },
                            onNextTrack = { mediaRepository.nextTrack() },
                            onPrevTrack = { mediaRepository.previousTrack() },
                            onNavigateToSection = { activeSection = it },
                            onOpenAiAssistant = { isAiOverlayVisible = true }
                        )
                    }
                }
            }
        }

        // Voice Assistant Modal / HUD Overlay
        DendyAssistantOverlay(
            isVisible = isAiOverlayVisible,
            status = aiStatus,
            currentLanguage = aiLanguage,
            messages = aiMessages,
            onDismiss = { isAiOverlayVisible = false },
            onVoiceTrigger = { aiEngine.startListening() },
            onSendQuery = { query -> aiEngine.processVoiceQuery(query) }
        )
    }
}
