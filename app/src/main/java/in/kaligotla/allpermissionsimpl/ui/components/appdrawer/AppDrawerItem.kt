package `in`.kaligotla.allpermissionsimpl.ui.components.appdrawer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import `in`.kaligotla.allpermissionsimpl.core.DrawerParams
import `in`.kaligotla.allpermissionsimpl.navigation.MainNavOption
import `in`.kaligotla.allpermissionsimpl.proto.AppTheme
import `in`.kaligotla.allpermissionsimpl.ui.theme.AllPermissionsImplTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppDrawerItem(item: AppDrawerItemInfo<T>, onClick: (options: T) -> Unit) =
    Surface(
        modifier = Modifier
            .width(180.dp)
            .padding(bottom = 5.dp, top = 5.dp)
            .testTag("AppDrawer"),
        onClick = { onClick(item.drawerOption) },
        shape = RoundedCornerShape(50),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(5.dp)
        ) {
            Icon(
                painter = painterResource(id = item.drawableId),
                contentDescription = stringResource(id = item.descriptionId),
                modifier = Modifier
                    .size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = item.title),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
        }
    }


class MainStateProvider : PreviewParameterProvider<AppDrawerItemInfo<MainNavOption>> {
    override val values = sequence {
        DrawerParams.drawerButtons.forEach { element ->
            yield(element)
        }
    }
}

@Preview
@Composable
fun AppDrawerItemPreview(@PreviewParameter(MainStateProvider::class) state: AppDrawerItemInfo<MainNavOption>) {
    AllPermissionsImplTheme(appTheme = AppTheme.Default) {
        AppDrawerItem(item = state, onClick = {})
    }
}