package day18

import kotlin.math.absoluteValue

const val fileName = "input18.txt"

var magicNumber = 0L

fun main() {
    val readLines = java.io.File(fileName).readLines()

    val points = readLines.map { it.split(',').map { it.toInt() } }.map { Triple(it[0], it[1], it[2]) }

    var adiacent = 0

    for (i in points.indices) {
        val point = points[i]
        for (j in i + 1 until points.size) {
            if (isAdjacent(point, points[j]))
                adiacent += 2
        }
    }

    println(points.size * 6 - adiacent)

}

fun isAdjacent(p1: Triple<Int, Int, Int>, p2: Triple<Int, Int, Int>): Boolean {
    return (p1.first - p2.first).absoluteValue +(p1.second - p2.second).absoluteValue+(p1.third - p2.third).absoluteValue == 1
}

