package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.microphone

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("MissingPermission")
class AudioRecorder(
    private val context: Context,
    private val outputFile: File,
    private val amplitudeList: MutableList<Int>,
    private val onVisualizerReady: (Job) -> Unit
): Timer.OnTimerTickListner {
    private var recorder: MediaRecorder? = null
    private lateinit var timer: Timer
    private var _recordingDuration = MutableLiveData<String>()
    private var visualizerJob: Job? = null
    var recordingDuration: LiveData<String>
        get() = _recordingDuration
        set(value) {

        }

    fun startRecording() {
        timer = Timer(this)
        try {
            recorder = MediaRecorder(this.context).apply {
                setAudioSource(MediaRecorder.AudioSource.DEFAULT)
                setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
                setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
                setOutputFile(FileOutputStream(outputFile).fd)
                prepare()
            }
        } catch (e: IOException) {
            Toast.makeText(this.context, "IOException"+e.message, Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            Toast.makeText(this.context, "IllegalStateException"+e.message, Toast.LENGTH_SHORT).show()
        }
        recorder!!.start()
        timer.start()
        visualizerJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                try {
                    val maxAmplitude = recorder!!.maxAmplitude
                    amplitudeList.add(maxAmplitude)
                    delay(1)
                } catch (e: IllegalStateException) {
                    break
                }
            }
        }
        onVisualizerReady(visualizerJob!!)
    }

    fun pauseRecording() {
        recorder!!.pause()
        timer.pause()
    }

    fun resumeRecording() {
        recorder!!.resume()
        timer.start()
    }

    fun stopRecording() {
        visualizerJob?.cancel()
        onVisualizerReady(visualizerJob!!)
        timer.stop()
        recorder?.stop()
        recorder?.reset()
        recorder?.release()
        recorder = null
    }

    override fun onTimerTick(duration: String) {
        _recordingDuration.value = duration
    }
}