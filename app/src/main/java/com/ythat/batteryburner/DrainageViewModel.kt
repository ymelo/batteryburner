package com.ythat.batteryburner

import android.app.Application
import android.os.HardwarePropertiesManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ythat.batteryburner.data.HardwareRepository
import kotlinx.coroutines.*
import kotlin.math.pow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


data class HardwareStatistics(val cpuName: String, val coreCount: Int)

class DrainageViewModel(application: Application): AndroidViewModel(application) {
    private val hardwareRepository = HardwareRepository()
    private var currentJobs: MutableList<Job> = mutableListOf()

    private val keepAwake: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().also { it.value = false }
    }

    val cpuCores: Int = hardwareRepository.hardwareInfo().coreCount

    fun keepAwake() : LiveData<Boolean> {
        return keepAwake
    }

    fun makeCpuBurn(concurrentThreads: Int = 0) {
        for(job in currentJobs) {
            job.cancel()
        }
        Log.d("job", "Current job $currentJobs")
        for(i in 1..concurrentThreads) {
            Log.d("CPU", "Starting on new single thread $i")
            currentJobs.add(viewModelScope.launch(context = newSingleThreadContext("test$i")) {
                burn()
            })
        }
    }

    fun keepAwake(awake: Boolean) {
        keepAwake.value = awake
    }

    private suspend fun burn() {
        withContext(Dispatchers.IO) {
            while(isActive) {
                val i = Math.random() * 10000
                i.pow(i)
            }
        }
    }

    fun flashlight(enabled: Boolean) {
        val cw = CameraWrapper()
        cw.flashlight(enabled, getApplication<Application>().applicationContext)
    }
}