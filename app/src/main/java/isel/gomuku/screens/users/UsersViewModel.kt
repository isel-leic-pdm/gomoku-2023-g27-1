package isel.gomuku.screens.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import isel.gomuku.localRepository.LoggedUser
import isel.gomuku.localRepository.UserRepository
import isel.gomuku.screens.component.BaseViewModel

class UsersViewModel(private val userStorage: UserRepository) : BaseViewModel() {
    var user : LoggedUser? by mutableStateOf(userStorage.getUser())

    var inputName by mutableStateOf ("")
    var inputEmail by mutableStateOf("")
    var inputPassword by mutableStateOf("")

    fun login(){
        if (user != null) throw IllegalStateException("User already logged")//Should not be possible
        //call login services that return LoggedUser
        val newUser = LoggedUser(
            1,
            inputName,
            inputEmail,
            "secureToken"
        )
        user = newUser
        //Set in flow memory
        userStorage.setUser(newUser)

    }

}