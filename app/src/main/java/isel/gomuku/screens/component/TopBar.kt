package isel.gomuku.screens.component


import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import isel.gomuku.R

data class NavigationHandlers(
    val onBackHandler: (() -> Unit)? = null,
    val navigateToHandler: (() -> Unit)? = null,
    val navigateToLogin : (() -> Unit)? = null,
    val logout : (() -> Unit)? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    @StringRes title: Int = R.string.app_name,
    navigationHandlers: NavigationHandlers = NavigationHandlers()
) {
    TopAppBar(
        title = { Text(stringResource(id = title)) },
        navigationIcon = {
            ConditionalIconButton(
                action = navigationHandlers.onBackHandler,
                image = Icons.Default.ArrowBack,
                string = R.string.top_bar_go_back
            )
        },
        actions = {
            ConditionalIconButton(
                action =  navigationHandlers.navigateToLogin ,
                image = Icons.Default.AccountCircle,
                string = R.string.top_bar_user

            )
            ConditionalIconButton(
                action = navigationHandlers.logout,
                image = Icons.Default.ExitToApp,
                string = R.string.top_bar_user_logout
            )
            ConditionalIconButton(
                action = navigationHandlers.navigateToHandler,
                image = Icons.Default.Info,
                string = R.string.top_bar_navigate
            )
        }

    )
}


@Composable
fun ConditionalIconButton(
    action: (() -> Unit)?,
    image: ImageVector,
    @StringRes string: Int,
    modifier: Modifier = Modifier
) {
    if (action != null) {
        IconButton(
            onClick = action,
            modifier = modifier
        ) {
            Icon(
                imageVector = image,
                contentDescription = stringResource(id = string)
            )
        }
    }
}