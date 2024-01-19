package day14

import kotlin.math.max
import kotlin.math.min

const val fileName = "input14.txt"
fun main() {

    var minX = Int.MAX_VALUE
    var maxX = Int.MIN_VALUE
    var minY = 0
    var maxY = Int.MIN_VALUE

    val readLines = java.io.File(fileName).readLines()
    readLines.forEach {
        it.split(" -> ").forEach { pos->
            var a = pos.split(',')
            minX = min(minX, a[0].toInt())
            maxX = max(maxX, a[0].toInt())
            minY = min(minY, a[1].toInt())
            maxY = max(maxY, a[1].toInt())
        }
    }
    val result = Array(maxX - minX + 1) { Array(maxY - minY + 1) { "." } }

    readLines.forEach {
        val points = it.split(" -> ").map {
            it.split(',')[0].toInt() to it.split(',')[1].toInt()
        }
        points.forEachIndexed{idx,p ->
            val next = points.getOrNull(idx+1)
            if (p.first == next?.first) {
                val min = min(p.second, next.second)
                val max = max(p.second, next.second)
                for (i in min..max) {
                    result[p.first-minX][i-minY] = "#"
                }
            } else if (p.second == next?.second) {
                val min = min(p.first, next.first)
                val max = max(p.first, next.first)
                for (i in min..max) {
                    result[i-minX][p.second-minY] = "#"
                }
            } else if (next != null) {
                throw Exception()
            }
        }
    }

    var step = part1(maxY, result, minX, minY)

    println(step)


    minX = 200
    maxX = maxX + 200
    val result2 = Array(maxX - minX + 1) { Array(maxY - minY + 1 + 2) { "." } }

    readLines.forEach {
        val points = it.split(" -> ").map {
            it.split(',')[0].toInt() to it.split(',')[1].toInt()
        }
        points.forEachIndexed{idx,p ->
            val next = points.getOrNull(idx+1)
            if (p.first == next?.first) {
                val min = min(p.second, next.second)
                val max = max(p.second, next.second)
                for (i in min..max) {
                    result2[p.first-minX][i-minY] = "#"
                }
            } else if (p.second == next?.second) {
                val min = min(p.first, next.first)
                val max = max(p.first, next.first)
                for (i in min..max) {
                    result2[i-minX][p.second-minY] = "#"
                }
            } else if (next != null) {
                throw Exception()
            }
        }
    }

    result2.forEach {
        it[it.size - 1] = "#"
    }

    printResult(result2, minY)

    var step2 = part2(maxY, result2, minX, minY)

    println(step2)
}

private fun part1(
    maxY: Int,
    result: Array<Array<String>>,
    minX: Int,
    minY: Int
): Int {
    var run = true
    var step = 0
    while (run) {
        var sand = 500 to 0
        var down = true

        while (down) {
            if (sand.second == maxY) {
                down = false
                run = false
            } else if (result.get(sand.first - minX).get(sand.second + 1 - minY) == ".") {
                sand = sand.first to sand.second + 1
            } else if (result.get(sand.first - 1 - minX).get(sand.second + 1 - minY) == ".") {
                sand = sand.first - 1 to sand.second + 1
            } else if (result.get(sand.first + 1 - minX).get(sand.second + 1 - minY) == ".") {
                sand = sand.first + 1 to sand.second + 1
            } else {
                result[sand.first - minX][sand.second - minY] = "o"
                down = false
                step++
            }

        }
    }
    return step
}

private fun part2(
    maxY: Int,
    result: Array<Array<String>>,
    minX: Int,
    minY: Int
): Int {
    var run = true
    var step = 0
    while (run) {
        var sand = 500 to 0
        var down = true

        while (down) {
            if (result.get(sand.first - minX).get(sand.second + 1 - minY) == ".") {
                sand = sand.first to sand.second + 1
            } else if (result.get(sand.first - 1 - minX).get(sand.second + 1 - minY) == ".") {
                sand = sand.first - 1 to sand.second + 1
            } else if (result.get(sand.first + 1 - minX).get(sand.second + 1 - minY) == ".") {
                sand = sand.first + 1 to sand.second + 1
            } else {
                result[sand.first - minX][sand.second - minY] = "o"
                down = false
                step++
                if (sand == 500 to 0) run = false
            }

        }
    }
    return step
}

fun printResult(result: Array<Array<String>>, minY:Int) {
    for (k in 1..10) println()
    for (i in result[0].indices) {
        for (j in result.indices) {
            print(result[j][i])
        }
        print(i+minY)
        println()
    }
}


