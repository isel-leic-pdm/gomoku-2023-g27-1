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

    private var needsRegistering by mutableStateOf(false)

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
                                onBackHandler = { finish() })
                        )
                    }) { pad ->
                        val user = viewModel.user
                        if (user == null){
                            DrawUserAuth(
                                modifier = Modifier.padding(vertical = pad.calculateTopPadding()),
                                nickname = viewModel.inputName,
                                email = if (needsRegistering)viewModel.inputEmail else null,
                                password = viewModel.inputPassword,
                                editName = {viewModel.inputName = it},
                                editPassword = {viewModel.inputPassword = it},
                                editEmail = {viewModel.inputEmail = it},
                                changeForm = {
                                    needsRegistering = !needsRegistering
                                    viewModel.inputEmail = ""
                                },
                                if (!needsRegistering) viewModel::login else viewModel::login

                            )
                        }else{
                            Text(user.nome, modifier = Modifier.padding(vertical = pad.calculateTopPadding()))
                        }
                    }
                }
            }
        }
    }
}

