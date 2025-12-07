package com.xsb.classwork3

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xsb.ClassWork3.databinding.ActivityMainBinding
import com.xsb.classwork3.ui.ForecastFragment
import com.xsb.classwork3.ui.WeatherFragment
import com.xsb.classwork3.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WeatherViewModel

    private var currentCity = "广州"

    // 颜色常量
    private val unselectedColor by lazy { ColorStateList.valueOf(0x66FFFFFF.toInt()) }
    private val selectedColor by lazy { ColorStateList.valueOf(0xFF007BFF.toInt()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化 ViewModel
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        // 设置按钮监听
        setupCityButtons()
        setupBottomButtons()
        observeViewModel()

        // 默认显示天气页面
        replaceFragment(WeatherFragment())

        // 初始化所有按钮为未选中状态，然后选中广州
        initCityButtons()

        // 设置默认选中城市按钮
        updateBottomButtonStyle(binding.btnCity)

        // 加载默认城市天气
        viewModel.loadWeather(currentCity)
    }

    private fun initCityButtons() {
        // 先全部设置为未选中
        binding.btnBeijing.backgroundTintList = unselectedColor
        binding.btnShanghai.backgroundTintList = unselectedColor
        binding.btnGuangzhou.backgroundTintList = unselectedColor
        binding.btnShenzhen.backgroundTintList = unselectedColor

        // 设置默认选中广州
        binding.btnGuangzhou.backgroundTintList = selectedColor
    }

    private fun setupCityButtons() {
        binding.btnBeijing.setOnClickListener {
            selectCity("北京", binding.btnBeijing)
        }
        binding.btnShanghai.setOnClickListener {
            selectCity("上海", binding.btnShanghai)
        }
        binding.btnGuangzhou.setOnClickListener {
            selectCity("广州", binding.btnGuangzhou)
        }
        binding.btnShenzhen.setOnClickListener {
            selectCity("深圳", binding.btnShenzhen)
        }
    }

    private fun setupBottomButtons() {
        binding.btnCity.setOnClickListener {
            replaceFragment(WeatherFragment())
            updateBottomButtonStyle(binding.btnCity)
        }
        binding.btnForecast.setOnClickListener {
            replaceFragment(ForecastFragment())
            updateBottomButtonStyle(binding.btnForecast)
        }
    }

    private fun selectCity(cityName: String, selectedButton: Button) {
        // 如果点击的是当前城市，不重复加载
        if (currentCity == cityName) {
            return
        }

        currentCity = cityName

        // 更新按钮样式
        updateCityButtonStyle(selectedButton)

        // 加载新城市天气
        viewModel.loadWeather(cityName)
    }

    private fun updateCityButtonStyle(selectedButton: Button) {
        // 重置所有按钮为未选中状态
        binding.btnBeijing.backgroundTintList = unselectedColor
        binding.btnShanghai.backgroundTintList = unselectedColor
        binding.btnGuangzhou.backgroundTintList = unselectedColor
        binding.btnShenzhen.backgroundTintList = unselectedColor

        // 设置选中按钮为蓝色
        selectedButton.backgroundTintList = selectedColor
    }

    private fun updateBottomButtonStyle(selectedView: View) {
        // 未选中：透明背景
        val unselectedBackground = 0x00000000.toInt()
        // 选中：浅灰半透明背景
        val selectedBackground = 0x33FFFFFF.toInt()

        // 重置所有底部按钮为透明
        binding.btnCity.setBackgroundColor(unselectedBackground)
        binding.btnForecast.setBackgroundColor(unselectedBackground)

        // 设置选中按钮为浅灰色
        selectedView.setBackgroundColor(selectedBackground)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    private fun observeViewModel() {
        // 观察加载状态
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // 观察错误信息
        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}