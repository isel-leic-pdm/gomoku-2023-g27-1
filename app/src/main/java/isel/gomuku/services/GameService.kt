package isel.gomuku.services

import android.provider.CloudMediaProviderContract.AlbumColumns
import isel.gomuku.gameLogic.Position
import okhttp3.HttpUrl.Companion.toHttpUrl

class GameService(private val apiKey: String) : HttpService() {

    var lobbyId: Int? = null

    companion object {
        //por vals para parameters
        const val urlExtension = "/game"
        private const val ApiKeyParameter = "api_key" //Igual ao do prof, mas nao devia ir em header?

    }

    val baseUrl = {
        (baseApiUrl + urlExtension).toHttpUrl()
            .newBuilder()
            .addQueryParameter(ApiKeyParameter, apiKey)
    }

    suspend fun play(line: Int, column: Int) {}//doRequest(baseUrl().addQueryParameter()) {}

    fun startGame(gridSize: Int, variants: String, openingRules: String) {
        TODO()
    }

    fun quitGame() {

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