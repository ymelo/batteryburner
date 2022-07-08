package com.ythat.batteryburner


data class HardwareStatistics(val cpuName: String, val coreCount: Int)

class HardwareStatisticsProvider() {
    private var processNames = listOf("/system/bin/cat", "/proc/cpuinfo")
    fun hardwareInfo() : HardwareStatistics {
        return HardwareStatistics("test", Runtime.getRuntime().availableProcessors())
    }
}