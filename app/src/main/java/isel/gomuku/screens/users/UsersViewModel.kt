package isel.gomuku.screens.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import isel.gomuku.localRepository.LoggedUser
import isel.gomuku.screens.component.BaseViewModel

class UsersViewModel : BaseViewModel() {
    var user : LoggedUser? by mutableStateOf(null)
    var inputEmail by mutableStateOf("")


}