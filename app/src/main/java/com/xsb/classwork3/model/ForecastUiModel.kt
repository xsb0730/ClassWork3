package com.xsb.classwork3.model

data class ForecastUiModel(
    val date: String,           // 日期 (12-06)
    val week: String,           // 星期 (星期六)
    val weather: String,        // 天气 (晴)
    val highTemp: String,       // 最高温度 (25°)
    val lowTemp: String,        // 最低温度 (18°)
    val weatherIcon: String     // 天气图标
)