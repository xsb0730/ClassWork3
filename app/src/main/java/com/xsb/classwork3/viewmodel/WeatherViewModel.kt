package com.xsb.classwork3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xsb.classwork3.model.ForecastUiModel
import com.xsb.classwork3.model.WeatherUiModel
import com.xsb.classwork3.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _weatherData = MutableLiveData<WeatherUiModel>()
    val weatherData: LiveData<WeatherUiModel> = _weatherData

    private val _forecastList = MutableLiveData<List<ForecastUiModel>>()
    val forecastList: LiveData<List<ForecastUiModel>> = _forecastList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // 城市代码映射（使用城市编码而不是区域编码）
    private val cityCodeMap = mapOf(
        "广州" to "440100",
        "北京" to "110000",
        "上海" to "310000",
        "深圳" to "440300"
    )

    companion object {
        private const val TAG = "WeatherViewModel"
    }

    fun loadWeather(cityName: String) {
        val cityCode = cityCodeMap[cityName] ?: "110000"

        Log.d(TAG, "开始加载天气: 城市=$cityName, 代码=$cityCode")

        viewModelScope.launch {
            _isLoading.value = true

            try {
                val result = repository.getWeatherForecast(cityCode)

                result.onSuccess { response ->
                    Log.d(TAG, "API 返回: status=${response.status}, info=${response.info}")

                    if (response.status == "1") {
                        if (response.forecasts.isNotEmpty()) {
                            val forecast = response.forecasts[0]
                            val casts = forecast.casts

                            Log.d(TAG, "获取到 ${casts.size} 天预报数据")

                            if (casts.isNotEmpty()) {
                                val today = casts[0]

                                _weatherData.value = WeatherUiModel(
                                    city = cityName,
                                    currentTemp = today.daytemp,
                                    highTemp = today.daytemp,
                                    lowTemp = today.nighttemp,
                                    weather = today.dayweather,
                                    dayWeather = today.dayweather,
                                    dayTemp = today.daytemp,
                                    nightWeather = today.nightweather,
                                    nightTemp = today.nighttemp,
                                    dayWind = "${today.daywind}风 ${today.daypower}级",
                                    nightWind = "${today.nightwind}风 ${today.nightpower}级"
                                )

                                // 更新未来天气列表
                                val forecastUiList = casts.mapIndexed { index, cast ->
                                    ForecastUiModel(
                                        date = formatDate(cast.date),
                                        week = if (index == 0) "今天" else formatWeek(cast.week),
                                        weather = cast.dayweather,
                                        highTemp = "${cast.daytemp}°",
                                        lowTemp = "${cast.nighttemp}°",
                                        weatherIcon = getWeatherIcon(cast.dayweather)
                                    )
                                }
                                _forecastList.value = forecastUiList

                                Log.d(TAG, "天气数据加载成功")
                            } else {
                                _error.value = "没有天气数据"
                                Log.e(TAG, "casts 列表为空")
                            }
                        } else {
                            _error.value = "城市代码错误或无数据"
                            Log.e(TAG, "forecasts 列表为空")
                        }
                    } else {
                        val errorMsg = "API错误: ${response.info} (${response.infocode})"
                        _error.value = errorMsg
                        Log.e(TAG, errorMsg)
                    }
                }

                result.onFailure { exception ->
                    val errorMsg = "网络请求失败: ${exception.message}"
                    _error.value = errorMsg
                    Log.e(TAG, errorMsg, exception)
                }

            } catch (e: Exception) {
                val errorMsg = "发生异常: ${e.message}"
                _error.value = errorMsg
                Log.e(TAG, errorMsg, e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 格式化日期 (2025-12-06 -> 12-06)
    private fun formatDate(date: String): String {
        return try {
            val parts = date.split("-")
            if (parts.size == 3) {
                "${parts[1]}-${parts[2]}"
            } else {
                date
            }
        } catch (e: Exception) {
            date
        }
    }

    // 格式化星期
    private fun formatWeek(week: String): String {
        return when (week) {
            "1" -> "星期一"
            "2" -> "星期二"
            "3" -> "星期三"
            "4" -> "星期四"
            "5" -> "星期五"
            "6" -> "星期六"
            "7" -> "星期日"
            else -> "星期$week"
        }
    }

    private fun getWeatherIcon(weather: String): String {
        return when {
            weather.contains("晴") -> "☀️"
            weather.contains("云") -> "☁️"
            weather.contains("阴") -> "⛅"
            weather.contains("雨") -> "🌧️"
            weather.contains("雪") -> "❄️"
            weather.contains("雾") -> "🌫️"
            weather.contains("霾") -> "😷"
            else -> "🌤️"
        }
    }
}