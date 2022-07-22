package com.ythat.batteryburner

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import io.mockk.*
import org.junit.Before
import org.junit.Test

class CameraWrapperTest {
    val cw = CameraWrapper()

    // Mocks
    val ctx: Context = mockk()
    val cm: CameraManager = mockk()
    val characteristics: CameraCharacteristics = mockk()

    @Before
    fun setUp() {
        every { ctx.getSystemService(any()) } returns cm
        every { cm.getCameraCharacteristics(any()) } returns characteristics
        justRun { cm.setTorchMode(any(), any()) }
    }

    @Test
    fun `changing the flashlight is done on the first camera available`() {
        // Setting up CameraWrapper returning a specific Camera
        every { cm.cameraIdList } returns arrayOf("0", "1", "2")
        every { characteristics.get<Boolean>(any()) } returns  true


        cw.flashlight(true, ctx)
        verify { cm.setTorchMode("0", true) }
        cw.flashlight(false, ctx)
        verify { cm.setTorchMode("0", false) }
    }

    @Test
    fun `changing the flashlight with no camera does nothing`() {
        // Setting up CameraWrapper returning a specific Camera
        every { cm.cameraIdList } returns emptyArray()
        cw.flashlight(true, ctx)
        verify(exactly = 0) { cm.setTorchMode("0", true) }
        cw.flashlight(false, ctx)
        verify(exactly = 0) { cm.setTorchMode("0", false) }
    }

    @Test
    fun `changing the flashlight with camera with no torchlight`() {
        // Setting up CameraWrapper returning a specific Camera
        every { cm.cameraIdList } returns arrayOf("0")
        every { characteristics.get<Boolean>(any()) } returns  false
        cw.flashlight(true, ctx)
        verify(exactly = 0) { cm.setTorchMode("0", true) }
        cw.flashlight(false, ctx)
        verify(exactly = 0) { cm.setTorchMode("0", false) }
    }
}