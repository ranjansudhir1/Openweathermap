package com.test.openweathermap.data.datasource

import com.test.openweathermap.WeatherData
import com.test.openweathermap.container.Callback

interface MyDataSource {
    fun getWeatherUpdate(callback: Callback<WeatherData>)
}