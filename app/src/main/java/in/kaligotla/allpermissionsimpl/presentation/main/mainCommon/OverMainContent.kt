package `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OverMainContent(
    title: String,
    imageVector: ImageVector,
    iconOnTop: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.65f)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        if (iconOnTop) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                imageVector = imageVector,
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color.White
        )
        if (!iconOnTop) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                imageVector = imageVector,
                contentDescription = null,
                tint = Color.White
            )
        }
        Spacer(Modifier.height(8.dp))
    }
}