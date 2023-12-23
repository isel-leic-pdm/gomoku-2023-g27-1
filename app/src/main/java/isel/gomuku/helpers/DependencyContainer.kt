package isel.gomuku.helpers

import isel.gomuku.httpServices.GameService
import isel.gomuku.localRepository.UserRepository
import isel.gomuku.services.StatsService

interface DependencyContainer {
    val statsService : StatsService
    val gameService : GameService
    val userStorage : UserRepository
}