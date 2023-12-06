package `in`.kaligotla.allpermissionsimpl.presentation.main.permissions.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.EventItem
import `in`.kaligotla.allpermissionsimpl.presentation.main.mainCommon.getDate
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun MyCalendarGroup(key: String, eventlazyList: Map<Int, List<EventItem>>) {
    Box(
        modifier= Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(LocalConfiguration.current.screenWidthDp / 120),
            modifier = Modifier.padding(5.dp),
            contentPadding = PaddingValues(0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ) {
            if(eventlazyList.isNotEmpty()) {
                if(eventlazyList.containsKey(key.toInt())) {
                    items(eventlazyList[key.toInt()]!!.toMutableList()) {idEvent ->
                        ElevatedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            modifier = Modifier
                                .size(width = 100.dp, height = 120.dp)
                                .padding(1.dp),
                            content = {
                                Column(
                                    modifier = Modifier
                                        .padding(1.dp)
                                        .fillMaxWidth()
                                        .wrapContentSize(Alignment.Center)
                                        .testTag("Key"),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = getDate(idEvent.dtStart),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = SimpleDateFormat("EEEE").format(Date(idEvent.dtStart!!)),
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.padding(2.dp))
                                    Text(text = idEvent.title!!, fontSize = 12.sp, textAlign = TextAlign.Center)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}