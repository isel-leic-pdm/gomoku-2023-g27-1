package isel.gomuku.screens.users

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import isel.gomuku.screens.utils.viewModelInit
import isel.gomuku.screens.component.BaseComponentActivity
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.screens.gameScreeens.GameOptions
import isel.gomuku.screens.gameScreeens.remoteGame.RedirectException
import isel.gomuku.screens.gameScreeens.remoteGame.RemoteGameActivity
import isel.gomuku.screens.users.component.DrawUserAuth
import isel.gomuku.screens.users.component.DrawUserDetails
import isel.gomuku.ui.theme.GomukuTheme
import java.lang.Exception


class UsersActivity() : BaseComponentActivity<UsersViewModel>() {

    companion object {
        private const val EXTERNAL_WARNING = "EXTERNAL_WARNING"
        fun navigate(source: ComponentActivity,exception: Exception? = null) {
            val intent = Intent(source, UsersActivity::class.java)
            if (exception != null){
                intent.putExtra(EXTERNAL_WARNING, exception)
            }
            source.startActivity(intent)
        }
    }

    override val viewModel: UsersViewModel by viewModels {
        viewModelInit {
            UsersViewModel(
                dependencyContainer.userService
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Test","Created")
        viewModel.presentExternalError{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                this.intent.getParcelableExtra(EXTERNAL_WARNING, RedirectException::class.java)
            else
                this.intent.getParcelableExtra<RedirectException>(EXTERNAL_WARNING)
        }

        safeSetContent {
            GomukuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = {
                        TopBar(
                            navigationHandlers = NavigationHandlers(
                                onBackHandler = { finish() },
                                logout = if (viewModel.user != null) viewModel::logout else null
                            )
                        )
                    }) { pad ->
                        val user = viewModel.user
                        val modifier = Modifier.padding(vertical = pad.calculateTopPadding())
                        if (user == null){
                            DrawUserAuth(
                                modifier = modifier,
                                nickname = viewModel.inputName,
                                email = if (viewModel.isRegistering)viewModel.inputEmail else null ,
                                password = viewModel.inputPassword,
                                editName = {viewModel.inputName = it},
                                editPassword = {viewModel.inputPassword = it},
                                editEmail = {viewModel.inputEmail = it},
                                changeForm = viewModel::changeForm,
                                if (viewModel.isRegistering) viewModel::register else viewModel::login

                            )
                        }else{
                            DrawUserDetails(modifier = modifier, user = user)
                        }
                    }
                }
            }
        }
    }
}

