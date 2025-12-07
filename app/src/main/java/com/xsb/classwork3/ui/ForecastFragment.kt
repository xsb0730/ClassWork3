package com.xsb.classwork3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xsb.ClassWork3.databinding.FragmentForecastBinding
import com.xsb.classwork3.adapter.ForecastAdapter
import com.xsb.classwork3.viewmodel.WeatherViewModel

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherViewModel
    private lateinit var adapter: ForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 获取 Activity 级别的 ViewModel
        viewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = ForecastAdapter()
        binding.recyclerViewForecast.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ForecastFragment.adapter
        }
    }

    private fun observeViewModel() {
        // 观察城市名称
        viewModel.weatherData.observe(viewLifecycleOwner) { weather ->
            binding.tvForecastCity.text = weather.city
        }

        // 观察未来天气列表
        viewModel.forecastList.observe(viewLifecycleOwner) { forecasts ->
            adapter.submitList(forecasts)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}