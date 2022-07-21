package com.ythat.batteryburner.data

import org.junit.Assert
import org.junit.Test


internal class HardwareRepositoryTest {
    private val fakeDataSource = FakeHardwareSource()
    private val sut = HardwareRepository(fakeDataSource)
    @Test
    fun `hardwareInfo returns core count`() {
        Assert.assertEquals(0, sut.hardwareInfo().coreCount)
        fakeDataSource.coreCount = 22
        Assert.assertEquals(22, sut.hardwareInfo().coreCount)
    }
}

internal class FakeHardwareSource: HardwareDataSource {
    var coreCount = 0
    override fun cpuCoreCount(): Int {
        return coreCount
    }
}