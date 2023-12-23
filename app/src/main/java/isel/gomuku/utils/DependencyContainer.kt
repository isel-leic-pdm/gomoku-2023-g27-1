package isel.gomuku.utils

import isel.gomuku.services.http.game.GameService
import isel.gomuku.repository.user.UserRepository
import isel.gomuku.services.http.statistics.StatsServiceHttp

interface DependencyContainer {
    val statsService : StatsServiceHttp
    val gameService : GameService
    val userStorage : UserRepository
}