package isel.gomuku.services.http.game

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import isel.gomuku.services.GameService
import isel.gomuku.services.http.HttpRequest
import isel.gomuku.services.http.game.httpModel.AwaitingOpponent
import isel.gomuku.services.http.game.httpModel.AwaitingOpponentOutput
import isel.gomuku.services.http.game.httpModel.GameEnded
import isel.gomuku.services.http.game.httpModel.GameEndedOutput
import isel.gomuku.services.http.game.httpModel.GameOpenedOutput
import isel.gomuku.services.http.game.httpModel.GameRunningOutput
import isel.gomuku.services.http.game.httpModel.Move
import isel.gomuku.services.http.game.httpModel.UserInfo
import isel.gomuku.services.http.game.httpModel.WaitingOpponentPiecesOutput
import isel.gomuku.services.local.gameLogic.Player
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.RequestBody

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

    fun setLobbyId(lobby: Int) {
        lobbyId = lobby
    }

    override suspend fun play(line: Int, column: Int, auth: String): GameStateReturn? {
        val params: List<Pair<String, String>> =
            listOf(Pair("lin", line.toString()), Pair("col", column.toString()))
        val request = httpRequests.post(
            playUrl(), requestBody(params),
            hashMapOf("accept" to "application/json", "Authorization" to auth)
        )
        return httpRequests.doRequest(request) {
            //Recebe um gameStatusOutputModel
            //AwaitingOpponentOutput,   GameEndedOutput, PlayMadeOutput
            try {
                val dto = gson.fromJson(it.body?.string(), GameEndedOutput::class.java)
                return@doRequest GameStateReturn(dto.gameEnded.moves, false, dto.gameEnded.winner, true)
            } catch (_: JsonSyntaxException) {
            }
            return@doRequest null
        }
    }


    override suspend fun startGame(
        gridSize: Int,
        variants: String,
        openingRules: String,
        auth: String
    ): GameStart {
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
                val dto = gson.fromJson(it.body?.string(), AwaitingOpponentOutput::class.java)
                return@doRequest GameStart(dto.awaitingOpponent.lobbyId, Player.BLACK)
            } catch (_: Exception) {
            }

            val dto = gson.fromJson(it.body?.string(), WaitingOpponentPiecesOutput::class.java)

            return@doRequest GameStart(dto.gameOpened.lobbyId, Player.WHITE)
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

    override suspend fun getGameState(auth: String): GameStateReturn? {
        val request = httpRequests.get(
            gameState(),
            hashMapOf("accept" to "application/json", "Authorization" to auth)
        )
        return httpRequests.doRequest(request) {
            //AwaitingOpponentOutput, GameEndedOutput,
            //GameRunningOutput, GameOpenedOutput
            //WaitingOpponentPieces

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

            try {
                val dto = gson.fromJson(it.body?.string(), GameOpenedOutput::class.java)
                return@doRequest GameStateReturn(
                    dto.gameOpened.moves,
                    false,
                    null,
                    false
                )
            } catch (_: JsonSyntaxException) {
            }

            try {
                val dto = gson.fromJson(it.body?.string(), WaitingOpponentPiecesOutput::class.java)
                return@doRequest GameStateReturn(
                    dto.gameOpened.moves,
                    true,
                    null,
                    false
                )
            } catch (_: JsonSyntaxException) {
            }

            try {
                val dto = gson.fromJson(it.body?.string(), GameEndedOutput::class.java)
                return@doRequest GameStateReturn(
                    dto.gameEnded.moves,
                    false,
                    dto.gameEnded.winner,
                    true
                )
            } catch (_: JsonSyntaxException) {
            }

            return@doRequest null

        }
    }


}

class GameStateReturn(
    val moves: List<Move>,
    val inOpponentOpening: Boolean,
    val winner: Int?,
    val hasGameEnded: Boolean
)
class GameStart(val id: Int, val player: Player)