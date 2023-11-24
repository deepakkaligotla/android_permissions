package `in`.kaligotla.allpermissionsimpl.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import `in`.kaligotla.allpermissionsimpl.presentation.intro.IntroViewModel
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.MainBottomBar
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.MainTopBar
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.MyNavigationDrawer
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCompose(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    vm: IntroViewModel = hiltViewModel()
) {
    val orientation = LocalConfiguration.current.orientation
    var onboardState by rememberSaveable { mutableStateOf(true) }
    vm.onboardState.observe(LocalLifecycleOwner.current) {
        onboardState = vm.onboardState.value!!
    }

    AllPermissionsImplTheme {
        Scaffold(
            modifier = Modifier.testTag("MainCompose"),
            topBar = { MainTopBar(onboardState, drawerState) },
            bottomBar = { MainBottomBar(onboardState, navController) },
            content = {
                it
                Surface {
                    MyNavigationDrawer(navController, drawerState, onboardState)
                }
            }
        )
    }
}

enum class NavRoutes {
    IntroRoute,
    MainRoute,
    SubRoute
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainActivityPreview() {
    MainCompose()
}