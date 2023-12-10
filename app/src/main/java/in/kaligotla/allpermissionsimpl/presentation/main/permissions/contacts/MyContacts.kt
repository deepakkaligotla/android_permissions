package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.contacts

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import `in`.kaligotla.allpermissionsimpl.R
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.Contact
import `in`.kaligotla.allpermissionsimpl.navigation.Screen
import `in`.kaligotla.allpermissionsimpl.presentation.rememberUserTheme
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.components.appbar.AppBar
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MyContacts(
    userTheme: AppTheme,
    drawerState: DrawerState,
    viewModel: MyContactsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            "android.permission.READ_CONTACTS",
            "android.permission.WRITE_CONTACTS",
            "android.permission.GET_ACCOUNTS"
        )
    )
    var contactsLazyList by rememberSaveable { mutableStateOf(emptyList<Contact>()) }

    AllPermissionsImplTheme(appTheme = userTheme) {
        Scaffold(
            modifier = Modifier.testTag("myTableTag"),
            topBar = { AppBar(drawerState = drawerState) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.minus(100).dp)
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.padding(top = 5.dp)) {
                    Row {
                        Text(
                            text = "Contacts ${if(viewModel.contacts.collectAsStateWithLifecycle().value.isNotEmpty()) viewModel.contacts.collectAsStateWithLifecycle().value.size else 0}",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
                if (multiplePermissionsState.allPermissionsGranted) {
                    if (contactsLazyList.isEmpty()) {
                        contactsLazyList.toMutableList().clear()
                        viewModel.getAllContactsGroupsID(context)
                        contactsLazyList = viewModel.contacts.collectAsStateWithLifecycle().value
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(LocalConfiguration.current.screenWidthDp.minus(100).dp),
                            modifier = Modifier.padding(5.dp),
                            contentPadding = PaddingValues(0.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (contactsLazyList.isNotEmpty()) {
                                items(contactsLazyList.toMutableList()) { contact ->
                                    ElevatedCard(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.Gray,
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 6.dp
                                        ),
                                        modifier = Modifier.padding(3.dp),
                                        content = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(100.dp)
                                                    .padding(8.dp)
                                            ) {
                                                Spacer(modifier = Modifier.padding(5.dp))
                                                Box(
                                                    modifier = Modifier
                                                        .shadow(
                                                            elevation = 4.dp,
                                                            shape = CircleShape
                                                        )
                                                        .align(Alignment.CenterStart)
                                                ) {
                                                    Image(
                                                        painter = rememberImagePainter(
                                                            data  = Uri.parse(if(contact.contactPhoto!=null) contact.contactPhoto else "${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.resources.getResourcePackageName(R.drawable.ic_launcher_background)}/${context.getResources().getResourceTypeName(R.drawable.ic_launcher_background)}/${context.getResources().getResourceEntryName(R.drawable.ic_launcher_background)}")
                                                        ),
                                                        contentDescription = "123",
                                                        modifier = Modifier
                                                            .width(80.dp)
                                                            .height(80.dp)
                                                            .clip(CircleShape),
                                                        contentScale = ContentScale.Fit
                                                    )
                                                }

                                                Box(modifier = Modifier.align(Alignment.Center)) {
                                                    contact.contactName?.let {
                                                        Text(
                                                            text = it,
                                                            textAlign = TextAlign.Start,
                                                            softWrap = true,
                                                            modifier = Modifier.width(150.dp),
                                                            fontSize = 16.sp
                                                        )
                                                    }
                                                }

                                                Box(
                                                    modifier = Modifier
                                                        .shadow(
                                                            elevation = 4.dp,
                                                            shape = RoundedCornerShape(8.dp)
                                                        )
                                                        .align(Alignment.TopEnd)

                                                ) {
                                                    contact.homeNumber?.let {
                                                        Text(
                                                            text = "Home: $it",
                                                            textAlign = TextAlign.Center,
                                                            fontSize = 10.sp
                                                        )
                                                    }
                                                }
                                                Box(
                                                    modifier = Modifier
                                                        .shadow(
                                                            elevation = 4.dp,
                                                            shape = RoundedCornerShape(8.dp)
                                                        )
                                                        .align(Alignment.CenterEnd)

                                                ) {
                                                    contact.mobileNumber?.let {
                                                        Text(
                                                            text = "Mobile: $it",
                                                            textAlign = TextAlign.Center,
                                                            fontSize = 10.sp
                                                        )
                                                    }
                                                }
                                                Box(
                                                    modifier = Modifier
                                                        .shadow(
                                                            elevation = 4.dp,
                                                            shape = RoundedCornerShape(8.dp)
                                                        )
                                                        .align(Alignment.BottomEnd)

                                                ) {
                                                    contact.workNumber?.let {
                                                        Text(
                                                            text = "Work: $it",
                                                            textAlign = TextAlign.Center,
                                                            fontSize = 10.sp
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Button(onClick = {
                        multiplePermissionsState.launchMultiplePermissionRequest()
                    }) {
                        Text("Grant Contacts permission")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyContactsScreenPreview() {
    AllPermissionsImplTheme(appTheme = rememberUserTheme()) {
        Screen.MyContactsScreen
    }
}