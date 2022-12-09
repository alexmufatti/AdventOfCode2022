package day6

import java.io.File

const val fileName = "input6.txt"

fun main() {
    val chars = File(fileName).readLines()[0]

    run breaking@ {
    chars.forEachIndexed() { idx, _ ->
        if (chars.substring(idx, idx+4).toList().distinct().size == 4) {
            println(idx+4)
            return@breaking
        }
    }
    }

    run breaking@ {
    chars.forEachIndexed() { idx, _ ->
        if (chars.substring(idx, idx+14).toList().distinct().size == 14) {
            println(idx+14)
            return@breaking
        }
    }
    }
}
