package isel.gomuku.gameLogic

enum class Player(val symbol: Char) {
    BLACK('B'), WHITE('W');
    fun turn() = if(this == WHITE) BLACK else WHITE
}

fun String.toPlayer(): Player {
    require(this.length == 1) {"Illegal symbol with more than a single char."}
    return when(this[0]){
        Player.BLACK.symbol -> Player.BLACK
        Player.WHITE.symbol -> Player.WHITE
        else -> throw NoSuchElementException("This symbol does not correspond to any player")
    }
}