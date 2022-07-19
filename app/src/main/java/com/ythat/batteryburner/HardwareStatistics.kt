package com.ythat.batteryburner

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlin.math.pow


data class HardwareStatistics(val cpuName: String, val coreCount: Int)

class HardwareStatisticsRepository() {
    private var processNames = listOf("/system/bin/cat", "/proc/cpuinfo")
    fun hardwareInfo() : HardwareStatistics {
        val processBuilder = ProcessBuilder(processNames)
//        processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE)
        return HardwareStatistics("Unknown", Runtime.getRuntime().availableProcessors())
    }
}

class DrainageViewModel: ViewModel() {
    private var currentJob: Job? = null
    val keepAwake: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().also { it.value = false }
    }

    fun makeCpuBurn(concurrentThreads: Int = 0) {
        currentJob?.cancel()
        Log.d("job", "Current job $currentJob")
        if (concurrentThreads > 0) {
            currentJob = viewModelScope.launch {
                burn()
            }
        }

    }

    private suspend fun burn() {
        withContext(Dispatchers.IO) {
            for(i in 0..100) {
                ensureActive()
                delay(100)
                println("POWA ${i.toDouble().pow(i.toDouble())}")
//                i.toDouble().pow(i.toDouble())
            }
        }
    }

}