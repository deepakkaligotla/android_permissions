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
import `in`.kaligotla.allpermissionsimpl.presentation.settings.SettingsScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.mainGraph(navController: NavHostController, drawerState: DrawerState) {
    navigation(startDestination = MainNavOption.MyHome.name, route = NavRoutes.MainRoute.name) {
        composable(MainNavOption.MyHome.name) {
            MyHome(drawerState)
        }

        composable(MainNavOption.MyBodySensors.name) {
            MyBodySensors(drawerState)
        }

        composable(MainNavOption.MyCalendar.name) {
            MyCalendar(drawerState)
        }

        composable(MainNavOption.MyCallLogs.name) {
            MyCallLogs(drawerState = drawerState)
        }

        composable(MainNavOption.MyCamera.name) {

        }

        composable(MainNavOption.MyContacts.name) {

        }

        composable(MainNavOption.MyLocation.name) {

        }

        composable(MainNavOption.MyMicrophone.name) {

        }

        composable(MainNavOption.MyMusicAudio.name) {

        }

        composable(MainNavOption.MyNearbyDevices.name) {

        }

        composable(MainNavOption.MyNotifications.name) {

        }

        composable(MainNavOption.MyPhone.name) {

        }

        composable(MainNavOption.MyPhotosVideos.name) {

        }

        composable(MainNavOption.MyPhysicalActivity.name) {

        }

        composable(MainNavOption.MySms.name) {

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