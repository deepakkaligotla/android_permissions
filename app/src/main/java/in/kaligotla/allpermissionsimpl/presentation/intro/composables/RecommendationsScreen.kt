package `in`.kaligotla.allpermissionsimpl.presentation.intro.composables

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.kaligotla.allpermissionsimpl.R
import `in`.kaligotla.allpermissionsimpl.presentation.NavRoutes
import `in`.kaligotla.allpermissionsimpl.presentation.intro.IntroViewModel
import `in`.kaligotla.allpermissionsimpl.ui.previews.AllScreenPreview

@Composable
fun RecommendationScreen(
    navController: NavController,
    viewModel: IntroViewModel = hiltViewModel()
) = IntroCompose(
    navController = navController,
    text = "Recommendation",
    buttonText = R.string.start_app
) {
    viewModel.saveUserOnboarding(true)
    navController.navigate(NavRoutes.MainRoute.name) {
        popUpTo(NavRoutes.IntroRoute.name)
    }
}

@AllScreenPreview
@Composable
fun RecommendationScreenPreview() {
    val navController = rememberNavController()
    RecommendationScreen(navController = navController)
}