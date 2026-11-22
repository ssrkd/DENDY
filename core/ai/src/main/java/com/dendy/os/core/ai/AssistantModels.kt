package com.dendy.os.core.ai

enum class AssistantLanguage(val code: String, val displayName: String) {
    RUSSIAN("ru", "Русский"),
    KAZAKH("kk", "Қазақша"),
    ENGLISH("en", "English")
}

enum class AssistantStatus {
    IDLE,
    LISTENING,
    PROCESSING,
    SPEAKING,
    ERROR
}

sealed class VoiceIntent {
    data class ClimateChange(val targetTemp: Float?, val toggleAc: Boolean = false) : VoiceIntent()
    data class MediaAction(val action: String) : VoiceIntent() // "play", "pause", "next", "prev"
    object WeatherQuery : VoiceIntent()
    object VehicleStatusQuery : VoiceIntent()
    data class DriveModeChange(val modeName: String) : VoiceIntent()
    data class NavigateTo(val destination: String) : VoiceIntent()
    data class OpenApplication(val appName: String) : VoiceIntent()
    data class GeneralLlmQuery(val prompt: String) : VoiceIntent()
}

data class AssistantChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val isUser: Boolean,
    val text: String,
    val timestampMillis: Long = System.currentTimeMillis(),
    val detectedIntent: VoiceIntent? = null
)
