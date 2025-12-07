package com.xsb.classwork3.repository

import android.util.Log
import com.xsb.classwork3.api.WeatherApiService
import com.xsb.classwork3.model.WeatherResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WeatherRepository {

    companion object {
        private const val TAG = "WeatherRepository"
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

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
            Log.d(TAG, "请求天气: city=$city, key=$apiKey")
            val response = apiService.getWeatherForecast(city, apiKey)
            Log.d(TAG, "API 响应成功: ${response.status}")
            Result.success(response)
        } catch (e: Exception) {
            Log.e(TAG, "API 请求失败", e)
            Result.failure(e)
        }
    }
}