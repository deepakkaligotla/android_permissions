package `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import `in`.kaligotla.allpermissionsimpl.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(onboardState: Boolean, drawerState: DrawerState) {
    if (onboardState) {
        val contextForToast = LocalContext.current.applicationContext
        val coroutineScope = rememberCoroutineScope()
        var isTopBarTitleCollapsed by rememberSaveable { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            delay(2000)
            isTopBarTitleCollapsed = true
            delay(2000)
            isTopBarTitleCollapsed = false
        }

        CenterAlignedTopAppBar(
            title = {
                Row(
                    modifier = Modifier.padding(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = { isTopBarTitleCollapsed = !isTopBarTitleCollapsed }
                    ) {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.app_logo),
                            contentDescription = null
                        )
                    }
                    AnimatedVisibility(
                        visible = !isTopBarTitleCollapsed
                    ) {
                        Text(
                            text = "Deepak Kaligotla",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
            },
            actions = {
//                IconButton(onClick = {
//                    Toast.makeText(contextForToast, "Add Clicked", Toast.LENGTH_SHORT)
//                        .show()
//                }) {
//                    Icon(
//                        imageVector = Icons.Default.Add,
//                        contentDescription = "Add Items"
//                    )
//                }
                IconButton(
                    onClick = {
                        Toast.makeText(
                            contextForToast,
                            "Search Clicked",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Black.copy(alpha = 0.3f)
            )
        )
    }
}