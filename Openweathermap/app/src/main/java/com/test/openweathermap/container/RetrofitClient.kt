package com.test.openweathermap.container

import com.test.openweathermap.utils.Utils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/*
HTTP Client Request
* */
class RetrofitClient {
    companion object {
        @Volatile
        private var client: Retrofit? = null
        fun getClientInstance(): Retrofit? {
            if (client == null) {
                synchronized(this) {
                    if (client == null) {
                        client = Retrofit.Builder()
                            .baseUrl(Utils.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create()).build()
                            .also {
                                client = it
                            }
                    }
                }
            }
            return client
        }

    }
}