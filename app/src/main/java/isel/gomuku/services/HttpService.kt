package isel.gomuku.services

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

open class HttpService {

    companion object{
    const val baseApiUrl = ""

    }

    private val httpClient by lazy {
        OkHttpClient.Builder().build()
    }
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