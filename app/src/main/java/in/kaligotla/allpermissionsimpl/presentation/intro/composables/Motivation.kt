package `in`.kaligotla.allpermissionsimpl.presentation.intro.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.kaligotla.allpermissionsimpl.presentation.intro.IntroNavOption
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.previews.AllScreenPreview
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme


@Composable
fun MotivationScreen(navController: NavController) = IntroCompose(
    navController = navController,
    text = "Motivation"
) {
    navController.navigate(IntroNavOption.RecommendationScreen.name)
}

@AllScreenPreview
@Composable
fun MotivationPrivacyPreview() {
    val navController = rememberNavController()
    AllPermissionsImplTheme(appTheme = AppTheme.Default) {
        MotivationScreen(navController = navController)
    }
}

