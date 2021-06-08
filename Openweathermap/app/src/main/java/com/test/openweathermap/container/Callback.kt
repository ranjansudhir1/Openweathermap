package com.test.openweathermap.container

import com.test.openweathermap.WeatherData

interface Callback<T> {
    fun onResponse(response: WeatherData?)
    fun onError(errorCode: String, errorMessage: String)
}