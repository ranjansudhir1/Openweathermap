package com.test.openweathermap.data.datasource.repository

import com.test.openweathermap.WeatherData
import com.test.openweathermap.container.Callback
import com.test.openweathermap.data.datasource.MyDataSource
import com.test.openweathermap.data.datasource.remote.MyRemote
import com.test.openweathermap.data.model.Param

open class MyRepository(private val dataSource: MyDataSource) : MyDataSource {
    override fun getWeatherUpdate(callback: Callback<WeatherData>) {
        dataSource.getWeatherUpdate(object : Callback<WeatherData> {
            override fun onResponse(response: WeatherData?) {
                callback.onResponse(response)
            }

            override fun onError(errorCode: String, errorMessage: String) {
                callback.onError(errorCode, errorMessage)
            }

        })

    }

    companion object {
        @Volatile
        private var repository: MyRepository? = null
        fun getInstance(mParam: Param): MyRepository {
            if (repository == null) {
                synchronized(this) {
                    if (repository == null) {
                        repository = MyRepository(MyRemote.getInstance(mParam))
                    }
                }
            }
            return repository!!
        }
    }
}