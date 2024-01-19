package day17

import kotlin.system.exitProcess

const val fileName = "input17.txt"

var magicNumber = 0L

fun main() {

    val rocks = listOf(S(),P(),L(),I(),Q())
    val readLines = java.io.File(fileName).readLines()

    magicNumber = readLines[0].length*5L

    println(run(rocks, readLines, 2022))

    println(run(rocks, readLines, 1000000000000))
}

private fun run(rocks: List<Rock>, readLines: List<String>, limit: Long): Long {
    val ret = findLoop(rocks, readLines, limit)
    if (ret.second == limit) return ret.first

    else return (ret.first * (limit % ret.second)) + run(rocks, readLines, limit / ret.second)
}

private fun findLoop(
    rocks: List<Rock>,
    readLines: List<String>,
    limit: Long
): Pair<Long,Long> {
    var delta = 0L
    var stoppedRocks = 0L
    var chamber = Array(7) { Array(2022 * 40) { "." } }
    var rock = getNextRock(rocks, stoppedRocks)
    var currentPos = 2 to 3
    while (true) {
        readLines[0].forEach { jet ->
            if (stoppedRocks == limit) {
                printChamber(chamber)
                return calcMaxHeight(chamber) + delta + 1 to stoppedRocks
            }
            currentPos = when (jet) {
                '<' -> rock.moveLeft(chamber, currentPos)
                '>' -> rock.moveRight(chamber, currentPos)
                else -> {
                    throw Exception()
                }
            }

            val down = rock.moveDown(chamber, currentPos)
            if (down != null) {
                currentPos = down
            } else {
                rock.stop(chamber, currentPos)
                stoppedRocks++
                val cleaned = cleanChamber(chamber, delta)
                delta = cleaned.first
                chamber = cleaned.second
                val loop = cleaned.third
                if (loop) return calcMaxHeight(chamber) + delta + 1 to stoppedRocks
                currentPos = 2 to calcMaxHeight(chamber) + 4
                if (stoppedRocks % 1_000_000 == 0L)
                    println("$stoppedRocks $currentPos")
                rock = getNextRock(rocks, stoppedRocks)

            }

        }
    }
}

fun cleanChamber(chamber: Array<Array<String>>, delta:Long): Triple<Long, Array<Array<String>>, Boolean> {
    var cutLine = 0
    var loop = false
    for (y in chamber[0].indices) {
        if ((chamber.indices).all { x ->
            chamber[x][y] == "#"
        })
            cutLine = y + 1
    }

    return if (cutLine !=0) {
        val newChamber = Array(7) { Array(2022 * 40) { "." } }

        for (y in cutLine until chamber[0].size) {
            for (x in chamber.indices) {
                newChamber[x][y-cutLine] = chamber[x][y]
            }
        }
        if (delta != 0L && delta % (magicNumber) == 0L) {
            println("trovatoooo --> $delta")
            loop = true
        }
        Triple(delta + cutLine, newChamber, loop)
    } else
        Triple (delta, chamber, loop)
}

fun calcMaxHeight(chamber: Array<Array<String>>): Int {
    return chamber.maxOf { it.lastIndexOf("#") }
}

fun getNextRock(rocks: List<Rock>, stoppedRocks: Long): Rock {
    return rocks[(stoppedRocks % 5).toInt()]
}

fun printChamber(result: Array<Array<String>>) {
    for (k in 1..10) println()
    for (i in result[0].indices) {
        for (j in result.indices) {
            print(result[j][result[0].size - i -1])
        }
        println()
    }
}

abstract class Rock {
    fun moveRight(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Pair<Int,Int> {
        return if (canMoveRight(chamber, pos)) {
            pos.first + 1 to pos.second
        } else
            pos
    }

    fun moveLeft(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Pair<Int,Int> {
        return if (canMoveLeft(chamber, pos)) {
            pos.first -1 to pos.second
        } else
            pos
    }

    fun moveDown(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Pair<Int,Int>? {
        return if (canMoveDown(chamber, pos)) {
            pos.first to pos.second - 1
        } else
            null
    }

    abstract fun canMoveRight(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean
    abstract fun canMoveDown(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean
    abstract fun canMoveLeft(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean
    abstract fun stop(chamber: Array<Array<String>>, pos: Pair<Int, Int>)
}

class L : Rock() {
    // ..#
    // ..#
    // ###
    override fun canMoveRight(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.first + 3 < chamber.size
                && chamber[pos.first + 3][pos.second] == "."
                && chamber[pos.first + 3][pos.second + 1] == "."
                && chamber[pos.first + 3][pos.second + 2] == ".")
    }

    override fun canMoveDown(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.second - 1 >= 0
                && chamber[pos.first][pos.second - 1] == "."
                && chamber[pos.first + 1][pos.second - 1] == "."
                && chamber[pos.first + 2][pos.second - 1] == ".")
    }

    override fun canMoveLeft(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.first - 1 >= 0
                && chamber[pos.first - 1][pos.second] == "."
                && chamber[pos.first + 1][pos.second + 1] == "."
                && chamber[pos.first + 1][pos.second + 2] == ".")
    }

    override fun stop(chamber: Array<Array<String>>, pos: Pair<Int, Int>) {
        chamber[pos.first][pos.second] = "#"
        chamber[pos.first + 1][pos.second] = "#"
        chamber[pos.first + 2][pos.second] = "#"
        chamber[pos.first + 2][pos.second + 1] = "#"
        chamber[pos.first + 2][pos.second + 2] = "#"
    }
}

class I : Rock() {
    // #
    // #
    // #
    // #
    override fun canMoveRight(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.first + 1 < chamber.size
                && (0..3).all { chamber[pos.first + 1][pos.second + it] == "." })
    }

    override fun canMoveLeft(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.first - 1 >= 0
                && (0..3).all { chamber[pos.first - 1][pos.second + it] == "." })
    }

    override fun stop(chamber: Array<Array<String>>, pos: Pair<Int, Int>) {
        chamber[pos.first][pos.second] = "#"
        chamber[pos.first][pos.second+1] = "#"
        chamber[pos.first][pos.second+2] = "#"
        chamber[pos.first][pos.second+3] = "#"
    }

    override fun canMoveDown(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.second - 1 >= 0
                && chamber[pos.first][pos.second - 1] == ".")
    }
}

class P : Rock() {
    // .#.
    // ###
    // .#.
    override fun canMoveRight(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.first + 3 < chamber.size
                && chamber[pos.first + 2][pos.second] == "."
                && chamber[pos.first + 3][pos.second + 1] == "."
                && chamber[pos.first + 2][pos.second + 2] == ".")
    }

    override fun canMoveDown(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.second - 1 >= 0
                && chamber[pos.first][pos.second] == "."
                && chamber[pos.first + 1][pos.second - 1] == "."
                && chamber[pos.first + 2][pos.second] == ".")
    }

    override fun canMoveLeft(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.first - 1 >= 0
                && chamber[pos.first][pos.second] == "."
                && chamber[pos.first - 1][pos.second + 1] == "."
                && chamber[pos.first][pos.second + 2] == ".")
    }

    override fun stop(chamber: Array<Array<String>>, pos: Pair<Int, Int>) {
        chamber[pos.first+1][pos.second] = "#"
        chamber[pos.first][pos.second+1] = "#"
        chamber[pos.first+1][pos.second+1] = "#"
        chamber[pos.first+2][pos.second+1] = "#"
        chamber[pos.first+1][pos.second+2] = "#"
    }
}

class S : Rock() {
    // ####
    override fun canMoveRight(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.first + 4 < chamber.size
                && chamber[pos.first + 4][pos.second] == ".")
    }

    override fun canMoveDown(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.second - 1 >= 0
                && chamber[pos.first][pos.second - 1] == "."
                && chamber[pos.first + 1][pos.second - 1] == "."
                && chamber[pos.first + 2][pos.second - 1] == "."
                && chamber[pos.first + 3][pos.second - 1] == ".")
    }

    override fun canMoveLeft(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.first - 1 >= 0
                && chamber[pos.first -1][pos.second] == ".")
    }

    override fun stop(chamber: Array<Array<String>>, pos: Pair<Int, Int>) {
        chamber[pos.first][pos.second] = "#"
        chamber[pos.first+1][pos.second] = "#"
        chamber[pos.first+2][pos.second] = "#"
        chamber[pos.first+3][pos.second] = "#"
    }
}

class Q : Rock() {
    // ##
    // ##
    override fun canMoveRight(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.first + 2 < chamber.size
                && chamber[pos.first + 2][pos.second] == "."
                && chamber[pos.first + 2][pos.second+1] == ".")
    }

    override fun canMoveDown(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.second - 1 >= 0
                && chamber[pos.first][pos.second - 1] == "."
                && chamber[pos.first + 1][pos.second - 1] == ".")
    }

    override fun canMoveLeft(chamber: Array<Array<String>>, pos: Pair<Int, Int>): Boolean {
        return (pos.first - 1 >= 0
                && chamber[pos.first -1][pos.second] == "."
                && chamber[pos.first -1][pos.second + 1] == ".")
    }

    override fun stop(chamber: Array<Array<String>>, pos: Pair<Int, Int>) {
        chamber[pos.first][pos.second] = "#"
        chamber[pos.first+1][pos.second] = "#"
        chamber[pos.first][pos.second+1] = "#"
        chamber[pos.first+1][pos.second+1] = "#"
    }
}
