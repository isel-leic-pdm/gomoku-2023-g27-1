package isel.gomuku.http.gameServiceHttpModels

class PlayMade(val lobbyId: Int)

class PlayMadeOutput(val gameOpened:PlayMade,val opponent:UserInfo?)