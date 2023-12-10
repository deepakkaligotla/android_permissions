package `in`.kaligotla.allpermissionsimpl.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import `in`.kaligotla.allpermissionsimpl.R
import `in`.kaligotla.allpermissionsimpl.core.DrawerParams
import `in`.kaligotla.allpermissionsimpl.navigation.MainNavOption
import `in`.kaligotla.allpermissionsimpl.navigation.mainGraph
import `in`.kaligotla.allpermissionsimpl.navigation.subGraph
import `in`.kaligotla.allpermissionsimpl.presentation.intro.IntroViewModel
import `in`.kaligotla.allpermissionsimpl.presentation.intro.introGraph
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.MainBottomBar
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.MainTopBar
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.MyNavigationDrawer
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.MyObserver
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appdrawer.AppDrawerContent
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainCompose(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    vm: IntroViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var userTheme by rememberSaveable { mutableStateOf( AppTheme.Default) }
    var myDestinaton = NavRoutes.IntroRoute.name
    val observer = MyObserver(context, Handler(Looper.getMainLooper()))

    DisposableEffect(Unit) {
        observer.registerObserver()
        onDispose {
            observer.unregisterObserver()
        }
    }

    LaunchedEffect(LocalContext) {
        myDestinaton = if(vm.onboardState) NavRoutes.MainRoute.name else NavRoutes.IntroRoute.name
        vm.userTheme.observeForever {
            userTheme = it.userTheme
        }
    }

    AllPermissionsImplTheme(appTheme = userTheme) {
        Scaffold(
            modifier = Modifier.testTag("MainCompose"),
            topBar = { MainTopBar(true, drawerState, vm) },
            bottomBar = { MainBottomBar(true, navController) }
        ) { it
            Surface {
                MyNavigationDrawer(navController, userTheme, drawerState, true)
            }
        }
    }
}

@Composable
fun rememberUserTheme(): AppTheme {
    return AppTheme.Dark
}

enum class NavRoutes {
    IntroRoute,
    MainRoute,
    SubRoute
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainActivityPreview() {
    MainCompose()
}