package com.xsb.classwork3.repository

import com.xsb.classwork3.api.WeatherApiService
import com.xsb.classwork3.model.WeatherResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WeatherRepository {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY       // 打印完整日志
    }

    //构建 OkHttpClient 实例
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()                    //将 Builder 中所有配置项固化，生成不可变的 OkHttpClient 实例

    //创建一个 Retrofit 实例，接口基础 URL、底层网络客户端（OkHttp）、JSON 数据转换器
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restapi.amap.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(WeatherApiService::class.java)

    //
    private val apiKey = "9c14cc89856c7bbcdcb7cfd686197660"

    suspend fun getWeatherForecast(city: String): Result<WeatherResponse> {
        return try {
            val response = apiService.getWeatherForecast(city, apiKey)

            Result.success(response)
        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}