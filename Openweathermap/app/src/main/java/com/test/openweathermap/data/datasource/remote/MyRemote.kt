package com.test.openweathermap.data.datasource.remote

import com.test.openweathermap.WeatherData
import com.test.openweathermap.container.MyApiServices
import com.test.openweathermap.container.RetrofitServices
import com.test.openweathermap.data.datasource.MyDataSource
import com.test.openweathermap.data.model.Param
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRemote(private val services: MyApiServices, mParam: Param) : MyDataSource {
    private var param: Param = mParam

    companion object {
        @Volatile
        private var remote: MyRemote? = null
        fun getInstance(mParam: Param): MyRemote {
            if (remote == null) {
                synchronized(this) {
                    if (remote == null) {
                        remote = MyRemote(RetrofitServices.create(), mParam)
                    }
                }
            }
            return remote!!
        }
    }

    override fun getWeatherUpdate(callback: com.test.openweathermap.container.Callback<WeatherData>) {
        services.getWeatherData(param.zip, param.app_id)?.enqueue(object : Callback<WeatherData?> {
            override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                callback.onError(t.message.toString(), t.localizedMessage)
            }

            override fun onResponse(call: Call<WeatherData?>, response: Response<WeatherData?>) {
                callback.onResponse(response.body())
            }
        })
    }
}