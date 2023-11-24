package isel.gomuku.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import isel.gomuku.gameLogic.Player
import isel.gomuku.services.HttpService
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface GameService {
    suspend fun play(line: Int, column: Int)
    suspend fun startGame(gridSize: Int, variants: String, openingRules: String)
    suspend fun quitGame()
}

const val KEY = "KEY"

class GameServiceHttp(private val client: OkHttpClient, private val gson: Gson) : GameService {

    var lobbyId: Int? = null

    companion object {
        //const val urlExtension = "game/"
    }

    //Pode ser usado para polling
    private val request: (String) -> Request = { extension ->
        Request.Builder()
            .url("https://localhost.com/game/$extension")
            .addHeader("accept", "application/json")
            .addHeader("Authorization", KEY)
            .build()
    }

    private val postRequest: (String, RequestBody) -> Request = { extension, body ->
        Request.Builder()
            .url("https://localhost.com/game/$extension")
            .addHeader("accept", "application/json")
            .addHeader("Authorization", KEY)
            .post(body)
            .build()
    }
    private fun requestBody(params: List<Pair<String, String>>): RequestBody {
        var body = FormBody.Builder()
        params.forEach { param -> body = body.add(param.first, param.second) }
        return body.build()
    }

    override suspend fun play(line: Int, column: Int) {
        val params: List<Pair<String, String>> =
            listOf(Pair("lin", line.toString()), Pair("col", column.toString()))
        doRequest("play/$lobbyId", body = requestBody(params)) {
        }
    }


    override suspend fun startGame(gridSize: Int, variants: String, openingRules: String) {
        val params: List<Pair<String, String>> = listOf(
            Pair("grid", gridSize.toString()),
            Pair("openingRule", openingRules), Pair("variant", variants)
        )

        doRequest("start", body = requestBody(params)) {
            val dto = gson.fromJson(it.body?.string(), Player::class.java)
        }
    }

    override suspend fun quitGame() {
        doRequest("quit", body = requestBody(emptyList())) {}
    }

    private suspend fun <T> doRequest(
        urlExtension: String,
        validateBody: Boolean = true,
        body: RequestBody? = null,
        callback: (Response) -> T
    ): T {
        val request = if (body == null) this.request(urlExtension)
        else this.postRequest(urlExtension, body)
        val httpCall = client.newCall(request);

        return suspendCancellableCoroutine {
            it.invokeOnCancellation {
                httpCall.cancel()
            }

            httpCall.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (validateBody && (response.isSuccessful == false || response.body == null))
                        it.resumeWithException(Exception(response.message))

                    try {
                        it.resume(callback(response))
                    } catch (e: Exception) {
                        it.resumeWithException(e)
                    }

                }
            })
        }
    }
}


/*
package isel.gomuku.services

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

import okhttp3.Response

abstract class GomokuService() {

    companion object {
        const val apiKeyParameter = "api_key"
        const val BaseApiUrl = "" //Acrescentar
    }


    private val httpClient =
        OkHttpClient.Builder().build()



    suspend fun <T> doRequest(
        url: HttpUrl.Builder,
        validateBody: Boolean = true,
        callback: (Response) -> T
    ): T {
        val request = Request
            .Builder()
            .url(url.build())
            .build()

        val httpCall = httpClient.newCall(request);

        return suspendCancellableCoroutine {
            it.invokeOnCancellation {
                httpCall.cancel()
            }

            httpCall.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (validateBody && (response.isSuccessful == false || response.body == null))
                        it.resumeWithException(Exception(response.message))

                    try {
                        it.resume(callback(response))
                    } catch (e: Exception) {
                        it.resumeWithException(e)
                    }

                }
            })
        }
    }
}
 */