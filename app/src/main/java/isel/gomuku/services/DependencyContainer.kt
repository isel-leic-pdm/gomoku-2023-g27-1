package isel.gomuku.services

import isel.gomuku.http.GameService
import isel.gomuku.localRepository.UserRepository

interface DependencyContainer {
    val statsService : StatsService
    val gameService : GameService
    val userStorage : UserRepository
}