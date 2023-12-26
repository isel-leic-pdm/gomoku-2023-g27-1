package isel.gomuku

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import isel.gomuku.repository.user.model.LoggedUser
import isel.gomuku.services.http.HttpRequest
import isel.gomuku.services.http.requestBody.LoginBody
import isel.gomuku.services.http.user.model.UserAuthorization
import isel.gomuku.services.local.gameLogic.Player
import isel.gomuku.services.local.gameLogic.Position
import isel.gomuku.services.local.gameLogic.toPosition
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun create_board_grid(){
            val board = mutableMapOf<Position, Player>()
            repeat(361) {
                board.put(it.toPosition(), Player.BLACK)
            }
            repeat(19){col ->
                repeat(19){
                    val pos = Position.invoke(it,col)
                    require( board.get(pos) != null)
                }
            }
    }

    @Test
    fun remote_request(){
        val gson = Gson()

        runBlocking {
            val request = HttpRequest(OkHttpClient())
            val testR = request.post(
                "http://localhost:8080/api/user/login".toHttpUrl().newBuilder(),
                gson.toJson(LoginBody("a","b")).toRequestBody("application/json".toMediaType())
            )

            request.doRequest(testR){
                try {
                    val a = it.body
                    val dto = gson.fromJson(it.body?.string(), UserAuthorization::class.java)
                    println(LoggedUser(dto.id,dto.nickname,dto.tokenInfo.tokenValue))
                }catch (e: JsonSyntaxException){
                    throw IllegalStateException("Fatal error:" + e.message)
                }
            }
        }

    }
}