package com.xsb.classwork3.model

data class ForecastUiModel(
    val date: String,           // 日期
    val week: String,           // 星期
    val weather: String,        // 天气
    val highTemp: String,       // 最高温度
    val lowTemp: String,        // 最低温度
    val weatherIcon: String     // 天气图标
)