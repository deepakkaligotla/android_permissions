package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.microphone

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.S)
@HiltViewModel
class MyMicrophoneViewModel @Inject constructor(

) : ViewModel() {
    var player: MediaPlayer = MediaPlayer()
    var files by mutableStateOf(emptyArray<File>())

    fun getAllRecordings(context: Context) {
        files = File(context.getExternalFilesDir(Environment.DIRECTORY_RECORDINGS)?.path!!).listFiles()
    }

    @RequiresApi(34)
    fun startPlaying(context: Context, filePath: File) {
        player.reset()
        try {
            player.let {
                it.setDataSource(FileInputStream(filePath.path).fd)
                it.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setFlags(AudioAttributes.USAGE_MEDIA)
                        .setLegacyStreamType(AudioManager.USE_DEFAULT_STREAM_TYPE)
                        .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                        .setUsage(AudioAttributes.USAGE_UNKNOWN)
                        .build())
                it.prepareAsync()
            }
        } catch (e: IOException) {
            Toast.makeText(context, "IOException"+e.message, Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            Toast.makeText(context, "IllegalStateException"+e.message, Toast.LENGTH_SHORT).show()
        }
        player.setOnPreparedListener { player -> player.start() }
    }

    fun stopPlaying() {
        player.let {
            it.stop()
            it.reset()
            it.release()
        }
    }
}