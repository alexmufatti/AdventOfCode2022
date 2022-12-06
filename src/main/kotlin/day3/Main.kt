package day3

import java.io.File

const val fileName = "input3.txt"

fun main() {
    val readLines = File(fileName).readLines()

    val res = readLines.map { rucksack ->
            val first = rucksack.subSequence(0, rucksack.length / 2)
            val second = rucksack.subSequence(rucksack.length / 2, rucksack.length)
            val char = first.find { item -> second.contains(item) }
            if (char!!.isUpperCase()) char.code - (65 - 27) else char.code- (97 -1)
    }.sum()

    println(res)


    var res2 =0
    for (i in readLines.indices step 3) {
        val first = readLines[i]
        val second = readLines[i+1]
        val third = readLines[i+2]
        val char = first.find { item -> second.contains(item) && third.contains(item) }
        res2 += if (char!!.isUpperCase()) char.code - (65 - 27) else char.code- (97 -1)
    }

    println(res2)
}
