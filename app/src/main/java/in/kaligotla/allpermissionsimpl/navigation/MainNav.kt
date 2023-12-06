package `in`.kaligotla.allpermissionsimpl.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
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
            MyLocation(userTheme, drawerState)
        }

        composable(MainNavOption.MyMicrophone.name) {
            MyMicrophone(userTheme, drawerState)
        }

        composable(MainNavOption.MyMusicAudio.name) {
            MyMusicAudio(userTheme, drawerState)
        }

        composable(MainNavOption.MyNearbyDevices.name) {
            MyNearbyDevices(userTheme, drawerState)
        }

        composable(MainNavOption.MyNotifications.name) {
            MyNotifications(userTheme, drawerState)
        }

        composable(MainNavOption.MyPhone.name) {
            MyPhone(userTheme, drawerState)
        }

        composable(MainNavOption.MyPhotosVideos.name) {
            MyPhotosVideos(userTheme, drawerState)
        }

        composable(MainNavOption.MyPhysicalActivity.name) {
            MyPhysicalActivity(userTheme, drawerState)
        }

        composable(MainNavOption.MySms.name) {
            MySms(userTheme, drawerState)
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
    SettingsScreen,
    AboutScreen,
}