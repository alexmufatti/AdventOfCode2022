package day10

const val fileName = "input10.txt"

fun main() {
    val readLines = java.io.File(fileName).readLines()
    val time = mutableListOf(1)
    var loop = true
    var lineN = 0
    var nextCount = 20
    var count = 0
    var lcd = 0
    val display = mutableListOf<String>()
    while (loop) {
        val currentLine = readLines[lineN]
        if (currentLine == "noop") {
            time.add(time.last())
        } else {
            val add = currentLine.split(' ')[1].toInt()
            time.add(time.last())
            time.add(time.last() + add)
        }

        lineN++
        if (lineN == readLines.size) loop = false
        println(time.last())



    }

    time.forEachIndexed { index, i ->
        if (index == nextCount)
        {
            count += nextCount*time[nextCount - 1]
            nextCount += 40
        }
        if (lcd % 40 == i-1 || lcd % 40 == i || lcd % 40 == i+1)
            display.add("#")
        else
            display.add(".")

        lcd++
    }

    display.forEachIndexed { index, i ->
        if (index % 40 == 0)
        {
            println()
        }
        print(i)
    }


    println(count)





}
