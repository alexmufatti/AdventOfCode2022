package day13

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MainKtTest {

    @Test
    fun parsePart1() {
        val value = "[10,1]"
        val res = parsePart(value)
        assertEquals(PartList( listOf(PartInt( 10), PartInt(1))),res)
    }

    @Test
    fun parsePart2() {
        val value = "[10]"
        val res = parsePart(value)
        assertEquals(PartList( listOf(PartInt( 10))),res)
    }

    @Test
    fun parsePart3() {
        val value = "[10,[1,2]]"
        val res = parsePart(value)
        assertEquals(PartList( listOf(PartInt( 10), PartList(listOf(PartInt(1), PartInt(2))))),res)
    }

    @Test
    fun parsePart4() {
        val value = "[[],[1,2]]"
        val res = parsePart(value)
        assertEquals(PartList(listOf(PartList( listOf()), PartList(listOf(PartInt(1), PartInt(2))))),res)
    }
}
