package com.ythat.batteryburner

import android.app.Application
import android.hardware.Camera
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Log
import androidx.lifecycle.*
import com.ythat.batteryburner.data.HardwareRepository
import kotlinx.coroutines.*
import kotlin.math.pow


data class HardwareStatistics(val cpuName: String, val coreCount: Int)

class DrainageViewModel(application: Application): AndroidViewModel(application) {
    private val hardwareRepository = HardwareRepository()
    private var currentJob: Job? = null

    private val keepAwake: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().also { it.value = false }
    }

    val cpuCores: Int = hardwareRepository.hardwareInfo().coreCount

    fun keepAwake() : LiveData<Boolean> {
        return keepAwake
    }

    fun makeCpuBurn(concurrentThreads: Int = 0) {
        currentJob?.cancel()
        Log.d("job", "Current job $currentJob")
        if (concurrentThreads > 0) {
            currentJob = viewModelScope.launch(CoroutineName("Yoplay")) {
                burn()
            }
        }
    }

    fun keepAwake(awake: Boolean) {
        keepAwake.value = awake
    }

    private suspend fun burn() {
        withContext(Dispatchers.IO) {
            while(isActive) {
                val i = Math.random()
                println("POWA ${i.toDouble().pow(i.toDouble())}")
            }
        }
    }

    fun flashlight(enabled: Boolean) {
        val cw = CameraWrapper()
        cw.flashlight(enabled, getApplication<Application>().applicationContext)

    }
}