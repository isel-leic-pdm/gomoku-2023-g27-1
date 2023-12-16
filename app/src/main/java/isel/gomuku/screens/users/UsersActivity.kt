package isel.gomuku.screens.users

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import isel.gomuku.MainActivity
import isel.gomuku.screens.component.BaseComponentActivity
import isel.gomuku.ui.theme.GomukuTheme

class UsersActivity() : BaseComponentActivity<UsersViewModel>() {

    companion object{
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
            GomukuTheme{

                Box{
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text(text = "Login", fontSize = 25.sp)
                        OutlinedTextField(
                            value = viewModel.inputEmail,
                            onValueChange = { viewModel.inputEmail = it },
                            label = { Text("Email") }
                        )
                    }
                }
            }
        }
    }
}