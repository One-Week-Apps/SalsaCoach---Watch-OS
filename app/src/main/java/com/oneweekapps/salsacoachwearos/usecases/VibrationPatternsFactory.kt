package com.oneweekapps.salsacoachwearos.usecases

import android.os.VibrationEffect
import com.oneweekapps.salsacoachwearos.models.EncodedMove
import com.oneweekapps.salsacoachwearos.models.Move
import com.oneweekapps.salsacoachwearos.models.VibrationDuration

/**
 * Encode move patterns using the following convention.
 * Moves are encoded with up to 3 notes and have two states: short and long.
 * With 3 notes there is 8 possible codes.
 * With 2 notes there is 4 possible codes.
 * With 1 note there is 2 possible codes.
 * So we can support a grammar of 14 different moves.
 */
class VibrationPatternsFactory {
    companion object {
        const val ON_AMPLITUDE = 40
        const val OFF_AMPLITUDE = 0
        const val DELAY_BETWEEN_NOTES_MS = 200L
        const val SHORT_DURATION_MS = 300L
        const val LONG_DURATION_MS = 600L
    }

    fun makeVibrationPatterns(movesList: Array<Move>): Array<VibrationEffect> {
        if (movesList.isEmpty())
            return arrayOf()

        val vibrationEffects: MutableList<VibrationEffect> = mutableListOf()
        movesList.forEach { move ->
            val encodedMove = encodedMoveFromMove(move)
            val effect = effectForMove(encodedMove)
            vibrationEffects.add(effect)
        }

        return vibrationEffects.toTypedArray()
    }

    private fun encodedMoveFromMove(move: Move): EncodedMove {
        return when (move.name) {
            "Sombrero" -> EncodedMove(
                arrayOf(
                    VibrationDuration.LONG,
                    VibrationDuration.SHORT,
                    VibrationDuration.LONG
                )
            )
            "El Uno" -> EncodedMove(arrayOf(VibrationDuration.SHORT))
            "El Dos" -> EncodedMove(arrayOf(VibrationDuration.SHORT, VibrationDuration.SHORT))
            "Montana" -> EncodedMove(
                arrayOf(
                    VibrationDuration.SHORT,
                    VibrationDuration.LONG,
                    VibrationDuration.SHORT
                )
            )
            "Coca Cola" -> EncodedMove(arrayOf(VibrationDuration.LONG))
            "Kentucky" -> EncodedMove(
                arrayOf(
                    VibrationDuration.LONG,
                    VibrationDuration.LONG,
                    VibrationDuration.LONG
                )
            )
            "Tour Magico" -> EncodedMove(
                arrayOf(
                    VibrationDuration.SHORT,
                    VibrationDuration.SHORT,
                    VibrationDuration.SHORT
                )
            )
            "Vacilala Vacilense" -> EncodedMove(
                arrayOf(
                    VibrationDuration.LONG,
                    VibrationDuration.LONG
                )
            )
            "Enchufela Doble" -> EncodedMove(
                arrayOf(
                    VibrationDuration.SHORT,
                    VibrationDuration.LONG,
                    VibrationDuration.LONG
                )
            )
            "Exhibela Doble" -> EncodedMove(
                arrayOf(
                    VibrationDuration.LONG,
                    VibrationDuration.LONG,
                    VibrationDuration.SHORT
                )
            )
            "Cementario" -> EncodedMove(
                arrayOf(
                    VibrationDuration.SHORT,
                    VibrationDuration.SHORT,
                    VibrationDuration.LONG
                )
            )
            "Nudo" -> EncodedMove(
                arrayOf(
                    VibrationDuration.LONG,
                    VibrationDuration.SHORT,
                    VibrationDuration.SHORT
                )
            )
            else -> EncodedMove(arrayOf())
        }
    }

    private fun effectForMove(encodedMove: EncodedMove): VibrationEffect {
        val vibrationDurations = encodedMove.vibrationDurations
        val timings: MutableList<Long> = mutableListOf()
        val amplitudes: MutableList<Int> = mutableListOf()

        for (index in vibrationDurations.indices) {

            timings.add(DELAY_BETWEEN_NOTES_MS)
            amplitudes.add(OFF_AMPLITUDE)

            when (vibrationDurations[index]) {
                VibrationDuration.SHORT -> {
                    timings.add(SHORT_DURATION_MS)
                    amplitudes.add(ON_AMPLITUDE)
                }
                VibrationDuration.LONG -> {
                    timings.add(LONG_DURATION_MS)
                    amplitudes.add(ON_AMPLITUDE)
                }
            }

        }

        return VibrationEffect.createWaveform(timings.toTypedArray().toLongArray(), amplitudes.toTypedArray().toIntArray(), -1)
    }

}
