package com.pidois.ester

import android.icu.util.TimeZone
import android.os.SystemClock
import android.text.format.Time
import android.widget.Chronometer
import java.util.*

class ExerciseClass {
    private var level:Int = 0

    private var time:Timer = 1000

    private var mode:Int = 0

    private var sequence:String = ""

    fun keepLevel():Int{

    }

    fun sendLevel():Int{

    }

    fun receiveSequence():String{

    }

    fun showSequence():String{

    }

    fun countTime(){
        var view_timer : object : Chronometer {

        }
        view_timer.isCountDown = true
        view_timer.base = SystemClock.elapsedRealtime() + 20000
        view_timer.start()
    }

    fun receiveReply(){

    }

    fun countExercise(){

    }

    fun decode(){

    }
}