package isel.gomuku.screens.users

import android.content.Intent
import android.os.Bundle
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
import isel.gomuku.screens.users.component.DrawUserAuth
import isel.gomuku.screens.users.component.DrawUserDetails
import isel.gomuku.ui.theme.GomukuTheme



class UsersActivity() : BaseComponentActivity<UsersViewModel>() {

    companion object {
        fun navigate(source: ComponentActivity) {
            val intent = Intent(source, UsersActivity::class.java)
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

    private var isRegistering by mutableStateOf(false)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                                email = if (isRegistering)viewModel.inputEmail else null ,
                                password = viewModel.inputPassword,
                                editName = {viewModel.inputName = it},
                                editPassword = {viewModel.inputPassword = it},
                                editEmail = {viewModel.inputEmail = it},
                                changeForm = {
                                    isRegistering = !isRegistering
                                    viewModel.inputEmail = ""
                                },
                                if (isRegistering) viewModel::register else viewModel::login

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

