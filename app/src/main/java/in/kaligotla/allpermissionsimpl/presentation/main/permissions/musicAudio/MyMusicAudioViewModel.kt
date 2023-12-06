package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.musicAudio

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MyMusicAudioViewModel @Inject constructor(

) : ViewModel() {
    private var filesArray = ArrayList<File>()
    private var audioMusicFilesExtensionArray = ArrayList<String>()
    var audioMusicFilesList by mutableStateOf(emptyList<File>())
    var audioMusicFilesExtensionList by mutableStateOf(emptyList<String>())
    var player: MediaPlayer = MediaPlayer()
    var isPlaying by mutableStateOf(false)
    val isAudio = listOf("8svx", "aac", "ac3", "aiff", "amb", "au", "avr", "caf",
        "cdda", "cvs", "cvsd", "cvu", "dts", "dvms", "fap", "flac", "fssd", "gsrt", "hcom",
        "htk", "ima", "ircam", "m4a", "m4r", "maud", "mp2", "mp3", "nist", "oga", "ogg",
        "opus", "paf", "prc", "pvf", "ra", "sd2", "sln", "smp", "snd", "sndr", "sndt",
        "sou", "sph", "spx", "tta", "txw", "vms", "voc", "vox", "w64", "wav", "wma",
        "wv", "wve")

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("Range")
    fun createCursor(context: Context) {

        val filesInternal: Cursor? = context.contentResolver.query(
            MediaStore.Files.getContentUri("internal"),
            null,
            null,
            null,
            null
        )

        val filesExternal: Cursor? = context.contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            null,
            null,
            null,
            null
        )

//        combinedResults(filesInternal)
        combinedResults(filesExternal)
    }

    @SuppressLint("Range")
    private fun combinedResults(cursor: Cursor?) {
        while(cursor?.moveToNext()==true) {
            val filePath1 = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA))
            val fileExtension = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)).substringAfterLast('.', "")
            check(File(filePath1), fileExtension)
        }
        cursor?.close()
        audioMusicFilesList = filesArray.toList()
        audioMusicFilesExtensionList = audioMusicFilesExtensionArray.toList()
    }

    private fun check(temp: File, fileExtension: String) {
        for(extension in isAudio) {
            if(extension==fileExtension && !audioMusicFilesExtensionArray.contains(fileExtension)) {
                audioMusicFilesExtensionArray.add(fileExtension)
            }
        }
        if(audioMusicFilesExtensionArray.contains(fileExtension) && !filesArray.contains(temp)) {
            filesArray.add(temp)
        }
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
        isPlaying = player.isPlaying
    }

    fun stopPlaying() {
        player.let {
            it.stop()
            it.reset()
        }
    }
}