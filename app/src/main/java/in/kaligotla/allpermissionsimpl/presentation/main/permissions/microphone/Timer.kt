package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.microphone

import android.os.Handler
import android.os.Looper

class Timer(listner: OnTimerTickListner) {

    interface OnTimerTickListner{
        fun onTimerTick(duration: String)
    }

    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    private var duration = 0L
    private var delay = 100L

    init {
        runnable = Runnable {
            duration += delay
            handler.postDelayed(runnable, delay)
            listner.onTimerTick(format())
        }
    }

    fun start() {
        handler.postDelayed(runnable, delay)
    }

    fun pause() {
        handler.removeCallbacks(runnable)
    }

    fun stop() {
        handler.removeCallbacks(runnable)
        duration = 0L
    }

    private fun format(): String {
        val millis = duration.mod(1000)
        val seconds = (duration.div(1000)).mod(60)
        val minutes = (duration.div(1000 * 60)).mod(60)
        val hours = (duration.div(1000 * 60 * 60))

        return if (hours > 0)
            "%02d:%02d:%02d.%02d".format(hours, minutes, seconds, millis)
        else if (minutes > 0) "%02d:%02d.%02d".format(minutes, seconds, millis)
        else "%02d.%02d".format(seconds, millis)
    }
}