package com.dendy.os.core.ai

import com.dendy.os.core.vehicle.model.DriveMode
import com.dendy.os.core.vehicle.repository.VehicleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class DendyAiEngine(
    private val vehicleRepository: VehicleRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val _status = MutableStateFlow(AssistantStatus.IDLE)
    val status: StateFlow<AssistantStatus> = _status.asStateFlow()

    private val _currentLanguage = MutableStateFlow(AssistantLanguage.RUSSIAN)
    val currentLanguage: StateFlow<AssistantLanguage> = _currentLanguage.asStateFlow()

    private val _conversation = MutableStateFlow<List<AssistantChatMessage>>(
        listOf(
            AssistantChatMessage(
                isUser = false,
                text = "Сәлем! Я DENDY AI — бортовой ассистент твоего Omoda C5. Чем могу помочь?"
            )
        )
    )
    val conversation: StateFlow<List<AssistantChatMessage>> = _conversation.asStateFlow()

    fun setLanguage(language: AssistantLanguage) {
        _currentLanguage.value = language
    }

    /**
     * Trigger simulated or microphone voice recognition session
     */
    fun startListening() {
        _status.value = AssistantStatus.LISTENING
    }

    fun stopListening() {
        _status.value = AssistantStatus.IDLE
    }

    /**
     * Processes recognized speech or text command across RU, KZ, EN.
     */
    fun processVoiceQuery(rawQuery: String) {
        scope.launch {
            val userMsg = AssistantChatMessage(isUser = true, text = rawQuery)
            _conversation.value = _conversation.value + userMsg
            _status.value = AssistantStatus.PROCESSING
            delay(400) // Realistic processing cadence

            val intent = parseIntent(rawQuery)
            val responseText = executeIntent(intent, rawQuery)

            _status.value = AssistantStatus.SPEAKING
            val assistantMsg = AssistantChatMessage(
                isUser = false,
                text = responseText,
                detectedIntent = intent
            )
            _conversation.value = _conversation.value + assistantMsg

            delay(1200) // Simulated voice speech duration
            _status.value = AssistantStatus.IDLE
        }
    }

    private fun parseIntent(query: String): VoiceIntent {
        val q = query.lowercase(Locale.ROOT).trim()

        // Climate Control
        if (q.contains("климат") || q.contains("градус") || q.contains("жылыт") || q.contains("салқындат") || q.contains("temp") || q.contains("ac")) {
            val tempMatch = Regex("(\\d{2})").find(q)
            val temp = tempMatch?.value?.toFloatOrNull()
            return VoiceIntent.ClimateChange(targetTemp = temp, toggleAc = q.contains("кондиционер") || q.contains("ac"))
        }

        // Media Control
        if (q.contains("песн") || q.contains("музык") || q.contains("трек") || q.contains("ән") || q.contains("track") || q.contains("music") || q.contains("келесі") || q.contains("следующ")) {
            val action = when {
                q.contains("следующ") || q.contains("келесі") || q.contains("next") -> "next"
                q.contains("предыдущ") || q.contains("алдыңғы") || q.contains("prev") -> "prev"
                q.contains("пауз") || q.contains("тоқтат") || q.contains("stop") -> "pause"
                else -> "play"
            }
            return VoiceIntent.MediaAction(action)
        }

        // Tire pressure & vehicle state
        if (q.contains("шин") || q.contains("давлен") || q.contains("дөңгелек") || q.contains("қысым") || q.contains("tire") || q.contains("pressure") || q.contains("машин") || q.contains("көлік")) {
            return VoiceIntent.VehicleStatusQuery
        }

        // Drive Mode
        if (q.contains("спорт") || q.contains("эко") || q.contains("нормал") || q.contains("sport") || q.contains("eco")) {
            val mode = when {
                q.contains("спорт") || q.contains("sport") -> "SPORT"
                q.contains("эко") || q.contains("eco") -> "ECO"
                else -> "NORMAL"
            }
            return VoiceIntent.DriveModeChange(mode)
        }

        // Weather
        if (q.contains("погод") || q.contains("ауа райы") || q.contains("weather") || q.contains("жаңбыр") || q.contains("дождь")) {
            return VoiceIntent.WeatherQuery
        }

        // Navigation
        if (q.contains("поехали") || q.contains("маршрут") || q.contains("бар") || q.contains("навигац") || q.contains("navigate") || q.contains("drive to")) {
            return VoiceIntent.NavigateTo(destination = query)
        }

        // Open application
        if (q.contains("открой") || q.contains("аш") || q.contains("open")) {
            return VoiceIntent.OpenApplication(appName = query)
        }

        return VoiceIntent.GeneralLlmQuery(prompt = query)
    }

    private fun executeIntent(intent: VoiceIntent, raw: String): String {
        val lang = _currentLanguage.value

        return when (intent) {
            is VoiceIntent.ClimateChange -> {
                if (intent.targetTemp != null) {
                    vehicleRepository.setClimateTemperature(intent.targetTemp, intent.targetTemp)
                    when (lang) {
                        AssistantLanguage.RUSSIAN -> "Установила температуру климата на ${intent.targetTemp.toInt()}°C для водителя и пассажира."
                        AssistantLanguage.KAZAKH -> "Климат температурасын ${intent.targetTemp.toInt()}°C етіп орнаттым."
                        AssistantLanguage.ENGLISH -> "Setting climate temperature to ${intent.targetTemp.toInt()}°C."
                    }
                } else {
                    vehicleRepository.toggleAc()
                    when (lang) {
                        AssistantLanguage.RUSSIAN -> "Переключила кондиционер (A/C)."
                        AssistantLanguage.KAZAKH -> "Кондиционер қосу/өшіру орындалды."
                        AssistantLanguage.ENGLISH -> "Toggled A/C."
                    }
                }
            }

            is VoiceIntent.DriveModeChange -> {
                val mode = when (intent.modeName) {
                    "SPORT" -> DriveMode.SPORT
                    "ECO" -> DriveMode.ECO
                    else -> DriveMode.NORMAL
                }
                vehicleRepository.setDriveMode(mode)
                when (lang) {
                    AssistantLanguage.RUSSIAN -> "Режим движения переключен на ${intent.modeName}. Полный привод AWD в боевой готовности."
                    AssistantLanguage.KAZAKH -> "Жүргізу режимі ${intent.modeName} күйіне ауыстырылды. Толық жетек (AWD) дайын."
                    AssistantLanguage.ENGLISH -> "Drive mode switched to ${intent.modeName}. AWD system primed."
                }
            }

            is VoiceIntent.VehicleStatusQuery -> {
                val v = vehicleRepository.vehicleState.value
                when (lang) {
                    AssistantLanguage.RUSSIAN -> "Состояние Omoda C5 в норме. Давление в шинах: передние 2.3 бар, задние 2.2 бар. Бак заполнен на ${v.fuelLevelPercent}%, запас хода ${v.rangeRemainingKm} км."
                    AssistantLanguage.KAZAKH -> "Omoda C5 жағдайы керемет. Дөңгелек қысымы: алдыңғы 2.3 бар, артқы 2.2 бар. Жанармай ${v.fuelLevelPercent}%, қалған жол ${v.rangeRemainingKm} км."
                    AssistantLanguage.ENGLISH -> "Omoda C5 status is optimal. Tire pressures: front 2.3 bar, rear 2.2 bar. Fuel at ${v.fuelLevelPercent}%, range ${v.rangeRemainingKm} km."
                }
            }

            is VoiceIntent.MediaAction -> {
                when (lang) {
                    AssistantLanguage.RUSSIAN -> "Мультимедиа: команда '${intent.action}' выполнена."
                    AssistantLanguage.KAZAKH -> "Мультимедиа: '${intent.action}' бұйрығы орындалды."
                    AssistantLanguage.ENGLISH -> "Media control: '${intent.action}' executed."
                }
            }

            is VoiceIntent.WeatherQuery -> {
                when (lang) {
                    AssistantLanguage.RUSSIAN -> "Сейчас +18°C, ясная погода, ветра почти нет. Отличные условия для поездки!"
                    AssistantLanguage.KAZAKH -> "Қазір +18°C, күн ашық, жел баяу. Жолға шығуға қолайлы ауа райы!"
                    AssistantLanguage.ENGLISH -> "Currently +18°C and clear skies with light breeze. Ideal driving conditions!"
                }
            }

            is VoiceIntent.NavigateTo -> {
                when (lang) {
                    AssistantLanguage.RUSSIAN -> "Прокладываю оптимальный маршрут с учетом дорожной обстановки."
                    AssistantLanguage.KAZAKH -> "Жол кептелісін ескере отырып, ең оңтайлы бағытты құрып жатырмын."
                    AssistantLanguage.ENGLISH -> "Calculating the fastest route avoiding heavy traffic."
                }
            }

            is VoiceIntent.OpenApplication -> {
                when (lang) {
                    AssistantLanguage.RUSSIAN -> "Открываю запрашиваемое приложение."
                    AssistantLanguage.KAZAKH -> "Сұралған қосымша ашылуда."
                    AssistantLanguage.ENGLISH -> "Opening requested application."
                }
            }

            is VoiceIntent.GeneralLlmQuery -> {
                when (lang) {
                    AssistantLanguage.RUSSIAN -> "Принято: '$raw'. Модуль LLM готов к подключению внешнего API модели."
                    AssistantLanguage.KAZAKH -> "Түсіндім: '$raw'. Сыртқы LLM моделіне қосылу модулі дайын."
                    AssistantLanguage.ENGLISH -> "Understood: '$raw'. LLM agent module ready for API uplink."
                }
            }
        }
    }
}
