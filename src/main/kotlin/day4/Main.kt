package day4

import java.io.File

const val fileName = "input4.txt"

fun main() {
    val readLines = File(fileName).readLines()

   val res = readLines.map {
       val assignement = it.split(',')
       if (fullyContains(assignement[0], assignement[1]) || fullyContains(assignement[1], assignement[0])) 1 else 0
   }.sum()

    println(res)

    val res2 = readLines.map {
        val assignement = it.split(',')
        if (overlaps(assignement[0], assignement[1]) || overlaps(assignement[1], assignement[0])) 1 else 0
    }.sum()

    println(res2)
}

fun fullyContains(s: String, s1: String): Boolean {
    val r1 = s.split('-').map { it.toInt() }
    val r2 = s1.split('-').map { it.toInt() }

    return r2[0] in (r1[0]..r1[1]) && r2[1] in (r1[0]..r1[1])
}

fun overlaps(s: String, s1: String): Boolean {
    val r1 = s.split('-').map { it.toInt() }
    val r2 = s1.split('-').map { it.toInt() }

    return r2[0] in (r1[0]..r1[1]) || r2[1] in (r1[0]..r1[1])
}
