package day9

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainKtTest {

    @Test
    fun test1() {
        val res = calcNextTail(0 to 0, 0 to 0 )

        assertEquals(0 to 0, res)
    }

    @Test
    fun test2() {
        val res = calcNextTail(1 to 0, 0 to 0 )

        assertEquals(0 to 0, res)
    }

    @Test
    fun test3() {
        val res = calcNextTail(0 to 0, 0 to 0 )

        assertEquals(0 to 0, res)
    }

    @Test
    fun test4() {
        val res = calcNextTail(2 to 0, 0 to 0 )

        assertEquals(1 to 0, res)
    }

    @Test
    fun test5() {
        val res = calcNextTail(4 to 2, 3 to 0 )

        assertEquals(4 to 1, res)
    }

    @Test
    fun test6() {
        val res = calcNextTail(2 to 4, 4 to 3 )

        assertEquals(3 to 4, res)
    }
}
