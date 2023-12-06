package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.photosVideos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.OpenInFull
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPhotoScreenComponent(
    photoExtensionsList: List<String>,
    photoLazyList: Map<String, List<File>>,
    showItem: MutableState<Boolean>
) {
    val photoExtensionPagerState = rememberPagerState(pageCount = { photoExtensionsList.size })
    val photoFilesPagerState = rememberPagerState(pageCount = { photoLazyList.size })
    var selectedItem: File? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.padding(top = 5.dp)) {
            Row {
                Text(
                    text = "Photos (${photoLazyList.size})",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }

        VerticalPager(
            modifier = Modifier
                .height(LocalConfiguration.current.screenHeightDp.minus(240).dp)
                .fillMaxWidth(),
            state = photoExtensionPagerState,
            contentPadding = PaddingValues(1.dp),
            pageSize = PageSize.Fixed(200.dp),
            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                photoFilesPagerState,
                Orientation.Horizontal
            )
        ) { index ->
            ElevatedCard(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp)
                    .height(180.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Gray
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${photoExtensionsList[index]} files (${if (photoLazyList[photoExtensionsList[index]].isNullOrEmpty()) 0 else photoLazyList[photoExtensionsList[index]]!!.size})",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(2.dp),
                        textAlign = TextAlign.Justify,
                        color = Color.Green
                    )
                    if (photoLazyList[photoExtensionsList[index]].isNullOrEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No ${photoExtensionsList[index]} files found",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(2.dp),
                                textAlign = TextAlign.Justify,
                                color = Color.Red
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LazyHorizontalGrid(
                                rows = GridCells.FixedSize(120.dp),
                                contentPadding = PaddingValues(0.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (photoLazyList.isNotEmpty()) {
                                    if (photoLazyList.containsKey(photoExtensionsList[index])) {
                                        items(photoLazyList[photoExtensionsList[index]]!!.toMutableList()) { item ->
                                            ElevatedCard(
                                                colors = CardDefaults.cardColors(
                                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                                ),
                                                elevation = CardDefaults.cardElevation(
                                                    defaultElevation = 6.dp
                                                ),
                                                modifier = Modifier
                                                    .size(
                                                        width = 120.dp,
                                                        height = 120.dp
                                                    )
                                                    .padding(2.dp)
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .padding(1.dp)
                                                        .fillMaxWidth()
                                                        .wrapContentSize(
                                                            Alignment.Center
                                                        )
                                                        .testTag("Key"),
                                                    verticalArrangement = Arrangement.Center,
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .padding(8.dp)
                                                    ) {
                                                        Box(
                                                            modifier = Modifier
                                                                .shadow(
                                                                    elevation = 4.dp,
                                                                )
                                                                .align(Alignment.TopStart)
                                                                .selectable(
                                                                    selected = selectedItem == item,
                                                                    onClick = {
                                                                        selectedItem =
                                                                            item
                                                                        showItem.value =
                                                                            true
                                                                    }
                                                                )
                                                        ) {
                                                            AsyncImage(
                                                                model = item.path,
                                                                contentDescription = "",
                                                                modifier = Modifier.fillMaxSize()
                                                            )

                                                            Box(
                                                                modifier = Modifier
                                                                    .shadow(
                                                                        elevation = 4.dp,
                                                                        shape = CircleShape
                                                                    )
                                                                    .align(
                                                                        Alignment.BottomEnd
                                                                    )
                                                                    .clip(
                                                                        CircleShape
                                                                    )
                                                            ) {
                                                                if (selectedItem == item && showItem.value) {
                                                                    MyImageDialogue(
                                                                        selectedItem = selectedItem!!
                                                                    ) {
                                                                        showItem.value = false
                                                                    }
                                                                } else {
                                                                    Icon(
                                                                        imageVector = Icons.Outlined.OpenInFull,
                                                                        contentDescription = "Expand",
                                                                        tint = Color.White
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}