package com.dendy.os.core.weather.model

data class WeatherInfo(
    val cityName: String = "Астана",
    val country: String = "Казахстан",
    val latitude: Double = 51.1801,
    val longitude: Double = 71.4460,
    val temperatureCelsius: Float = 6.0f,
    val feelsLikeCelsius: Float = 2.5f,
    val conditionDescription: String = "Пасмурно",
    val humidityPercent: Int = 83,
    val windSpeedKmh: Float = 15.7f,
    val isDay: Boolean = true
)

