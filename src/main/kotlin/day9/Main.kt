package day9

import kotlin.math.abs
import kotlin.math.sign

const val fileName = "input9.txt"

fun main() {

    val bridge = mutableSetOf<Pair<Int, Int>>()
    val rope = arrayOf(0 to 0, 0 to 0, 0 to 0, 0 to 0, 0 to 0, 0 to 0, 0 to 0, 0 to 0, 0 to 0, 0 to 0)
    val readLines = java.io.File(fileName).readLines()
    readLines.forEach {move ->
        val direction = move.split(' ')[0]
        val steps = move.split(' ')[1].toInt()

        bridge.add(0 to 0)
        for (i in 0 until  steps)
        {
            if (direction == "U") rope[0] = rope[0].first to rope[0].second + 1
            if (direction == "D") rope[0] = rope[0].first to rope[0].second - 1
            if (direction == "L") rope[0] = rope[0].first - 1 to rope[0].second
            if (direction == "R") rope[0] = rope[0].first + 1 to rope[0].second

            for (j in 0 .. rope.size - 2)
                rope[j + 1] = calcNextTail(rope[j], rope[j+1])

            bridge.add(rope.last())
        }
    }

    println(bridge.size)
}

fun calcNextTail(currentHead:Pair<Int, Int>, currentTail:Pair<Int, Int>): Pair<Int, Int> {
    if (abs(currentTail.first - currentHead.first) <=1 && abs(currentTail.second - currentHead.second) <=1) return currentTail

    if (currentTail.first == currentHead.first) return currentTail.first to currentTail.second + (1* sign((currentHead.second - currentTail.second).toDouble())).toInt()

    if (currentTail.second == currentHead.second) return currentTail.first + (1* sign((currentHead.first - currentTail.first).toDouble())).toInt() to currentTail.second

    return currentTail.first + (1* sign((currentHead.first - currentTail.first).toDouble())).toInt() to currentTail.second + (1* sign((currentHead.second - currentTail.second).toDouble())).toInt()
}
