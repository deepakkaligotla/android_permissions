package `in`.kaligotla.allpermissionsimpl.presentation.intro.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.kaligotla.allpermissionsimpl.presentation.intro.IntroNavOption
import `in`.kaligotla.allpermissionsimpl.ui.previews.AllScreenPreview
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme

@Composable
fun WelcomeScreen(navController: NavController = rememberNavController()) = IntroCompose(
    navController = navController,
    text = "Welcome",
    showBackButton = false
) {
    navController.navigate(IntroNavOption.MotivationScreen.name)
}


@AllScreenPreview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}

