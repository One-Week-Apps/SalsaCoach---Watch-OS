package com.oneweekapps.salsacoachwearos.utils

import android.os.Handler
import android.os.Looper

interface ClockFacadeInterface {
    fun getCurrentTimeMillis(): Long
    fun getCurrentTimeSeconds(): Long
    fun delay(callback: (() -> Unit), delayMillis: Long = 2000)
    fun invokeRepeating(callback: (() -> Unit), delayMillis: Long)
    fun stopInvokeRepeating()
}

class ClockFacade : ClockFacadeInterface {
    private val handler = Handler()

    override fun getCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

    override fun getCurrentTimeSeconds(): Long {
        return System.currentTimeMillis()/1000
    }

    override fun delay(callback: (() -> Unit), delayMillis: Long) {
        if (delayMillis == 0L) {
            callback()
        } else {
            handler.postDelayed({
                callback()
            }, delayMillis)
        }
    }

    val mainHandler = Handler(Looper.getMainLooper())
    var runnable: Runnable? = null
    override fun invokeRepeating(callback: (() -> Unit), delayMillis: Long) {
        runnable = object : Runnable {
            override fun run() {
                callback()
                mainHandler.postDelayed(this, delayMillis)
            }
        }
        mainHandler.post(runnable)
    }

    override fun stopInvokeRepeating() {
        mainHandler.removeCallbacks(runnable)
    }
}