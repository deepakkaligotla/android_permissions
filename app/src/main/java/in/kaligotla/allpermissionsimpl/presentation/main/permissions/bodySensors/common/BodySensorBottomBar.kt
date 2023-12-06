package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.PagerState
import `in`.kaligotla.allpermissionsimpl.R
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.components.CSButton
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.components.CSButtonPosition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BodySensorBottomBar(coroutineScope: CoroutineScope, pagerState: PagerState, sensorStates: Int) {
    Row(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(bottom = 90.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CSButton(
            text = "Previous",
            icon = R.drawable.left_arrow,
            onClick = {
                coroutineScope.launch {
                    tween<Float>(durationMillis = 700)
                    pagerState.animateScrollToPage(
                        page = pagerState.currentPage - 1
                    )
                }
            },
            onLongClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(0)
                }
            },
            position = CSButtonPosition.Start,
            enabled = pagerState.currentPage != 0
        )
        CSButton(
            text = "Next",
            icon = R.drawable.right_arrow,
            onClick = {
                coroutineScope.launch {
                    tween<Float>(durationMillis = 700)
                    pagerState.animateScrollToPage(
                        page = pagerState.currentPage + 1
                    )
                }
            },
            onLongClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(sensorStates - 1)
                }
            },
            position = CSButtonPosition.End,
            enabled = pagerState.currentPage != sensorStates- 1
        )
    }
}