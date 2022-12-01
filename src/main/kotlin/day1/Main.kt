package day1
import java.io.File

val fileName = "input1.txt"

fun main(args: Array<String>) {
    val readLines = File(fileName).readLines()
    val calories = mutableMapOf<Int, Int>()
    var i = 0
    readLines.forEach {
        if  (it.isBlank()) {
            i++
        } else {

            calories[i] = calories[i]?.plus(Integer.parseInt(it))?:Integer.parseInt(it)
        }
    }

    println(calories.maxOf { it.value })

    val sorted = calories.values.sortedDescending()

    println(sorted[0]+sorted[1]+sorted[2])
}
