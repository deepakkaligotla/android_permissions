package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.photosVideos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import `in`.kaligotla.allpermissionsimpl.navigation.Screen
import `in`.kaligotla.allpermissionsimpl.presentation.rememberUserTheme
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme
import java.io.File

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class)
@Composable
fun MyPhotosVideos(
    userTheme: AppTheme,
    drawerState: DrawerState,
    viewModel: MyPhotosVideosViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            "android.permission.ACCESS_MEDIA_LOCATION",
            "android.permission.READ_MEDIA_IMAGES",
            "android.permission.READ_MEDIA_VIDEO"
        )
    )
    var photoLazyList by rememberSaveable { mutableStateOf(emptyMap<String, List<File>>()) }
    var videoLazyList by rememberSaveable { mutableStateOf(emptyMap<String, List<File>>()) }
    var photoExtensionsList by rememberSaveable { mutableStateOf(emptyList<String>()) }
    var videoExtensionsList by rememberSaveable { mutableStateOf(emptyList<String>()) }
    val mainPager = rememberPagerState(pageCount = { 2 })
    val showItem = rememberSaveable { mutableStateOf(false) }

    AllPermissionsImplTheme(appTheme = userTheme) {
        Scaffold(
            modifier = Modifier.testTag("myTableTag"),
            topBar = { AppBar(drawerState = drawerState) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (multiplePermissionsState.allPermissionsGranted) {
                    if (photoLazyList.isEmpty() && videoLazyList.isEmpty()) {
                        if (photoLazyList.isEmpty()) {
                            photoExtensionsList.toMutableList().clear()
                            viewModel.createCursor(context)
                            photoExtensionsList = viewModel.isPhoto
                            for (extension in photoExtensionsList) {
                                if (viewModel.photoVideosFilesList.groupBy(keySelector = {
                                        it.name.substringAfterLast(
                                            '.',
                                            ""
                                        ).contains(extension)
                                    })[true] != null) {
                                    photoLazyList += mapOf(
                                        Pair(
                                            extension,
                                            viewModel.photoVideosFilesList.groupBy(keySelector = {
                                                it.name.substringAfterLast(
                                                    '.',
                                                    ""
                                                ).contains(extension)
                                            })[true]!!
                                        )
                                    )
                                }
                            }
                        }
                        if (videoLazyList.isEmpty()) {
                            videoExtensionsList.toMutableList().clear()
                            viewModel.createCursor(context)
                            videoExtensionsList = viewModel.isVideo
                            for (extension in videoExtensionsList) {
                                if (viewModel.photoVideosFilesList.groupBy(keySelector = {
                                        it.name.substringAfterLast(
                                            '.',
                                            ""
                                        ).contains(extension)
                                    })[true] != null) {
                                    videoLazyList += mapOf(
                                        Pair(
                                            extension,
                                            viewModel.photoVideosFilesList.groupBy(keySelector = {
                                                it.name.substringAfterLast(
                                                    '.',
                                                    ""
                                                ).contains(extension)
                                            })[true]!!
                                        )
                                    )
                                }
                            }
                        }
                    } else {
                        HorizontalPager(state = mainPager) {
                            when(it) {
                                0 -> MyPhotoScreenComponent(photoExtensionsList, photoLazyList, showItem)
                                1 -> MyVideoScreenComponent(videoExtensionsList, videoLazyList, showItem, context)
                            }
                        }
                    }
                } else {
                    Button(onClick = {
                        multiplePermissionsState.launchMultiplePermissionRequest()
                    }) {
                        Text("Grant Photos & Videos permission")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyPhotosVideosScreenPreview() {
    AllPermissionsImplTheme(appTheme = rememberUserTheme()) {
        Screen.MyPhotosVideosScreen
    }
}