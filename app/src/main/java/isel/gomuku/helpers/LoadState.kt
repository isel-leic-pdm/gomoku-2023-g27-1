package isel.gomuku.helpers

import isel.gomuku.services.dto.GlobalStatistics

sealed class LoadState<out T>

object Idle : LoadState<Nothing>()
object Loading : LoadState<Nothing>()
data class Loaded<T>(val result: Result<T>) : LoadState<T>()
