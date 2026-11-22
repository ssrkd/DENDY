package com.dendy.os

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.dendy.os.core.ai.DendyAiEngine
import com.dendy.os.core.ai.VoiceIntent
import com.dendy.os.core.designsystem.theme.DendyTheme
import com.dendy.os.core.media.DefaultMediaRepository
import com.dendy.os.core.media.ExternalAutomotiveMediaManager
import com.dendy.os.core.vehicle.repository.DefaultVehicleRepository
import com.dendy.os.ui.DendyMainContainer

class MainActivity : ComponentActivity() {

    private lateinit var vehicleRepository: DefaultVehicleRepository
    private lateinit var mediaRepository: DefaultMediaRepository
    private lateinit var externalMediaManager: ExternalAutomotiveMediaManager
    private lateinit var aiEngine: DendyAiEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Automotive Immersion: Keep screen active while vehicle is on and hide standard system bars
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.hide(WindowInsetsCompat.Type.systemBars())
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Initialize Core Automotive Modules
        vehicleRepository = DefaultVehicleRepository(applicationContext)
        mediaRepository = DefaultMediaRepository()
        externalMediaManager = ExternalAutomotiveMediaManager(applicationContext)

        // AI Engine with custom action callback for 2GIS & Yandex Music
        aiEngine = DendyAiEngine(
            vehicleRepository = vehicleRepository,
            onExecuteCustomAction = { intent ->
                when (intent) {
                    is VoiceIntent.Launch2GisNavigation -> externalMediaManager.launch2Gis()
                    is VoiceIntent.LaunchYandexMusic -> externalMediaManager.launchYandexMusic()
                    else -> Unit
                }
            }
        )

        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            DendyTheme(darkTheme = isDarkTheme) {
                DendyMainContainer(
                    vehicleRepository = vehicleRepository,
                    mediaRepository = mediaRepository,
                    externalMediaManager = externalMediaManager,
                    aiEngine = aiEngine,
                    isDarkTheme = isDarkTheme,
                    onToggleDarkTheme = { isDarkTheme = it }
                )
            }
        }
    }

    override fun onBackPressed() {
        // Prevent accidental closing of automotive launcher
    }
}
