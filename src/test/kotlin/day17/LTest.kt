package day17

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LTest {

    @Test
    fun testLEmpty() {

        val chamber = Array(7) { Array(10) { "." } }


        (0..3).forEach {
            assertTrue(L().canMoveRight(chamber,it to 2))
        }

        assertFalse(L().canMoveRight(chamber,4 to 2))

        (1..4).forEach {
            assertTrue(L().canMoveLeft(chamber,it to 2))
        }

        assertFalse(L().canMoveLeft(chamber,0 to 2))

    }

    @Test
    fun testLFull() {

        val chamber = Array(7) { Array(10) { "." } }
        (0..9).forEach{
            chamber[0][it] = "#"
            chamber[6][it] = "#"
        }

        chamber[0][3] = "."

        (0..2).forEach {
            assertTrue(L().canMoveRight(chamber,it to 2))
        }

        assertFalse(L().canMoveRight(chamber,3 to 2))

        (2..4).forEach {
            assertTrue(L().canMoveLeft(chamber,it to 2))
        }

        assertFalse(L().canMoveLeft(chamber,1 to 2))

        assertTrue(L().canMoveLeft(chamber,1 to 3))
        assertFalse(L().canMoveLeft(chamber,0 to 3))

    }

    // 0123456
    // .......
    // .......
}
