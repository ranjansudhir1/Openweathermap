package com.test.openweathermap

import com.test.openweathermap.data.model.Coord
import com.test.openweathermap.data.model.Weather
data class WeatherData(
    val weather: MutableList<Weather> = mutableListOf(),
    val coord: Coord
)