package isel.gomuku.services.http.game

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import isel.gomuku.services.GameService
import isel.gomuku.services.http.HttpRequest
import isel.gomuku.services.http.Problem
import isel.gomuku.services.http.RemoteSourceException
import isel.gomuku.services.http.game.httpModel.AwaitingOpponent
import isel.gomuku.services.http.game.httpModel.GameDetails
import isel.gomuku.services.http.game.httpModel.GameEnded
import isel.gomuku.services.http.game.httpModel.GameRunning
import isel.gomuku.services.http.game.httpModel.GameStatus
import isel.gomuku.services.http.game.httpModel.Move
import isel.gomuku.services.http.game.httpModel.PlayMade
import isel.gomuku.services.http.requestBody.GameOptionsBody
import isel.gomuku.services.http.requestBody.PlayBody
import isel.gomuku.services.local.gameLogic.Player
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class GameServiceHttp(
    private val requestBuilder: HttpRequest,
    private val gson: Gson,
    private val baseApiUrl: String
) : GameService {

    private var lobbyId: Int? = null // Guardado em remote game onde for chamada

    //Pode ser usado para polling
    private val gameUrl = {
        baseApiUrl.toHttpUrl()
            .newBuilder()
            .addPathSegment("game")
    }
    private val startGameURL = {
        gameUrl()
            .addPathSegment("start")
    }
    private val playUrl = {
        gameUrl()
            .addPathSegment("play")
            .addPathSegment("$lobbyId")
    }
    private val quitUrl = {
        gameUrl()
            .addPathSegment("quit")
            .addPathSegment("$lobbyId")
    }


    private val gameState = {
        gameUrl()
            .addPathSegment("$lobbyId")
            .addPathSegment("state")
    }
    private val gameInfo = {
        gameUrl()
            .addPathSegment("$lobbyId")
    }
    private val activeGame = {
        gameUrl()
            .addPathSegment("active-match")
    }

    private suspend fun remoteRequest(request: Request, action:((Response) -> Unit)? = null){
        try {
            requestBuilder.doRequest(request){
                if (action != null) {
                    action(it)
                }
            }
        }catch (jsonException:JsonSyntaxException){
            throw IllegalStateException("Fatal error:" + jsonException.message)
        }catch (remote: RemoteSourceException){
            val prob = gson.fromJson(remote.body?.string(), Problem::class.java)
            if (remote.status == 401){
                TODO("Decide what do do when unauthorized")
            }
            if (prob.type != "basic" && prob.title == "Already in a game"){
                TODO("Fetch active game id and give options")
            }
            throw Exception(if (prob.detail != null)prob.title +":" + prob.detail else prob.title)
        }
    }

    fun setLobbyId(lobby: Int) {
        lobbyId = lobby
    }

    override suspend fun play(line: Int, column: Int, auth: String): GameStateReturn? {
        val play = PlayBody(line,column)
        val body = gson.toJson(play).toRequestBody("application/json".toMediaType())
        val request = requestBuilder.post(
            playUrl(),
            body,
            mapOf("Authorization" to "Bearer $auth")
        )
        var gameState : GameStateReturn? = null
        remoteRequest(request){
            val dto = gson.fromJson(it.body?.string(),ApiGameDetails::class.java)
            Log.d("Test",dto.toString())
        }
        return gameState

    }


    override suspend fun startGame(
        gridSize: Int,
        variants: String,
        openingRules: String,
        auth: String
    ): GameStatus {
        val gameOptions = GameOptionsBody(gridSize,openingRules,variants)
        val body = gson.toJson(gameOptions).toRequestBody("application/json".toMediaType())
        val request = requestBuilder.post(
            startGameURL(),
            body,
            mapOf("Authorization" to "Bearer $auth")
        )
        var gameState : GameStatus? = null
        Log.d("Test","Starting game")
        remoteRequest(request){
            val dto = gson.fromJson(it.body?.string(),ApiGameDetails::class.java)
            if (dto.awaitingOpponent != null){
                gameState = dto.awaitingOpponent
            }else{
                throw IllegalStateException("Unexpected server response")
            }
            Log.d("Test",dto.toString())
        }
        requireNotNull(gameState)
        return gameState as GameStatus

    }

    override suspend fun quitGame(auth: String) {
        TODO()
        /*val request = httpRequests.post(
            quitUrl(), requestBody(emptyList()),
            hashMapOf("accept" to "application/json", "Authorization" to auth)
        )
        httpRequests.doRequest(request) {
            //Recebe um gameStatusOutputModel
            //LobbyClosedOutput,    GameEndedOutput
        }*/
    }

    override suspend fun getGameState(auth: String): GameStateReturn? {
        TODO()
        /*val request = httpRequests.get(
            gameState(),
            hashMapOf("accept" to "application/json", "Authorization" to auth)
        )
        return httpRequests.doRequest(request) {
            try {
                val dto = gson.fromJson(it.body?.string(), GameRunningOutput::class.java)
                return@doRequest GameStateReturn(
                    dto.gameOpened.moves,
                    false,
                    null,
                    false
                )
            } catch (_: JsonSyntaxException) {
            }

            return@doRequest null

        }*/
    }


}

data class ApiGameDetails(
    val gameDetails : GameDetails,
    val awaitingOpponent: AwaitingOpponent,
    val gameRunning: GameRunning,
    val playMade: PlayMade,
    val gameEnded: GameEnded,
)

class GameStateReturn(
    val moves: List<Move>,
    val inOpponentOpening: Boolean,
    val winner: Int?,
    val hasGameEnded: Boolean
)
class GameStart(val id: Int, val player: Player)