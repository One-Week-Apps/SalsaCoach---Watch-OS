package com.oneweekapps.salsacoachwearos.models

data class EncodedMove(
    @JvmField val vibrationDurations: Array<VibrationDuration>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncodedMove

        if (!vibrationDurations.contentEquals(other.vibrationDurations)) return false

        return true
    }

    override fun hashCode(): Int {
        return vibrationDurations.contentHashCode()
    }

    override fun toString(): String {
        return this.vibrationDurations.joinToString(", ") { it.name }
    }
}
