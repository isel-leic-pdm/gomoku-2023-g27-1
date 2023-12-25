package isel.gomuku.screens.users

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import isel.gomuku.repository.user.model.LoggedUser
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.services.UserService

class UsersViewModel(private val userService: UserService) : BaseViewModel() {
    var user : LoggedUser? by mutableStateOf(null)

    var inputName by mutableStateOf ("")
    var inputEmail by mutableStateOf("")
    var inputPassword by mutableStateOf("")

    init {
        safeCall {
            user = userService.getUser()
        }
    }

    fun login(){
        safeCall {
            if (user != null) throw IllegalStateException("User already logged")//Should not be possible
            val userName = inputName
            val password = inputPassword
            val newUser : LoggedUser = userService.login(userName,password) //TODO:Deal with the error
            user = newUser
        }
    }
    fun logout(){
        safeCall {
            user = null
            userService.logout()
        }
    }
    fun register(){
        safeCall {
            if (user != null) throw IllegalStateException("Need to logout first")
            val userName = inputName
            val email = inputEmail
            val password = inputPassword
            val newUser : LoggedUser = userService.register(userName,email,password)
            user = newUser
        }
    }
}
