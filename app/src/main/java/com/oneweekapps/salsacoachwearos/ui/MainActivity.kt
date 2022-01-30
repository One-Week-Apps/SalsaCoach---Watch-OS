package com.oneweekapps.salsacoachwearos.ui

import android.os.Bundle
import android.os.VibrationEffect
import android.support.wearable.activity.WearableActivity
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.oneweekapps.salsacoachwearos.R
import com.oneweekapps.salsacoachwearos.adapters.VibratorAdapter
import com.oneweekapps.salsacoachwearos.adapters.VibratorHardwareAdapter
import com.oneweekapps.salsacoachwearos.models.Move
import com.oneweekapps.salsacoachwearos.usecases.VibrationPatternsFactory
import com.oneweekapps.salsacoachwearos.utils.ClockFacade
import com.oneweekapps.salsacoachwearos.utils.ClockFacadeInterface


class MainActivity : WearableActivity() {
    companion object {
        private const val MOVE_DURATION_MS = 8000L
    }

    private val vibrationPatternsFactory = VibrationPatternsFactory()
    private val clockFacade: ClockFacadeInterface = ClockFacade()
    private val vibratorAdapter: VibratorAdapter = VibratorHardwareAdapter(this)

    private lateinit var listView: ListView
    private lateinit var startButton: Button

    private var currentVibrationPatternIndex = 0
    private var vibrationPatterns: Array<VibrationEffect> = arrayOf()

    private var isStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()

        // Composition root
        val moves = arrayOf(
            Move("Sombrero", "Key Times: 4 * 4", "The Sombrero move", "https://www.youtube.com/embed/AqnNTeRs2Pw", 1),
            Move("El Uno", "Key Times: 4 * 4", "The El Uno move", "https://www.youtube.com/embed/Imw-H_bQb1c", 2),
            Move("El Dos", "Key Times: 4 * 4", "The El Dos move", "https://www.youtube.com/embed/WQXHNy77fgY", 2),
            Move("Montana", "Key Times: 4 * 4", "The Sombrero Manolito move", "https://www.youtube.com/embed/A8MCYkUZRXk", 2),
            Move("Coca Cola", "Key Times: 4 * 4", "The Coca Cola move", "https://www.youtube.com/embed/C8N58KGoGyw", 2),
            Move("Kentucky", "Key Times: 4 * 4", "The Kentucky move", "https://www.youtube.com/embed/6CXoxcQ3bAk", 2),
            Move("Tour Magico", "Key Times: 4 * 4", "The Tour Magico move", "https://www.youtube.com/embed/mG_J0xQy8O8", 2),
            Move("Vacilala Vacilense", "Key Times: 4 * 4", "The Vacilala Vacilense move", "https://www.youtube.com/embed/Obl71WOOjJ4", 2),
            Move("Enchufela Doble", "Key Times: 4 * 4", "The Enchufela Doble move", "https://www.youtube.com/embed/r2KcM4wxOC4", 2),
            Move("Exhibela Doble", "Key Times: 4 * 4", "The Exhibela Doble move", "https://www.youtube.com/embed/o4nkV-0Ts9Q", 2),
            Move("Cementario", "Key Times: 4 * 4", "The Cementario move", "https://www.youtube.com/embed/itBdgUz4hMA", 3),
            Move("Nudo", "Key Times: 4 * 4", "The Nudo move", "https://www.youtube.com/embed/cUHjbSiQU9Y", 3)
        )
        moves.shuffle()

        vibrationPatterns = vibrationPatternsFactory.makeVibrationPatterns(moves)

        listView = findViewById(R.id.list_view)
        startButton = findViewById(R.id.button)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            moves.map { "${it.name} (difficulty: ${it.difficulty})" }
        )
        listView.adapter = adapter

        listView.onItemClickListener = OnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position) as String
            val index = moves.indexOfFirst { item.contains(it.name) }
            val pattern = vibrationPatterns[index]
            vibratorAdapter.vibrate(pattern)
        }

        startButton.setOnClickListener {
            if (isStarted) {
                runOnUiThread {
                    startButton.text = getString(R.string.start)
                }
                stop()
            } else {
                runOnUiThread {
                    startButton.text = getString(R.string.stop)
                }
                start()
            }
            isStarted = !isStarted
        }

    }

    private fun start() {
        clockFacade.invokeRepeating({
            currentVibrationPatternIndex =
                (currentVibrationPatternIndex + 1) % vibrationPatterns.size
            vibratorAdapter.vibrate(vibrationPatterns[currentVibrationPatternIndex])
        }, MOVE_DURATION_MS)
    }

    private fun stop() {
        currentVibrationPatternIndex = 0
        clockFacade.stopInvokeRepeating()
    }

}
