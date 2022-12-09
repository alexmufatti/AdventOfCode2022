package day5

import java.io.File

const val fileName = "input5.txt"

fun main() {
    val readLines = File(fileName).readLines()

    val stacks = listOf<MutableList<String>>(mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf())

    for (i in 0..7 ) {
        for (k in 0..8) {
            val letter = readLines[7 - i].substring((k * 4) + 1, (k * 4) + 2)
            if (letter.isNotBlank())
                stacks[k].add(letter)
        }
    }

    printStacks(stacks)

    for (i in 10 until readLines.size ) {
        println(readLines[i])
        val qta = readLines[i].split(" ")[1].toInt()
        val from = readLines[i].split(" ")[3].toInt()
        val to = readLines[i].split(" ")[5].toInt()

        val fromS = stacks[from-1]
        val toS = stacks[to-1]

        for (r in 0 until qta) {
            val removed = fromS.removeAt(fromS.size-1)
            toS.add(removed)
        }
        printStacks(stacks)
    }
    println("-----------------")
    stacks.forEach{ print(it.last())}


    val stacks2 = listOf<MutableList<String>>(mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf(),mutableListOf())

    for (i in 0..7 ) {
        for (k in 0..8) {
            val letter = readLines[7 - i].substring((k * 4) + 1, (k * 4) + 2)
            if (letter.isNotBlank())
                stacks2[k].add(letter)
        }
    }

    printStacks(stacks2)

    for (i in 10 until readLines.size ) {
        println(readLines[i])
        val qta = readLines[i].split(" ")[1].toInt()
        val from = readLines[i].split(" ")[3].toInt()
        val to = readLines[i].split(" ")[5].toInt()

        val fromS = stacks2[from-1]
        val toS = stacks2[to-1]

        for (r in 0 until qta) {
            val removed = fromS.removeAt(fromS.size-(qta-r))
            toS.add(removed)
        }
        printStacks(stacks2)
    }
    println("-----------------")
    stacks2.forEach{ print(it.last())}
}

private fun printStacks(stacks: List<MutableList<String>>) {
    stacks.forEach { println(it) }
}
