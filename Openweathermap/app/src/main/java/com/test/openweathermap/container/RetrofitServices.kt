package com.test.openweathermap.container

class RetrofitServices {
    companion object {
        fun create(): MyApiServices {
            return RetrofitClient.getClientInstance()!!.create(MyApiServices::class.java)
        }
    }
}