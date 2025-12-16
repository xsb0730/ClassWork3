package com.xsb.classwork3.model

//封装界面展示所需的天气预报数据
data class ForecastUiModel(
    val date: String,           // 日期
    val week: String,           // 星期
    val weather: String,        // 天气
    val highTemp: String,       // 最高温度
    val lowTemp: String,        // 最低温度
    val weatherIcon: String     // 天气图标
)