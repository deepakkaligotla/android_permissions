package `in`.kaligotla.allpermissionsimpl.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun MySimpleDialogueBox(text: String, onDismissRequest: () -> Unit) {

    val scrollState = rememberScrollState()
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = Modifier.wrapContentSize(Alignment.Center)
        ) {
            Box(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .verticalScroll(scrollState)
                        .wrapContentSize(Alignment.Center, unbounded = false),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(5.dp),
                        textAlign = TextAlign.Center,
                        softWrap = true,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .height(18.dp).width(18.dp)
            ) {
                IconButton(
                    onClick = {
                        onDismissRequest.invoke()
                    },
                    modifier = Modifier
                    .background(Color.Red, CircleShape)
                    .height(18.dp).width(18.dp)
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