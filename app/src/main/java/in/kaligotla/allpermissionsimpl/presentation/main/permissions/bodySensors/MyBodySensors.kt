package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import `in`.kaligotla.allpermissionsimpl.R
import `in`.kaligotla.allpermissionsimpl.navigation.Screen
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.CustomHorizontalPager
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.OverMainContent
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.common.SensorStateListener
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.components.CSButton
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.components.CSButtonPosition
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.components.SensorItem
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.components.SensorItemHiddenContent
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.AccelerometerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.AmbientTemperatureSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.GameRotationVectorSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.GeomagneticRotationVectorSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.GravitySensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.GyroscopeSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.HeadTrackerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.HeadingSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.HeartBeatSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.HeartRateSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.HingeAngleSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.LightSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.LimitedAxesAccelerometerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.LimitedAxesGyroscopeSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.LinearAccelerationSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.LowLatencyOffBodyDetectSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.MagneticFieldSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.MotionDetectSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.PressureSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.ProximitySensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.RotationVectorSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.SignificantMotionSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.StationaryDetectSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.StepCounterSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.StepDetectorSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.UncalibratedAccelerometerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.UncalibratedGyroscopeSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.UncalibratedLimitedAxesAccelerometerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.UncalibratedMagneticFieldSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberAccelerometerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberAmbientTemperatureSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberGameRotationVectorSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberGeomagneticRotationVectorSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberGravitySensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberGyroscopeSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberHeadTrackerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberHeadingSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberHeartBeatSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberHeartRateSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberHingeAngleSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberLightSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberLimitedAxesAccelerometerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberLimitedAxesGyroscopeSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberLinearAccelerationSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberLowLatencyOffBodyDetectSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberMagneticFieldSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberMotionDetectSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberPressureSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberProximitySensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberRotationVectorSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberSignificantMotionSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberStationaryDetectSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberStepCounterSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberStepDetectorSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberUncalibratedAccelerometerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberUncalibratedGyroscopeSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberUncalibratedLimitedAxesAccelerometerSensorState
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.sensorStates.rememberUncalibratedMagneticFieldSensorState
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme
import kotlinx.coroutines.launch

private val sensorStateList: List<SensorStateListener>
    @SuppressLint("NewApi")
    @Composable
    get() = listOf(
        rememberAccelerometerSensorState(),
        rememberMagneticFieldSensorState(),
        rememberGyroscopeSensorState(),
        rememberLightSensorState(),
        rememberPressureSensorState(),
        rememberProximitySensorState(),
        rememberGravitySensorState(),
        rememberLinearAccelerationSensorState(),
        rememberRotationVectorSensorState(),
        rememberAmbientTemperatureSensorState(),
        rememberUncalibratedMagneticFieldSensorState(),
        rememberGameRotationVectorSensorState(),
        rememberUncalibratedGyroscopeSensorState(),
        rememberSignificantMotionSensorState(onMotionEvent = {}),
        rememberStepDetectorSensorState(),
        rememberStepCounterSensorState(),
        rememberGeomagneticRotationVectorSensorState(),
        rememberHeartRateSensorState(),
        rememberStationaryDetectSensorState(),
        rememberMotionDetectSensorState(),
        rememberHeartBeatSensorState(),
        rememberLowLatencyOffBodyDetectSensorState(),
        rememberUncalibratedAccelerometerSensorState(),
        rememberHingeAngleSensorState(),
        rememberHeadTrackerSensorState(),
        rememberLimitedAxesAccelerometerSensorState(),
        rememberLimitedAxesGyroscopeSensorState(),
        rememberUncalibratedLimitedAxesAccelerometerSensorState(),
        rememberHeadingSensorState()
    )

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun MyBodySensors(
    drawerState: DrawerState,
    viewModel: MyBodySensorsViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val sensorStates by rememberUpdatedState(sensorStateList)
    val pagerState = rememberPagerState()
//    val swipeRefreshState = rememberPullRefreshState(true, onRefresh =)
    val bodySensorsPermissionState = rememberPermissionState("android.permission.BODY_SENSORS")
    val bodySensorsBackgroundPermissionState =
        rememberPermissionState("android.permission.BODY_SENSORS_BACKGROUND")

    LaunchedEffect(pagerState.currentPage) {
        sensorStates.forEachIndexed { index, listener ->
            if (pagerState.currentPage == index) {
                listener.startListening()
            } else {
                listener.stopListening()
            }
        }
    }
    AllPermissionsImplTheme {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()
        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )
            onDispose {}
        }
        Scaffold(
            modifier = Modifier.testTag("myTableTag"),
            topBar = { AppBar(drawerState = drawerState) }
        ) { padding ->
            if (bodySensorsPermissionState.status.isGranted) {
                if (bodySensorsBackgroundPermissionState.status.isGranted) {
                    Scaffold(
                        bottomBar = {
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
                                            pagerState.scrollToPage(sensorStates.size - 1)
                                        }
                                    },
                                    position = CSButtonPosition.End,
                                    enabled = pagerState.currentPage != sensorStates.size - 1
                                )
                            }
                        }
                    ) {
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            CustomHorizontalPager(
                                count = sensorStateList.size,
                                initialHorizontalPadding = 64.dp,
                                state = pagerState,
                                initialWidth = 300.dp,
                                targetWidth = maxWidth,
                                mainContent = { index, isExpanded ->
                                    when (val state = sensorStates[index]) {
                                        is AccelerometerSensorState -> {
                                            SensorItem(
                                                name = "Accelerometer",
                                                imageRef = R.drawable.accelerometer,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is MagneticFieldSensorState -> {
                                            SensorItem(
                                                name = "Magnetic Field",
                                                imageRef = R.drawable.magnetic_field,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is GyroscopeSensorState -> {
                                            SensorItem(
                                                name = "Gyroscope",
                                                imageRef = R.drawable.gyroscope,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is LightSensorState -> {
                                            SensorItem(
                                                name = "Light",
                                                imageRef = R.drawable.light,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is PressureSensorState -> {
                                            SensorItem(
                                                name = "Pressure",
                                                imageRef = R.drawable.pressure,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is ProximitySensorState -> {
                                            SensorItem(
                                                name = "Proximity",
                                                imageRef = R.drawable.proximity,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is GravitySensorState -> {
                                            SensorItem(
                                                name = "Gravity",
                                                imageRef = R.drawable.gravity,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is LinearAccelerationSensorState -> {
                                            SensorItem(
                                                name = "Linear Acceleration",
                                                imageRef = R.drawable.linear_acceleration,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is RotationVectorSensorState -> {
                                            SensorItem(
                                                name = "Rotation Vector",
                                                imageRef = R.drawable.rotation_vector,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is AmbientTemperatureSensorState -> {
                                            SensorItem(
                                                name = "Ambient Temperature",
                                                imageRef = R.drawable.ambient_temperature,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is UncalibratedMagneticFieldSensorState -> {
                                            SensorItem(
                                                name = "Magnetic Field (Uncalibrated)",
                                                imageRef = R.drawable.magnetic_field,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is GameRotationVectorSensorState -> {
                                            SensorItem(
                                                name = "Game Rotation Vector",
                                                imageRef = R.drawable.game_rotation_vector,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is UncalibratedGyroscopeSensorState -> {
                                            SensorItem(
                                                name = "Gyroscope (Uncalibrated)",
                                                imageRef = R.drawable.gyroscope,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is SignificantMotionSensorState -> {
                                            // Trigger motion event whenever its card is selected
                                            LaunchedEffect(Unit) {
                                                state.requestEventTrigger()
                                            }

                                            SensorItem(
                                                name = "Significant Motion",
                                                imageRef = R.drawable.significant_motion,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is StepDetectorSensorState -> {
                                            SensorItem(
                                                name = "Step Detector",
                                                imageRef = R.drawable.step_detection,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is StepCounterSensorState -> {
                                            SensorItem(
                                                name = "Step Counter",
                                                imageRef = R.drawable.step_counter,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is GeomagneticRotationVectorSensorState -> {
                                            SensorItem(
                                                name = "Geomagnetic Rotation Vector",
                                                imageRef = R.drawable.geomagnetic_rotation_vector,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is HeartRateSensorState -> {
                                            SensorItem(
                                                name = "Heart Rate",
                                                imageRef = R.drawable.heart_rate,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is StationaryDetectSensorState -> {
                                            SensorItem(
                                                name = "Stationary Detect",
                                                imageRef = R.drawable.stationary_detect,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is MotionDetectSensorState -> {
                                            SensorItem(
                                                name = "Motion Detect",
                                                imageRef = R.drawable.motion_detect,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is HeartBeatSensorState -> {
                                            SensorItem(
                                                name = "Heart Beat",
                                                imageRef = R.drawable.heart_rate,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is LowLatencyOffBodyDetectSensorState -> {
                                            SensorItem(
                                                name = "Low Latency Off-Body Detect",
                                                imageRef = R.drawable.low_latency_off_body_detect,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is UncalibratedAccelerometerSensorState -> {
                                            SensorItem(
                                                name = "Accelerometer (Uncalibrated)",
                                                imageRef = R.drawable.accelerometer,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is HingeAngleSensorState -> {
                                            SensorItem(
                                                name = "Hinge Angle",
                                                imageRef = R.drawable.hinge_angle,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is HeadTrackerSensorState -> {
                                            SensorItem(
                                                name = "Head Tracker",
                                                imageRef = R.drawable.head_tracker,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is LimitedAxesAccelerometerSensorState -> {
                                            SensorItem(
                                                name = "Accelerometer (Limited Axes)",
                                                imageRef = R.drawable.accelerometer,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is LimitedAxesGyroscopeSensorState -> {
                                            SensorItem(
                                                name = "Gyroscope (Limited Axes)",
                                                imageRef = R.drawable.gyroscope,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is UncalibratedLimitedAxesAccelerometerSensorState -> {
                                            SensorItem(
                                                name = "Accelerometer (Uncalibrated - Limited Axes)",
                                                imageRef = R.drawable.accelerometer,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }

                                        is HeadingSensorState -> {
                                            SensorItem(
                                                name = "Heading",
                                                imageRef = R.drawable.heading,
                                                isAvailable = state.isAvailable,
                                                isExpanded = isExpanded
                                            )
                                        }
                                    }
                                },
                                overMainContentCollapsed = {
                                    OverMainContent(
                                        title = "Details",
                                        imageVector = Icons.Default.KeyboardArrowDown
                                    )
                                },
                                overMainContentExpanded = {
                                    OverMainContent(
                                        title = "Close",
                                        imageVector = Icons.Default.KeyboardArrowUp,
                                        iconOnTop = true
                                    )
                                },
                                hiddenContent = { index ->
                                    when (val state = sensorStates[index]) {
                                        is AccelerometerSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Force X" to state.xForce,
                                                    "Force Y" to state.yForce,
                                                    "Force Z" to state.zForce
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is MagneticFieldSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Strength X" to state.xStrength,
                                                    "Strength Y" to state.yStrength,
                                                    "Strength Z" to state.zStrength
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is GyroscopeSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Rotation X" to state.xRotation,
                                                    "Rotation Y" to state.yRotation,
                                                    "Rotation Z" to state.zRotation
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is LightSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Illuminance" to state.illuminance),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is PressureSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Pressure" to state.pressure),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is ProximitySensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Distance" to state.sensorDistance),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is GravitySensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Force X" to state.xForce,
                                                    "Force Y" to state.yForce,
                                                    "Force Z" to state.zForce
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is LinearAccelerationSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Force X" to state.xForce,
                                                    "Force Y" to state.yForce,
                                                    "Force Z" to state.zForce
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is RotationVectorSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Vector X" to state.vectorX,
                                                    "Vector Y" to state.vectorY,
                                                    "Vector Z" to state.vectorZ,
                                                    "Scalar" to state.scalar,
                                                    "Heading Accuracy" to state.estimatedHeadingAccuracy
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is AmbientTemperatureSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Temperature" to state.temperature),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is UncalibratedMagneticFieldSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Strength X" to state.xStrength,
                                                    "Strength Y" to state.yStrength,
                                                    "Strength Z" to state.zStrength,
                                                    "Bias X" to state.xBias,
                                                    "Bias Y" to state.yBias,
                                                    "Bias Z" to state.zBias
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is GameRotationVectorSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Vector X" to state.vectorX,
                                                    "Vector Y" to state.vectorY,
                                                    "Vector Z" to state.vectorZ
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is UncalibratedGyroscopeSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Rotation X" to state.xRotation,
                                                    "Rotation Y" to state.yRotation,
                                                    "Rotation Z" to state.zRotation,
                                                    "Bias X" to state.xBias,
                                                    "Bias Y" to state.yBias,
                                                    "Bias Z" to state.zBias
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is SignificantMotionSensorState -> {
                                            // Trigger motion event whenever its card is selected
                                            LaunchedEffect(Unit) {
                                                state.requestEventTrigger()
                                            }

                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Listening?" to state.isListening,
                                                    "Timestamp (ns)" to state.lastEventTimestamp
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is StepDetectorSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Step Count" to state.stepCount),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is StepCounterSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Step Count" to state.stepCount),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is GeomagneticRotationVectorSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Vector X" to state.vectorX,
                                                    "Vector Y" to state.vectorY,
                                                    "Vector Z" to state.vectorZ
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is HeartRateSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Heart Rate" to state.heartRate),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is StationaryDetectSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Is device stationary?" to state.isDeviceStationary),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is MotionDetectSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Is device moving?" to state.isDeviceInMotion),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is HeartBeatSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Is confident peak?" to state.isConfidentPeak),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is LowLatencyOffBodyDetectSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Is On Body?" to state.isDeviceOnBody),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is UncalibratedAccelerometerSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Force X" to state.xForce,
                                                    "Force Y" to state.yForce,
                                                    "Force Z" to state.zForce,
                                                    "Bias X" to state.xBias,
                                                    "Bias Y" to state.yBias,
                                                    "Bias Z" to state.zBias
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is HingeAngleSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Angle" to state.angle),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is HeadTrackerSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Rotation X" to state.xRotation,
                                                    "Rotation Y" to state.yRotation,
                                                    "Rotation Z" to state.zRotation,
                                                    "Velocity X" to state.xAngularVelocity,
                                                    "Velocity Y" to state.yAngularVelocity,
                                                    "Velocity Z" to state.zAngularVelocity
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is LimitedAxesAccelerometerSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Force X" to state.xForce,
                                                    "Force Y" to state.yForce,
                                                    "Force Z" to state.zForce,
                                                    "X Supported?" to state.xAxisSupported,
                                                    "Y Supported?" to state.yAxisSupported,
                                                    "Z Supported?" to state.zAxisSupported
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is LimitedAxesGyroscopeSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Rotation X" to state.xRotation,
                                                    "Rotation Y" to state.yRotation,
                                                    "Rotation Z" to state.zRotation,
                                                    "X Supported?" to state.xAxisSupported,
                                                    "Y Supported?" to state.yAxisSupported,
                                                    "Z Supported?" to state.zAxisSupported
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is UncalibratedLimitedAxesAccelerometerSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf(
                                                    "Rotation X" to state.xForce,
                                                    "Rotation Y" to state.yForce,
                                                    "Rotation Z" to state.zForce,
                                                    "Bias X" to state.xBias,
                                                    "Bias Y" to state.yBias,
                                                    "Bias Z" to state.zBias,
                                                    "X Supported?" to state.xAxisSupported,
                                                    "Y Supported?" to state.yAxisSupported,
                                                    "Z Supported?" to state.zAxisSupported
                                                ),
                                                isAvailable = state.isAvailable
                                            )
                                        }

                                        is HeadingSensorState -> {
                                            SensorItemHiddenContent(
                                                sensorValues = mapOf("Degrees" to state.degrees),
                                                isAvailable = state.isAvailable
                                            )
                                        }
                                    }
                                },
                                onTransform = { isExpanded -> }
                            )
                        }
                    }
                } else {
                    Button(onClick = {
                        bodySensorsBackgroundPermissionState.launchPermissionRequest()
                    }) {
                        Text("Grant background permission")
                    }
                }
            } else {
                Button(onClick = {
                    bodySensorsPermissionState.launchPermissionRequest()
                }) {
                    Text("Grant Body Sensors permission")
                }
            }
        }
    }
}

@Preview
@Composable
fun MyBodySensorsScreenPreview() {
    Screen.MyBodySensorsScreen
}