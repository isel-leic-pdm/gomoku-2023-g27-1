package isel.gomuku.screens.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import isel.gomuku.repository.user.model.LoggedUser
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.services.UserService

class UsersViewModel(private val userService: UserService) : BaseViewModel() {
    var user : LoggedUser? by mutableStateOf(userService.getUser())

    var inputName by mutableStateOf ("")
    var inputEmail by mutableStateOf("")
    var inputPassword by mutableStateOf("")

    fun login(){
        safeCall {
            if (user != null) throw IllegalStateException("User already logged")//Should not be possible
            //call login services that return LoggedUser
            val userName = inputName
            val password = inputPassword
            val newUser : LoggedUser = userService.login(userName,password) //TODO:Deal with the error
            /*LoggedUser(
            1,
            inputName,
            inputEmail,
            "secureToken"
        )*/
            user = newUser/*
            //Set in flow memory
            userService.saveUser(newUser)*/
        }
    }
    fun logout(){
        safeCall {
            userService.logout()
            user = null
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