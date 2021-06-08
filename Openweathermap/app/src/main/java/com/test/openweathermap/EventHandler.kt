package com.test.openweathermap

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/*
@EventHandler Used for performing Periodic Request using work manager.
* */
class EventHandler(context: Context, workParameters: WorkerParameters) :
    Worker(context, workParameters) {
    override fun doWork(): Result {
        Log.d("Main Event Handler", "ENQUEUED")
        return Result.success()
    }

}