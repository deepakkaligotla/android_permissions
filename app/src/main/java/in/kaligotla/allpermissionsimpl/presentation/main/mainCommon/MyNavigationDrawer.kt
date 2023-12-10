package `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import `in`.kaligotla.allpermissionsimpl.core.DrawerParams
import `in`.kaligotla.allpermissionsimpl.navigation.MainNavOption
import `in`.kaligotla.allpermissionsimpl.navigation.mainGraph
import `in`.kaligotla.allpermissionsimpl.navigation.subGraph
import `in`.kaligotla.allpermissionsimpl.presentation.NavRoutes
import `in`.kaligotla.allpermissionsimpl.presentation.intro.introGraph
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appdrawer.AppDrawerContent

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNavigationDrawer(
    navController: NavHostController,
    userTheme: AppTheme,
    drawerState: DrawerState,
    onboardState: Boolean
) {
    ModalNavigationDrawer(
        modifier = Modifier.testTag("drawerNavigation"),
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                drawerState = drawerState,
                menuItems = DrawerParams.drawerButtons,
                defaultPick = MainNavOption.MyHome
            ) { onUserPickedOption ->
                when (onUserPickedOption) {
                    MainNavOption.MyHome -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyBodySensors -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyCalendar -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyCallLogs -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyCamera -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyContacts -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyLocation -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyMicrophone -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyMusicAudio -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyNearbyDevices -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyNotifications -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyPhone -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyPhotosVideos -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyPhysicalActivity -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MySms -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.MyBiometrics -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.SettingsScreen -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    MainNavOption.AboutScreen -> {
                        navController.navigate(onUserPickedOption.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }

                    else -> {}
                }
            }
        },
        gesturesEnabled = true
    ) {
        NavHost(
            navController,
            startDestination = if(onboardState) NavRoutes.MainRoute.name else NavRoutes.IntroRoute.name
        ) {
            introGraph(navController)
            mainGraph(navController, drawerState, userTheme)
            subGraph(navController, drawerState, userTheme)
        }
    }
}