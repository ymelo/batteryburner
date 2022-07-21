package com.ythat.batteryburner.data

import com.ythat.batteryburner.HardwareStatistics

class HardwareData: HardwareDataSource {
    override fun cpuCoreCount(): Int {
        return Runtime.getRuntime().availableProcessors()
    }
}