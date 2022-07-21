package com.ythat.batteryburner

import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ythat.batteryburner.data.HardwareRepository
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.math.pow


data class HardwareStatistics(val cpuName: String, val coreCount: Int)

class DrainageViewModel: ViewModel() {
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
//                delay(100)
                val i = Math.random()
                println("POWA ${i.toDouble().pow(i.toDouble())}")
//                i.toDouble().pow(i.toDouble())
            }
        }
    }

}