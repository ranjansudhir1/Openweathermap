package com.test.openweathermap

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.test.openweathermap.data.datasource.repository.MyRepository
import com.test.openweathermap.data.model.Param
import com.test.openweathermap.databinding.ActivityMainBinding
import com.test.openweathermap.persanter.MyPresenter
import com.test.openweathermap.utils.Utils
import java.util.concurrent.TimeUnit
import androidx.work.PeriodicWorkRequest


class MainActivity : AppCompatActivity(), MyContract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MyPresenter
    private lateinit var workManager: WorkManager
    private lateinit var workRequest: PeriodicWorkRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val param = Param(Utils.ZIP_ID, Utils.APP_ID)
        MyPresenter(this, MyRepository.getInstance(param))
        presenter.onGetWeatherUpdate()
        getWorkManager()
    }

    override fun onDestroy() {
        super.onDestroy()
        workManager.cancelWorkById(workRequest.id)
    }

    override fun setPresenter(presenter: MyContract.Presenter?) {
        this.presenter = presenter as MyPresenter
    }

    override fun getWeatherUpdate(weatherDiscription: String, latLong: String) {
        binding.txtMainWeather.text = weatherDiscription
        binding.txtMainLatLon.text = latLong
    }

    override fun getDataError(errorCode: String, errorMessage: String) {
        //Not Yet Implemented, This block is used to show error to the user
    }

    //Work Manager
    private fun getWorkManager(): Unit {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        workRequest = PeriodicWorkRequestBuilder<EventHandler>(Utils.TWO_HOUR, TimeUnit.HOURS)
            .setConstraints(constraint)
            .addTag(Utils.ADD_TAG)
            .build()
        workManager = WorkManager.getInstance()
        workManager.enqueueUniquePeriodicWork(
            Utils.ADD_TAG, ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, object : Observer<WorkInfo?> {
                override fun onChanged(info: WorkInfo?) {
                    if (info != null && info.state == WorkInfo.State.ENQUEUED) {
                        presenter.onGetWeatherUpdate()
                    }
                }
            })
    }
}