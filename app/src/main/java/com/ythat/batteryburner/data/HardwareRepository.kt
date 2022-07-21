package com.ythat.batteryburner.data

import com.ythat.batteryburner.HardwareStatistics

interface HardwareDataSource {
    fun cpuCoreCount() : Int
}

class HardwareRepository(private val source: HardwareDataSource = HardwareData()) {

    fun hardwareInfo() : HardwareStatistics {
        return HardwareStatistics("Unknown", source.cpuCoreCount())
    }
}