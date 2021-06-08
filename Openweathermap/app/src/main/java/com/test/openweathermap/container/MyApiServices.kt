package com.test.openweathermap.container

import com.test.openweathermap.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApiServices {
    @GET("data/2.5/weather?")
    fun getWeatherData(
        @Query("zip") zip: String?,
        @Query("appid") app_id: String?
    ): Call<WeatherData?>?
}