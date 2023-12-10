package `in`.kaligotla.allpermissionsimpl.navigation

import android.os.Build
import androidx.compose.material3.DrawerState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import `in`.kaligotla.allpermissionsimpl.presentation.NavRoutes
import `in`.kaligotla.allpermissionsimpl.presentation.about.AboutScreen
import `in`.kaligotla.allpermissionsimpl.presentation.main.home.myHome.MyHome
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.bodySensors.MyBodySensors
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.calendar.MyCalendar
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.callLogs.MyCallLogs
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.camera.MyCamera
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.contacts.MyContacts
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.extras.security.MyBiometrics
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.location.MyLocation
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.microphone.MyMicrophone
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.musicAudio.MyMusicAudio
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.nearbyDevices.MyNearbyDevices
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.notifications.MyNotifications
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.phone.MyPhone
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.photosVideos.MyPhotosVideos
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.physicalActivity.MyPhysicalActivity
import `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.sms.MySms
import `in`.kaligotla.allpermissionsimpl.presentation.settings.SettingsScreen
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme

fun NavGraphBuilder.mainGraph(navController: NavHostController, drawerState: DrawerState, userTheme: AppTheme) {
    navigation(startDestination = MainNavOption.MyHome.name, route = NavRoutes.MainRoute.name) {
        composable(MainNavOption.MyHome.name) {
            MyHome(userTheme, drawerState)
        }

        composable(MainNavOption.MyBodySensors.name) {
            MyBodySensors(userTheme, drawerState)
        }

        composable(MainNavOption.MyCalendar.name) {
            MyCalendar(userTheme, drawerState)
        }

        composable(MainNavOption.MyCallLogs.name) {
            MyCallLogs(userTheme, drawerState)
        }

        composable(MainNavOption.MyCamera.name) {
            MyCamera(userTheme, drawerState)
        }

        composable(MainNavOption.MyContacts.name) {
            MyContacts(userTheme, drawerState)
        }

        composable(MainNavOption.MyLocation.name) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MyLocation(userTheme, drawerState)
            }
        }

        composable(MainNavOption.MyMicrophone.name) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                MyMicrophone(userTheme, drawerState)
            }
        }

        composable(MainNavOption.MyMusicAudio.name) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                MyMusicAudio(userTheme, drawerState)
            }
        }

        composable(MainNavOption.MyNearbyDevices.name) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                MyNearbyDevices(userTheme, drawerState)
            }
        }

        composable(MainNavOption.MyNotifications.name) {
            MyNotifications(userTheme, drawerState)
        }

        composable(MainNavOption.MyPhone.name) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                MyPhone(userTheme, drawerState)
            }
        }

        composable(MainNavOption.MyPhotosVideos.name) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MyPhotosVideos(userTheme, drawerState)
            }
        }

        composable(MainNavOption.MyPhysicalActivity.name) {
            MyPhysicalActivity(userTheme, drawerState)
        }

        composable(MainNavOption.MySms.name) {
            MySms(userTheme, drawerState)
        }

        composable(MainNavOption.MyBiometrics.name) {
            MyBiometrics(userTheme, drawerState)
        }

        composable(MainNavOption.SettingsScreen.name) {
            SettingsScreen(drawerState)
        }

        composable(MainNavOption.AboutScreen.name) {
            AboutScreen(drawerState)
        }
    }
}

enum class MainNavOption {
    MyHome,
    MyBodySensors,
    MyCalendar,
    MyCallLogs,
    MyCamera,
    MyContacts,
    MyLocation,
    MyMicrophone,
    MyMusicAudio,
    MyNearbyDevices,
    MyNotifications,
    MyPhone,
    MyPhotosVideos,
    MyPhysicalActivity,
    MySms,
    MyBiometrics,
    SettingsScreen,
    AboutScreen,
}