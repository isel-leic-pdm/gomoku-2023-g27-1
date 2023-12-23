package isel.gomuku.services.http.game.httpModel

class PlayMade(val lobbyId: Int)

class PlayMadeOutput(val gameOpened: PlayMade, val opponent: UserInfo?)