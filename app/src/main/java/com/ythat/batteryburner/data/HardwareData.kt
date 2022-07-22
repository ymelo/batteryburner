package com.ythat.batteryburner.data

class HardwareData(): HardwareDataSource {

    override fun cpuCoreCount(): Int {
        return Runtime.getRuntime().availableProcessors()
    }
}