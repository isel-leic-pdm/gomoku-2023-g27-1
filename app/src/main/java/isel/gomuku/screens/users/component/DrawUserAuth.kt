package isel.gomuku.screens.users.component

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import isel.gomuku.R
import isel.gomuku.screens.component.NavigationHandlers
import isel.gomuku.screens.component.TopBar
import isel.gomuku.ui.theme.GomukuTheme


const val FONT_SIZE = 25
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DrawUserAuth(
    modifier: Modifier,
    nickname: String,
    email: String?,
    password: String,
    editName:(String) -> Unit,
    editPassword:(String) -> Unit,
    editEmail: (String) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = stringResource(id = R.string.user_request_login), fontSize =  FONT_SIZE.sp)
            }
            Text(text = stringResource(id = R.string.user_request_nickname), fontSize =  FONT_SIZE.sp)
            OutlinedTextField(
                value = nickname,
                onValueChange = { editName(it) },
                label = { Text(stringResource(id = R.string.user_request_nickname)) }
            )
            if (email != null) {
                Text(text = stringResource(id = R.string.user_request_email), fontSize =  FONT_SIZE.sp)
                OutlinedTextField(
                    value = email,
                    onValueChange = { editEmail(it) },
                    label = { Text(stringResource(id = R.string.user_request_email)) }
                )
            }
            Text(text = stringResource(id = R.string.user_request_password), fontSize =  FONT_SIZE.sp)
            OutlinedTextField(
                value = password,
                onValueChange = { editPassword(it) },
                label = {
                    Text(
                        text = stringResource(id = R.string.user_request_password)
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(modifier = modifier,onClick = { /*TODO*/ }) {
                    Text(text = stringResource(id = R.string.user_request_login), fontSize =  FONT_SIZE.sp)
                }
            }
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Preview() {
    var nickname by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf<String?>("null")
    }
    GomukuTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(topBar = {
                TopBar(
                    navigationHandlers = NavigationHandlers(
                        onBackHandler = { })
                )
            }, modifier = Modifier.fillMaxSize()) { pad ->
                val modifier = Modifier.padding(vertical = pad.calculateTopPadding())
                DrawUserAuth(
                    modifier,
                    nickname,
                    email,
                    password,
                    {nickname = it},
                    {password = it},
                    {email = it})

            }

        }
    }
}