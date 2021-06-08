package com.test.openweathermap.persanter

import com.test.openweathermap.MyContract
import com.test.openweathermap.WeatherData
import com.test.openweathermap.container.Callback
import com.test.openweathermap.data.datasource.repository.MyRepository
import com.test.openweathermap.utils.Utils

class MyPresenter(myView: MyContract.View, var repository: MyRepository) :
    MyContract.Presenter {
    private lateinit var view: MyContract.View

    init {
        view = myView
        view.setPresenter(this)
    }

    override fun onGetWeatherUpdate() {
        repository.getWeatherUpdate(object : Callback<WeatherData> {
            override fun onResponse(response: WeatherData?) {
                view.getWeatherUpdate(
                    // Here for now getting zero position weather description data
                    Utils.LAT + response?.coord?.lat.toString() + "\n" + Utils.LON + response?.coord?.lon.toString(),
                    Utils.DISCRIPTION + response?.weather!![Utils.ZERO].description
                )
            }

            override fun onError(errorCode: String, errorMessage: String) {
                view.getDataError(errorCode, errorMessage)
            }

        })
    }
}
