package com.xsb.classwork3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xsb.ClassWork3.R
import com.xsb.classwork3.model.ForecastUiModel

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    private var forecastList = listOf<ForecastUiModel>()

    fun submitList(list: List<ForecastUiModel>) {
        forecastList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(forecastList[position], position)
    }

    override fun getItemCount() = forecastList.size

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvWeek: TextView = itemView.findViewById(R.id.tvWeek)
        private val tvWeather: TextView = itemView.findViewById(R.id.tvWeather)
        private val tvWeatherIcon: TextView = itemView.findViewById(R.id.tvWeatherIcon)
        private val tvHighTemp: TextView = itemView.findViewById(R.id.tvHighTemp)
        private val tvLowTemp: TextView = itemView.findViewById(R.id.tvLowTemp)

        fun bind(forecast: ForecastUiModel, position: Int) {
            tvDate.text = forecast.date
            // 第一个显示"今天"
            tvWeek.text = if (position == 0) "今天" else forecast.week
            tvWeather.text = forecast.weather
            tvWeatherIcon.text = forecast.weatherIcon
            tvHighTemp.text = forecast.highTemp
            tvLowTemp.text = forecast.lowTemp
        }
    }
}