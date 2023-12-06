package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SensorItem(
    name: String,
    @DrawableRes imageRef: Int,
    isAvailable: Boolean,
    isExpanded: Boolean
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.width(if (isExpanded) LocalConfiguration.current.screenWidthDp.dp else 300.dp)
                .height(if (isExpanded) LocalConfiguration.current.screenHeightDp.minus(400).dp else LocalConfiguration.current.screenHeightDp.dp),
            tonalElevation = 8.dp,
            shadowElevation = 16.dp,
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .alpha(if (isAvailable) 1f else 0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    if (isAvailable) "(Available)" else "(Unavailable)",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isAvailable) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(3.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageRef)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}

@Composable
fun SensorItemHiddenContent(
    sensorValues: Map<String, Any>,
    isAvailable: Boolean
) {
    Column {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(1.dp)
                    .padding(bottom = 130.dp)
                    .alpha(if (isAvailable) 1f else 0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier.heightIn(
                        max = LocalConfiguration.current.screenHeightDp.dp.times(0.1f)
                    )
                ) {
                    val sensorValueList = sensorValues.entries.toList()
                    if (sensorValueList.size > 3) {
                        item {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = "Scroll down to see more",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    items(sensorValueList.size) { index ->
                        val sensorValue: Map.Entry<String, Any> = sensorValueList[index]
                        SensorValue(
                            title = sensorValue.key,
                            value = sensorValue.value,
                        )
                    }
                }
            }
        }
    }
}
