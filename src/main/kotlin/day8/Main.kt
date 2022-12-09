package day8

const val fileName = "input8.txt"

fun main() {
    val cells = Array<Array<Int>>(99) { Array<Int>(99) { 0 } }
    val readLines = java.io.File(fileName).readLines()

    var count = 0
    readLines.forEachIndexed { idx, row ->
        if (idx == 0 || idx == readLines.size - 1) {

            count += row.length
        } else {
            row.forEachIndexed { idxR, tree ->
                if (idxR == 0 || idxR == row.length - 1) {
                    count++
                } else {
                    if (row.substring(0, idxR).all { it.toString().toInt() < tree.toString().toInt() }) {
                        count++
                    } else if (row.substring(idxR + 1, row.length)
                            .all { it.toString().toInt() < tree.toString().toInt() }
                    ) {
                        count++
                    } else {
                        var foundUp = false
                        var startedUp = false
                        for (i in 0 until idx) {
                            startedUp = true
                            if (readLines[i][idxR].toString().toInt() >= tree.toString().toInt()) foundUp = true
                        }
                        if (startedUp && !foundUp) {
                            count++
                        } else {
                            var foundDown = false
                            var startedDown = false
                            for (i in idx + 1 until readLines.size) {
                                startedDown = true
                                if (readLines[i][idxR].toString().toInt() >= tree.toString().toInt()) foundDown = true
                            }
                            if (startedDown && !foundDown) {
                                count++
                            }
                        }
                    }
                }

            }
        }
    }

    readLines.forEachIndexed { idxC, row ->
        row.forEachIndexed { idxR, tree ->
            var left = 0
            for (i in idxR - 1 downTo 0) {
                left++

                if (row[i].toString().toInt() >= tree.toString().toInt()) {
                    break
                }
            }

            var right = 0
            for (i in idxR + 1 until row.length) {
                    right++
                if (row[i].toString().toInt() >= tree.toString().toInt()) {
                    break
                }
            }


            var up = 0
            for (i in idxC - 1 downTo 0) {
                up++
                if (readLines[i][idxR].toString().toInt() >= tree.toString().toInt()) break
            }

            var down = 0
            for (i in idxC + 1 until readLines.size) {
                down++
                if (readLines[i][idxR].toString().toInt() >= tree.toString().toInt()) break
            }
            cells[idxC][idxR] = up*down*left*right
        }



    }

    val map = cells.mapNotNull { it.maxOrNull() }.maxOrNull()

    println(map)
}
