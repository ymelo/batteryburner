package com.ythat.batteryburner

import android.app.Application
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager

/**
 * Splitting the Android implementation from ours
 */
class CameraWrapper {
    fun flashlight(enabled: Boolean, context: Context) {
        val cm = context.getSystemService(Application.CAMERA_SERVICE) as CameraManager
        if (cm.cameraIdList.isNotEmpty()) {
            val firstId = cm.cameraIdList[0]
            val characteristics = cm.getCameraCharacteristics(firstId)
            if (characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true) {
                cm.setTorchMode(firstId, enabled)
            }
        }
    }
}