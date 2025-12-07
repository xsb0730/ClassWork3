package com.xsb.classwork3.api

import com.xsb.classwork3.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("v3/weather/weatherInfo")
    suspend fun getWeatherForecast(
        @Query("city") city: String,
        @Query("key") key: String,
        @Query("extensions") extensions: String = "all"  // 必须是 "all" 才能获取预报
    ): WeatherResponse
}