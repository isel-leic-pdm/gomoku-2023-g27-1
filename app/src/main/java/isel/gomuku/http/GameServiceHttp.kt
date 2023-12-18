package isel.gomuku.http

import com.google.gson.Gson
import isel.gomuku.gameLogic.Player
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.RequestBody

interface GameService {
    suspend fun play(line: Int, column: Int)
    suspend fun startGame(gridSize: Int, variants: String, openingRules: String)
    suspend fun quitGame()
}

const val TOKEN = "KEY"

class GameServiceHttp(private val client: OkHttpClient, private val gson: Gson, private val baseApiUrl: String) : GameService {

    var lobbyId: Int? = null // Guardar em memoria local

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

    private fun requestBody(params: List<Pair<String, String>>): RequestBody {
        var body = FormBody.Builder()
        params.forEach { param -> body = body.add(param.first, param.second) }
        return body.build()
    }

    val httpRequests = HttpRequest(client)

    override suspend fun play(line: Int, column: Int) {
        val params: List<Pair<String, String>> =
            listOf(Pair("lin", line.toString()), Pair("col", column.toString()))
        val request = httpRequests.post(playUrl(), requestBody(params))
        httpRequests.doRequest(request) {
            val dto = gson.fromJson(it.body?.string(), Player::class.java)
        }
    }


    override suspend fun startGame(gridSize: Int, variants: String, openingRules: String) {
        val params: List<Pair<String, String>> = listOf(
            Pair("grid", gridSize.toString()),
            Pair("openingRule", openingRules), Pair("variant", variants)
        )
        val request = httpRequests.post(startGameURL(), requestBody(params))

       httpRequests.doRequest(request) {
            val dto = gson.fromJson(it.body?.string(), Int::class.java)
            lobbyId = dto
        }
    }

    override suspend fun quitGame() {
        val request = httpRequests.post(quitUrl(), requestBody(emptyList()))
        httpRequests.doRequest(request) {
            return@doRequest gson.fromJson<String?>(it.body?.string(), String::class.java)
        }
    }

}

