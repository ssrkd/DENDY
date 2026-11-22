package com.dendy.os.core.installer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

data class DownloadProgress(
    val appName: String,
    val progressPercent: Int,
    val isDownloading: Boolean = false,
    val isCompleted: Boolean = false,
    val errorMessage: String? = null
)

/**
 * Real In-Car APK Installer for Omoda C5 Head Units:
 * Head units in cars often lack Google Play Services.
 * This installer downloads APKs (2GIS, Yandex Music, etc.) directly to local storage
 * and triggers the system package installer safely.
 */
class DendyPackageInstaller(
    private val context: Context
) {
    private val _downloadState = MutableStateFlow<DownloadProgress?>(null)
    val downloadState: StateFlow<DownloadProgress?> = _downloadState.asStateFlow()

    // Official Direct APK Endpoints
    val DGIS_APK_DIRECT_URL = "https://apk.2gis.ru/2gis.apk" // 2GIS official distribution
    val YANDEX_MUSIC_APK_URL = "https://rustore.ru/download/ru.yandex.music.apk"

    suspend fun downloadAndInstallApp(
        appName: String,
        downloadUrl: String,
        targetFileName: String
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            _downloadState.value = DownloadProgress(appName, 0, isDownloading = true)

            val destDir = File(context.getExternalFilesDir(null), "downloads")
            if (!destDir.exists()) destDir.mkdirs()
            val destFile = File(destDir, targetFileName)

            val url = URL(downloadUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.connect()

            val fileLength = connection.contentLength
            val input = connection.inputStream
            val output = FileOutputStream(destFile)

            val data = ByteArray(4096)
            var total: Long = 0
            var count: Int

            while (input.read(data).also { count = it } != -1) {
                total += count
                output.write(data, 0, count)
                if (fileLength > 0) {
                    val progress = ((total * 100) / fileLength).toInt()
                    _downloadState.value = DownloadProgress(appName, progress, isDownloading = true)
                }
            }

            output.flush()
            output.close()
            input.close()

            _downloadState.value = DownloadProgress(appName, 100, isDownloading = false, isCompleted = true)

            // Trigger Android Package Installer Intent
            installApkFile(destFile)
            true
        } catch (e: Exception) {
            _downloadState.value = DownloadProgress(
                appName,
                0,
                isDownloading = false,
                errorMessage = e.localizedMessage ?: "Ошибка скачивания"
            )
            false
        }
    }

    fun installApkFile(apkFile: File) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            val apkUri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", apkFile)
            } else {
                Uri.fromFile(apkFile)
            }
            setDataAndType(apkUri, "application/vnd.android.package-archive")
        }
        context.startActivity(intent)
    }
}

