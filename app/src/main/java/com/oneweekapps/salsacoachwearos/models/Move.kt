package com.oneweekapps.salsacoachwearos.models

data class Move(
    @JvmField val name: String,
    @JvmField val tempo: String,
    @JvmField val description: String,
    @JvmField val urlString: String,
    @JvmField val difficulty: Int
)