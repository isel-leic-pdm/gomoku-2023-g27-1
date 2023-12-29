package isel.gomuku.screens.users

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import isel.gomuku.repository.user.model.LoggedUser
import isel.gomuku.screens.component.BaseViewModel
import isel.gomuku.screens.gameScreeens.remoteGame.RedirectException
import isel.gomuku.services.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class UsersViewModel(private val userService: UserService) : BaseViewModel() {
    var user: LoggedUser? by mutableStateOf(null)
    var isRegistering by mutableStateOf(false)

    var inputName by mutableStateOf("")
    var inputEmail by mutableStateOf("")
    var inputPassword by mutableStateOf("")

    init {
        safeCall {
            val tempUser: LoggedUser? = userService.getUser()
            withContext(Dispatchers.Main) {
                user = tempUser
            }

        }
    }
    private suspend fun setUser(newUser:LoggedUser?){
        withContext(Dispatchers.Main){
            user = newUser
        }
    }

    fun login() {
        if (user != null) throw IllegalStateException("User already logged")//Should not be allowed
        safeCall {
            val userName = inputName
            val password = inputPassword
            val newUser: LoggedUser =
                userService.login(userName, password) //TODO:Deal with the error

            setUser(newUser)
        }
    }

    fun logout() {
        safeCall {
            setUser(null)
            userService.logout()
        }
    }

    fun register() {
        safeCall {
            if (user != null) throw IllegalStateException("Need to logout first")
            val userName = inputName
            val email = inputEmail
            val password = inputPassword
            val newUser: LoggedUser = userService.register(userName, email, password)
            setUser(newUser)
        }
    }

    fun changeForm() {
        isRegistering = !isRegistering
        inputEmail = ""
    }

    fun showWarning(warning: RedirectException,clearExternalWarning: ()->Unit) {
        error = warning.message
        clearExternalWarning()
    }

    fun presentExternalError(getExternalWarning: () -> RedirectException?) {
        error = getExternalWarning()?.message
    }
}
