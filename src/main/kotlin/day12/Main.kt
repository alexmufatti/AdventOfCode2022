package day12

import kotlin.math.min

const val fileName = "input12.txt"
var start = 0 to 0
var end = 0 to 0
var field = Array(61) { Array(41) { 0 } }
var min = Array(61) { Array(41) { Int.MAX_VALUE } }

var minSteps = Int.MAX_VALUE
var curSteps = 0
fun main() {
    val readLines = java.io.File(fileName).readLines()


    clean(readLines)

    calcNext(end, Int.MAX_VALUE)

    readLines.forEachIndexed { y, it ->
        it.forEachIndexed { x, char ->
            curSteps = 0
            min = Array(61) { Array(41) { Int.MAX_VALUE } }
            if (char == 'a') {
                clean(readLines)
                start = x to y
                calcNext(end, Int.MAX_VALUE)
                println("""$start -> $minSteps""")
            }
        }
    }

    println(minSteps)
}

private fun clean(readLines: List<String>) {
    readLines.forEachIndexed { y, it ->
        it.forEachIndexed { x, char ->
            if (char == 'S') {
                field[x][y] = 'a'.code - 96
                start = x to y
            } else if (char == 'E') {
                field[x][y] = 'z'.code - 96
                end = x to y
            } else
                field[x][y] = char.code - 96
        }
    }
}

fun calcNext(next: Pair<Int,Int>, nextValue: Int): Boolean {
    curSteps ++
    if ( min[next.first][next.second] <= curSteps) {
        curSteps --
        return false
    }
    else
        min[next.first][next.second] = curSteps

    if (next == start) {
        minSteps = min(minSteps, curSteps)

        curSteps --

        return true
    }

    if (curSteps >= minSteps) {
        curSteps --

        return false
    }

    if (next.second + 1 < field[next.first].size && field[next.first][next.second + 1] >= field[next.first][next.second] - 1) {
        field[next.first][next.second] = -5
        calcNext( next.first to next.second + 1, field[next.first][next.second + 1])
        field[next.first][next.second] = nextValue
     }

    if (next.second - 1 >= 0 && field[next.first][next.second -1]>= field[next.first][next.second] - 1){
        field[next.first][next.second] = -5
        calcNext( next.first to next.second - 1,field[next.first][next.second - 1])
        field[next.first][next.second] = nextValue
    }

    if (next.first + 1 < field.size && field[next.first + 1][next.second] >= field[next.first][next.second] - 1){
        field[next.first][next.second] = -5
        calcNext( next.first + 1 to next.second,field[next.first + 1][next.second])
        field[next.first][next.second] = nextValue
    }

    if (next.first - 1 >= 0 && field[next.first - 1][next.second] >= field[next.first][next.second] - 1){
        field[next.first][next.second] = -5
        calcNext( next.first -1 to next.second,field[next.first - 1][next.second])
        field[next.first][next.second] = nextValue
    }


    curSteps --

    return false
}
