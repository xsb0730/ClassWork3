package com.xsb.classwork3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xsb.ClassWork3.databinding.FragmentWeatherBinding
import com.xsb.classwork3.viewmodel.WeatherViewModel

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 获取 Activity 级别的 ViewModel
        viewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]

        observeViewModel()
    }

    private fun observeViewModel() {
        // 观察当前天气数据
        viewModel.weatherData.observe(viewLifecycleOwner) { weather ->
            binding.tvCity.text = weather.city
            binding.tvCurrentTemp.text = "${weather.currentTemp}°"
            binding.tvWeather.text = weather.weather
            binding.tvTempRange.text = "最高: ${weather.highTemp}° 最低: ${weather.lowTemp}°"

            // 白天天气
            binding.tvDayWeather.text = weather.dayWeather
            binding.tvDayTemp.text = "${weather.dayTemp}°"
            binding.tvDayWind.text = weather.dayWind

            // 夜间天气
            binding.tvNightWeather.text = weather.nightWeather
            binding.tvNightTemp.text = "${weather.nightTemp}°"
            binding.tvNightWind.text = weather.nightWind
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}