package isel.gomuku

import isel.gomuku.services.local.gameLogic.Player
import isel.gomuku.services.local.gameLogic.Position
import isel.gomuku.services.local.gameLogic.toPosition
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
}