package com.test.openweathermap

class MyContract {
    interface View {
        fun setPresenter(presenter: Presenter?)
        fun getWeatherUpdate(description: String, coord: String)
        fun getDataError(errorCode: String, errorMessage: String)
    }

    interface Presenter {
        fun onGetWeatherUpdate()
    }
}