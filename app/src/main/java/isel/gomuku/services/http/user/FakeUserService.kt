package isel.gomuku.services.http.user

import isel.gomuku.repository.user.UserRepository
import isel.gomuku.repository.user.model.LoggedUser
import isel.gomuku.services.UserService
import kotlinx.coroutines.delay

class FakeUserService(private val userRepository: UserRepository) : UserService {

    override suspend fun getUser(): LoggedUser? {
        delay(2000)
        return userRepository.getUser()
    }

    override suspend fun login(userName: String, password: String): LoggedUser {
        delay(2000)
        val user = LoggedUser(0,userName, "token")
        userRepository.setUser(user)
        return user
    }

    override suspend fun register(userName: String, email: String, password: String): LoggedUser {
        delay(2000)
        val user = LoggedUser(0,userName, "token")
        userRepository.setUser(user)
        return user
    }

    override suspend fun logout() {
        delay(2000)
       userRepository.deleteUser()
    }
}