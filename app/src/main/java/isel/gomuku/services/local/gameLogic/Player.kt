package isel.gomuku.services.local.gameLogic

enum class Player {
    BLACK, WHITE;
    fun turn() = if(this == WHITE) BLACK else WHITE
}

