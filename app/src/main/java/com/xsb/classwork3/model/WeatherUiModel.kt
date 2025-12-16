package com.xsb.classwork3.model

data class WeatherUiModel(
    val city: String,
    val currentTemp: String,
    val highTemp: String,
    val lowTemp: String,
    val weather: String,
    val dayWeather: String,       // 白天天气
    val dayTemp: String,          // 白天温度
    val dayWind: String,          // 白天风力
    val nightWeather: String,     // 夜间天气
    val nightTemp: String,        // 夜间温度
    val nightWind: String         // 夜间风力
)