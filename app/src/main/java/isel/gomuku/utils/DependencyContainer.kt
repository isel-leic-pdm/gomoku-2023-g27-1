package isel.gomuku.utils

import isel.gomuku.services.GameService
import isel.gomuku.services.StatsService
import isel.gomuku.services.UserService

interface DependencyContainer {
    val statsService : StatsService
    val gameService : GameService
    val userService : UserService
}