package `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import `in`.kaligotla.allpermissionsimpl.navigation.MainNavOption
import `in`.kaligotla.allpermissionsimpl.presentation.NavRoutes

@Composable
fun MainBottomBar(onboardState: Boolean, navController: NavHostController) {
    if (onboardState) {
        BottomAppBar(
            containerColor = Color.Black,
            contentColor = Color.White,
            contentPadding = PaddingValues()
        ) {
            IconButton(
                onClick = {
                    navController.navigate(MainNavOption.MyHome.name) {
                        popUpTo(NavRoutes.MainRoute.name)
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
            }

            IconButton(
                onClick = {
                    navController.navigate(MainNavOption.SettingsScreen.name) {
                        popUpTo(NavRoutes.MainRoute.name)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile"
                )
            }

            IconButton(
                onClick = {
                    navController.navigate(MainNavOption.AboutScreen.name) {
                        popUpTo(NavRoutes.MainRoute.name)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "About"
                )
            }
        }
    }
}