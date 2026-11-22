package com.dendy.os.core.media

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.os.Build
import android.view.KeyEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Automotive Media Integration Bridge:
 * Connects directly to external automotive media apps like Yandex Music (ru.yandex.music),
 * Spotify, VK Music, and OEM Radio via standard Android MediaSession APIs and KeyEvents.
 */
class ExternalAutomotiveMediaManager(
    private val context: Context,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    val YANDEX_MUSIC_PACKAGE = "ru.yandex.music"
    val DGIS_PACKAGE = "ru.dublgis.dgismobile"

    private val _activeMediaSource = MutableStateFlow("Яндекс Музыка")
    val activeMediaSource: StateFlow<String> = _activeMediaSource.asStateFlow()

    private var activeController: MediaController? = null

    init {
        findAndAttachActiveSession()
    }

    fun findAndAttachActiveSession() {
        try {
            val sessionManager = context.getSystemService(Context.MEDIA_SESSION_SERVICE) as? MediaSessionManager
            val controllers = sessionManager?.getActiveSessions(null)
            val yandexController = controllers?.firstOrNull { it.packageName == YANDEX_MUSIC_PACKAGE }
                ?: controllers?.firstOrNull()

            if (yandexController != null) {
                activeController = yandexController
                _activeMediaSource.value = if (yandexController.packageName == YANDEX_MUSIC_PACKAGE) {
                    "Яндекс Музыка"
                } else {
                    yandexController.packageName
                }
            }
        } catch (e: SecurityException) {
            // NotificationListenerAccess required on standard Android for full cross-app reading;
            // Falls back to global MediaKey Dispatcher below.
        }
    }

    /**
     * Sends hardware Media Key Events (Play/Pause, Next, Prev) that work globally
     * in background for Yandex Music without needing root or specialized permissions.
     */
    fun sendMediaKeyEvent(keyCode: Int) {
        val downIntent = Intent(Intent.ACTION_MEDIA_BUTTON).apply {
            setPackage(YANDEX_MUSIC_PACKAGE)
            putExtra(Intent.EXTRA_KEY_EVENT, KeyEvent(KeyEvent.ACTION_DOWN, keyCode))
        }
        val upIntent = Intent(Intent.ACTION_MEDIA_BUTTON).apply {
            setPackage(YANDEX_MUSIC_PACKAGE)
            putExtra(Intent.EXTRA_KEY_EVENT, KeyEvent(KeyEvent.ACTION_UP, keyCode))
        }
        context.sendOrderedBroadcast(downIntent, null)
        context.sendOrderedBroadcast(upIntent, null)
    }

    fun launchYandexMusic(): Boolean {
        return launchPackage(YANDEX_MUSIC_PACKAGE)
    }

    fun launch2Gis(): Boolean {
        return launchPackage(DGIS_PACKAGE)
    }

    fun open2GisRoute(latitude: Double, longitude: Double, label: String = "Пункт назначения"): Boolean {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = android.net.Uri.parse("dgis://2gis.ru/routeSearch/rsType/car/to/$longitude,$latitude")
            setPackage(DGIS_PACKAGE)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return try {
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            launch2Gis()
        }
    }

    private fun launchPackage(packageName: String): Boolean {
        val pm = context.packageManager
        val intent = pm.getLaunchIntentForPackage(packageName)
        return if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            true
        } else {
            // If app is not installed yet on head unit, open Play Store or browser install link
            try {
                val storeIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = android.net.Uri.parse("market://details?id=$packageName")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(storeIntent)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}

