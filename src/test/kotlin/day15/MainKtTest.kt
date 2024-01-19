package day15

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainKtTest {

    @Test
    fun test1() {
        val s = parseLine("Sensor at x=2300471, y=2016823: closest beacon is at x=2687171, y=2822745")

        assertEquals(Sensor(2300471,2016823,2687171,2822745), s)

        assertEquals(2687171 - 2300471 +  2822745 - 2016823,s.distance)
    }

    @Test
    fun testRange() {
        val s = Sensor(8,7, 2, 10)


        assertEquals(3..13,s.atRow(3))
    }

    @Test
    fun testRange2() {
        val s = Sensor(8,7, 2, 10)


        assertEquals(8..8,s.atRow(-2))
    }
}
