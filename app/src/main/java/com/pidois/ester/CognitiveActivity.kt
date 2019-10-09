package com.pidois.ester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast
import com.pidois.ester.ExerciseClass

private var level: Int = 0
private var time: Int = 0
private var sequence: String = ""

class CognitiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chronometer = findViewById<Chronometer>(R.id.chronometer)

        val button = findViewById<Button>(R.id.button)
        button?.setOnClickListener(object : View.OnClickListener {

            internal var isPlaying = false

            override fun onClick(v: View) {
                if (!isPlaying) {
                    chronometer.start()
                    isPlaying = true
                } else {
                    chronometer.stop()
                    isPlaying = false
                }

                button.setText(if (isPlaying) R.string.start else R.string.stop)
                Toast.makeText(this@CognitiveActivity, getString(if (isPlaying) R.string.playing else R.string.stopped), Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun countAnswers():Int{

    }

    fun showRightAnswers():Int{

    }
}
