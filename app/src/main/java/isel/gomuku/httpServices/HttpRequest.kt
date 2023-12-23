package isel.gomuku.httpServices

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class HttpRequest (private val client: OkHttpClient){

    fun get(url: HttpUrl.Builder, headers: Map<String, String> = emptyMap()) : Request {
        val request = Request.Builder()
            .url(url.build())
            .get()
        headers.forEach { (key, value) ->
            request.addHeader(key, value)
        }
        return request.build()
    }
    fun post(url: HttpUrl.Builder, body: RequestBody, headers: Map<String, String> = emptyMap()) : Request {
        val request = Request.Builder()
            .url(url.build())
            .post(body)
        headers.forEach { (key, value) ->
            request.addHeader(key, value)
        }
        return request.build()
    }
    suspend fun <T> doRequest(
        request: Request,
        validateBody: Boolean = true,
        callback: (Response) -> T
    ): T {

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
                    if (validateBody && (!response.isSuccessful || response.body == null))
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