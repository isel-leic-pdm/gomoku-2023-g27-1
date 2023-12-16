package isel.gomuku.http

import com.google.gson.Gson
import isel.gomuku.gameLogic.Player
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.RequestBody

interface GameService {
    suspend fun play(line: Int, column: Int, auth: String)
    suspend fun startGame(gridSize: Int, variants: String, openingRules: String, auth: String)
    suspend fun quitGame(auth: String)
    suspend fun getGameState(auth: String)
    suspend fun getPlayerActiveGame(auth: String)
    suspend fun getGameInfo(auth: String)
}

class GameServiceHttp(
    private val client: OkHttpClient,
    private val gson: Gson,
    private val baseApiUrl: String
) : GameService {

    private var lobbyId: Int? = null // Guardar em memoria local

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
            val dto = gson.fromJson(it.body?.string(), Player::class.java)
        }
    }


    override suspend fun startGame(
        gridSize: Int,
        variants: String,
        openingRules: String,
        auth: String
    ) {
        val params: List<Pair<String, String>> = listOf(
            Pair("grid", gridSize.toString()),
            Pair("openingRule", openingRules), Pair("variant", variants)
        )
        val request = httpRequests.post(
            startGameURL(), requestBody(params),
            hashMapOf("accept" to "application/json", "Authorization" to auth)
        )

        httpRequests.doRequest(request) {
            //Recebe um gameStatusOutputModel
            //WaitingOpponentPiecesOutput,   AwaitingOpponentOutput
            val dto = gson.fromJson(it.body?.string(), Int::class.java)
            lobbyId = dto
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
            return@doRequest gson.fromJson<String?>(it.body?.string(), String::class.java)
        }
    }

    override suspend fun getGameState(auth: String) {
        val request = httpRequests.get(
            gameState(),
            hashMapOf("accept" to "application/json", "Authorization" to auth)
        )
        httpRequests.doRequest(request) {
            //Recebe um gameStatusOutputModel
            //AwaitingOpponentOutput, GameEndedOutput

        }
    }

    //Checks if player is in a game that has not ended
    //Returns an id if player is in a game
    override suspend fun getPlayerActiveGame(auth: String) {
        val request = httpRequests.get(
            activeGame(),
            hashMapOf("accept" to "application/json", "Authorization" to auth)
        )
        httpRequests.doRequest(request) {
            //Recebe um gameStatusOutputModel
            //GameDataOutput
        }
    }

    override suspend fun getGameInfo(auth: String) {
        TODO("Not yet implemented")
    }


}
