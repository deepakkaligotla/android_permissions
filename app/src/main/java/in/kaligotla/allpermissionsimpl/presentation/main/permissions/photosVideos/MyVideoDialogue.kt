package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.photosVideos

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_READY
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView as ExoPlayerView
import java.io.File

@Composable
fun MyVideoDialogue(
    context: Context,
    selectedItem: File,
    onDismissRequest: () -> Unit
) {
    val video = Uri.parse(selectedItem.path)
    val exoPlayerView = remember { ExoPlayerView(context) }
    var player: ExoPlayer? = null
    val playerListener = object: Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when(playbackState){
                STATE_ENDED -> restartPlayer()
                STATE_READY -> play()
            }
        }

        fun play(){
            player?.playWhenReady = true
        }

        fun restartPlayer(){
            player?.seekTo(0)
            player?.playWhenReady = true
        }
    }
    player = ExoPlayer.Builder(context)
        .build()
        .apply {
            setMediaItem(MediaItem.fromUri(video))
            prepare()
            addListener(playerListener)
        }

    Dialog(onDismissRequest = {
        onDismissRequest()
        player!!.release()
        player = null
    }) {
        Box(
            modifier = Modifier.wrapContentSize(Alignment.Center)
                .height(300.dp).fillMaxWidth().padding(10.dp)
        ) {
            Box(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .wrapContentSize(Alignment.Center, unbounded = false),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    DisposableEffect(player) {
                        exoPlayerView.player = player
                        onDispose {
                            exoPlayerView.player = null
                        }
                    }

                    AndroidView(
                        factory = { exoPlayerView },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .height(18.dp)
                    .width(18.dp)
            ) {
                IconButton(
                    onClick = {
                        onDismissRequest.invoke()
                    },
                    modifier = Modifier
                        .background(Color.Red, CircleShape)
                        .height(18.dp)
                        .width(18.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = Color.White,
                        contentDescription = "Close")
                }
            }
        }
    }
}