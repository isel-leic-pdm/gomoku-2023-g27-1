package isel.gomuku.http

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

import okhttp3.Response

interface GameService {
    suspend fun play(line: Int, column: Int)
    suspend fun startGame(gridSize: Int, variants: String, openingRules: String)
    suspend fun quitGame()
}
class GameServiceHttp(private val client: OkHttpClient, private val gson: Gson) : GameService {

    var lobbyId: Int? = null

    companion object {
        //por vals para parameters
        const val urlExtension = "/game"
        private const val ApiKeyParameter = "api_key" //Igual ao do prof, mas nao devia ir em header?

    }

    private val request: Request by lazy {
        Request.Builder()
            .url("https://localhost.com/")
            .addHeader("accept", "application/json")
            .build()
    }


    override suspend fun play(line: Int, column: Int) {
        TODO()
    }//doRequest(baseUrl().addQueryParameter()) {}

    override suspend fun startGame(gridSize: Int, variants: String, openingRules: String) {
        TODO()
    }

    override suspend fun quitGame() {

    }
}



