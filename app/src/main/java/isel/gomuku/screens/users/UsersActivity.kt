package isel.gomuku.screens.users

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
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

    override val viewModel: UsersViewModel by viewModels()

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
                        DrawUserAuth(
                            modifier = Modifier.padding(vertical = pad.calculateTopPadding()),
                                nickname = "",
                                email = "",
                                password = "",
                                editName = {},
                                editPassword = {},
                                editEmail = {}
                            )

                    }
                }
            }
        }
    }
}

