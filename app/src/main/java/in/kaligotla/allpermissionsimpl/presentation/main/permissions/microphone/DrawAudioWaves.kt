package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.microphone

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun DrawAudioWaves(amplitudeList: List<Int>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Black)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawWaveform(amplitudeList, color = Color.White, stroke = Stroke(5.dp.toPx()))
        }
    }
}

fun DrawScope.drawWaveform(amplitudeList: List<Int>, color: Color, stroke: Stroke) {
    if (amplitudeList.isEmpty()) return

    val amplitudeCount = amplitudeList.size
    val width = size.width
    val height = size.height

    val amplitudeStep = width / amplitudeCount.toFloat()

    val path = Path()

    amplitudeList.forEachIndexed { index, amplitude ->
        val x = index * amplitudeStep
        val y = height - (amplitude.toFloat() / Short.MAX_VALUE) * height / 2
        if (index == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }

    drawPath(path = path, color = color, style = stroke)
}