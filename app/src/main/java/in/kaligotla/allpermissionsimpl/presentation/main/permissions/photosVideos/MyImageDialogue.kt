package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.photosVideos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import java.io.File
import java.io.FileInputStream

@Composable
fun MyImageDialogue(selectedItem: File, onDismissRequest: () -> Unit) {

    Dialog(onDismissRequest = { onDismissRequest() }) {
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
                    Image(
                        rememberAsyncImagePainter(File(selectedItem.path)),
                        contentDescription = "image",
                        modifier = Modifier.fillMaxSize()
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