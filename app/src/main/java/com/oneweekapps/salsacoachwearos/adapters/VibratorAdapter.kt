package com.oneweekapps.salsacoachwearos.adapters

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.wearable.activity.WearableActivity

interface VibratorAdapter {
    fun vibrate(vibrationEffect: VibrationEffect)
}

class VibratorHardwareAdapter(
    private val context: Context
) : VibratorAdapter {
    override fun vibrate(vibrationEffect: VibrationEffect) {
        val vibrator = context.getSystemService(WearableActivity.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(vibrationEffect)
    }
}