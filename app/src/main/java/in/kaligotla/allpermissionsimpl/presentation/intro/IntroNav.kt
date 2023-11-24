package `in`.kaligotla.allpermissionsimpl.presentation.intro

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import `in`.kaligotla.allpermissionsimpl.presentation.NavRoutes
import `in`.kaligotla.allpermissionsimpl.presentation.intro.composables.MotivationScreen
import `in`.kaligotla.allpermissionsimpl.presentation.intro.composables.RecommendationScreen
import `in`.kaligotla.allpermissionsimpl.presentation.intro.composables.WelcomeScreen

fun NavGraphBuilder.introGraph(navController: NavController) {
    navigation(
        startDestination = IntroNavOption.WelcomeScreen.name,
        route = NavRoutes.IntroRoute.name
    ) {
        composable(IntroNavOption.WelcomeScreen.name) {
            WelcomeScreen(navController)
        }
        composable(IntroNavOption.MotivationScreen.name) {
            MotivationScreen(navController)
        }
        composable(IntroNavOption.RecommendationScreen.name) {
            RecommendationScreen(navController)
        }
    }
}

enum class IntroNavOption {
    WelcomeScreen,
    MotivationScreen,
    RecommendationScreen
}
