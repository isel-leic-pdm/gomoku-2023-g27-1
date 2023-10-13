package isel.gomuku

import isel.gomuku.gameLogic.Player
import isel.gomuku.gameLogic.Position
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
            repeat(100) {
                board.put(it.toPosition(), Player.BLACK)
            }
            repeat(10){col ->
                repeat(10){
                    val pos = Position.invoke(it,col)
                    require( board.get(pos) != null)
                }
            }
    }
}