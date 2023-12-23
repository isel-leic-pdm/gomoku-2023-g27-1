package isel.gomuku.httpServices

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import isel.gomuku.gameLogic.Player
import isel.gomuku.http.gameServiceHttpModels.AwaitingOpponent
import isel.gomuku.http.gameServiceHttpModels.GameEnded
import isel.gomuku.http.gameServiceHttpModels.WaitingOpponentPiecesOutput
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.RequestBody

interface GameService {
    suspend fun play(line: Int, column: Int, auth: String)
    suspend fun startGame(gridSize: Int, variants: String, openingRules: String, auth: String): Int
    suspend fun quitGame(auth: String)
    suspend fun getGameState(auth: String)
}

class GameServiceHttp(
    private val client: OkHttpClient,
    private val gson: Gson,
    private val baseApiUrl: String
) : GameService {

    private var lobbyId: Int? = null // Guardado em remote game onde for chamada

    //Pode ser usado para polling
    val gameUrl = {
        baseApiUrl.toHttpUrl()
            .newBuilder()
            .addPathSegment("game")
    }
    val startGameURL = {
        gameUrl()
            .addPathSegment("start")
    }
    val playUrl = {
        gameUrl()
            .addPathSegment("play")
            .addPathSegment("$lobbyId")
    }
    val quitUrl = {
        gameUrl()
            .addPathSegment("quit")
            .addPathSegment("$lobbyId")
    }


    val gameState = {
        gameUrl()
            .addPathSegment("$lobbyId")
            .addPathSegment("state")
    }
    val gameInfo = {
        gameUrl()
            .addPathSegment("$lobbyId")
    }
    val activeGame = {
        gameUrl()
            .addPathSegment("active-match")
    }

    private fun requestBody(params: List<Pair<String, String>>): RequestBody {
        var body = FormBody.Builder()
        params.forEach { param -> body = body.add(param.first, param.second) }
        return body.build()
    }

    private val httpRequests = HttpRequest(client)

    /** Recebe
    class GameDataOutput(val game: GameStatus): GameStatusOutputModel()
    class GameRunningOutput(val gameRunning: GameStatus,val opponent:UserInfo?): GameStatusOutputModel()
    class GameOpenedOutput(val gameOpened:GameStatus,val opponent:UserInfo?): GameStatusOutputModel()
    class AwaitingOpponentOutput(val awaitingOpponent: GameStatus): GameStatusOutputModel()
    class WaitingOpponentPiecesOutput(val waitingOpponentPieces: GameStatus,val opponent:UserInfo?): GameStatusOutputModel()
    class GameEndedOutput(val gameEnded: GameStatus,val opponent:UserInfo?): GameStatusOutputModel()
    class PlayMadeOutput(val playMade: GameStatus): GameStatusOutputModel()
    class LobbyClosedOutput(val lobbyClosed: GameStatus): GameStatusOutputModel()
     */

    /**
     AwaitingOpponent -> AwaitingOpponentOutput(status)
     Game -> GameDataOutput(status)
     GameEnded -> GameEndedOutput(status,userSevice.getUserInfoById(status.opponentId))
     GameOpened -> GameOpenedOutput(status,userSevice.getUserInfoById(status.opponentId))
     GameRunning -> GameRunningOutput(status,userSevice.getUserInfoById(status.opponentId))
     LobbyClosed -> LobbyClosedOutput(status)
     PlayMade -> PlayMadeOutput(status)
     WaitingOpponentPieces -> WaitingOpponentPiecesOutput(status,userSevice.getUserInfoById(status.opponentId))
     */

    fun setLobbyId(lobby: Int){lobbyId = lobby}

    override suspend fun play(line: Int, column: Int, auth: String) {
        val params: List<Pair<String, String>> =
            listOf(Pair("lin", line.toString()), Pair("col", column.toString()))
        val request = httpRequests.post(
            playUrl(), requestBody(params),
            hashMapOf("accept" to "application/json", "Authorization" to auth)
        )
        httpRequests.doRequest(request) {
            //Recebe um gameStatusOutputModel
            //AwaitingOpponentOutput,   GameEndedOutput, PlayMadeOutput
            try{
                val dto = gson.fromJson(it.body?.string(), GameEnded::class.java)
                if (dto.winner == null) throw Exception("This game ended in a draw")
                throw Exception("You have won this game")
            }catch (_ : JsonSyntaxException){}
        }
    }


    /** Make startGame return lobby and player type*/
    override suspend fun startGame(
        gridSize: Int,
        variants: String,
        openingRules: String,
        auth: String
    ): Int {
        val params: List<Pair<String, String>> = listOf(
            Pair("grid", gridSize.toString()),
            Pair("openingRule", openingRules), Pair("variant", variants)
        )
        val request = httpRequests.post(
            startGameURL(), requestBody(params),
            hashMapOf("accept" to "application/json", "Authorization" to auth)
        )

        return httpRequests.doRequest(request) {
            //Recebe um gameStatusOutputModel
            //WaitingOpponentPiecesOutput,   AwaitingOpponentOutput
            try {
                val dto = gson.fromJson(it.body?.string(), AwaitingOpponent::class.java)
                lobbyId = dto.lobbyId
                return@doRequest dto.lobbyId
            }catch (_: Exception){}

            val dto = gson.fromJson(it.body?.string(), WaitingOpponentPiecesOutput::class.java)
            lobbyId = dto.gameOpened.lobbyId

            return@doRequest dto.gameOpened.lobbyId
        }
    }

    override suspend fun quitGame(auth: String) {
        val request = httpRequests.post(
            quitUrl(), requestBody(emptyList()),
            hashMapOf("accept" to "application/json", "Authorization" to auth)
        )
        httpRequests.doRequest(request) {
            //Recebe um gameStatusOutputModel
            //LobbyClosedOutput,    GameEndedOutput
        }
    }

    override suspend fun getGameState(auth: String) {
        val request = httpRequests.get(
            gameState(),
            hashMapOf("accept" to "application/json", "Authorization" to auth)
        )
        httpRequests.doRequest(request) {
            //Recebe um gameStatusOutputModel
            //AwaitingOpponentOutput, GameEndedOutput,
            //GameRunningOutput, GameOpenedOutput
            //WaitingOpponentPieces

            try {
                val dto = gson.fromJson(it.body?.string(), GameEnded::class.java)
                if (dto.winner == null)
                    throw Exception("This game ended in a draw")
                throw Exception("You have lost this game")
            }catch (_ : JsonSyntaxException){}
        }
    }


}

