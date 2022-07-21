package com.ythat.batteryburner.data

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


internal class HardwareRepositoryTest {
    private val fakeDataSource = FakeHardwareSource()
    private val sut = HardwareRepository(fakeDataSource)
    @Test
    fun `hardwareInfo with data source returns core count`() {
        fakeDataSource.coreCount = 0
        assertThat(sut.hardwareInfo().coreCount, `is`(0))
        fakeDataSource.coreCount = 22
        assertThat(sut.hardwareInfo().coreCount, `is`(22))
    }
}

internal class FakeHardwareSource: HardwareDataSource {
    var coreCount = 0
    override fun cpuCoreCount(): Int {
        return coreCount
    }
}