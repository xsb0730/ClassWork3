package com.xsb.classwork3.model

import com.google.gson.annotations.SerializedName

//API 响应根类（对应最外层 JSON）
data class WeatherResponse(
    val status: String,
    val count: String,
    val info: String,
    val infocode: String,
    val forecasts: List<Forecast>
)

//城市级预报类（对应 forecasts 数组的元素）
data class Forecast(
    val city: String,
    val adcode: String,
    val province: String,
    val reporttime: String,
    val casts: List<Cast>
)

//每日天气详情类（对应 casts 数组的元素）
data class Cast(
    val date: String,
    val week: String,
    val dayweather: String,
    val nightweather: String,
    val daytemp: String,
    val nighttemp: String,
    val daywind: String,
    val nightwind: String,
    val daypower: String,
    val nightpower: String,

    // 使用 @SerializedName 注解映射 API 返回的字段名
    @SerializedName("daytemp_float")
    val daytempFloat: String,

    @SerializedName("nighttemp_float")
    val nighttempFloat: String
)