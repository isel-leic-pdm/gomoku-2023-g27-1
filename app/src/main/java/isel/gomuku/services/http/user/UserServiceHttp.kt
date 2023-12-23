package isel.gomuku.services.http.user

import com.google.gson.Gson
import okhttp3.OkHttpClient

class UserServiceHttp(
    private val client: OkHttpClient,
    private val gson: Gson,
    private val baseApiUrl: String
)  {

}