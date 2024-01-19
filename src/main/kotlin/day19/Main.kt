package day19

import kotlin.math.absoluteValue

const val fileName = "input19.txt"

fun main() {


    val readLines = java.io.File(fileName).readLines()

    val list = readLines.mapIndexed { index, s -> MyNum(index, s.toInt(), null, null) }
    list.forEachIndexed() { idx, it ->
        when (idx) {
            0 -> {
                it.prev = list.last()
                it.next = list[1]
            }

            list.lastIndex -> {
                it.prev = list[idx - 1]
                it.next = list.first()
            }

            else -> {
                it.prev = list[idx - 1]
                it.next = list[idx + 1]
            }
        }


    }

    checkList(list)

    list.forEachIndexed { index, num ->


        val delta = num.value
        val to = if (delta < 0) {
            var n = num
            (1..delta.absoluteValue + 1).forEach {
                n = n.prev!!
            }
            n
        } else if (delta == 0) {
            num
        }else {
                var n = num
                (1..delta).forEach {
                    n = n.next!!
                }
                n
            }

        moveAfter(num, to)
        checkList(list)

    }

    printIdx(list)

    val zero = list.find { it.value == 0 }!!

    val first = nextX(zero, 1000)
    val second = nextX(zero, 2000)
    val third = nextX(zero, 3000)

    println(first.value + second.value + third.value)

}

fun checkList(list: List<MyNum>) {
    list.forEach {
        if (it.next!!.prev!! != it || it.prev!!.next!! != it)
            throw Exception()

    }

    var i = list.first()
    var j = list.first().next
    var c = 1
    while (i != j) {
        j = j!!.next
        c++
    }

    if (c != list.count()) throw Exception()
}

fun nextX(num: MyNum, i: Int): MyNum {
    var n = num
    (1..i).forEach {
        n = n.next!!
    }
    println("$i --> ${n.value}")
    return n
}

fun moveAfter(num: MyNum, to: MyNum) {
    if (num == to) return
    val toNext = to.next
    val toPrev = to.prev
    val numNext = num.next
    val numPrev = num.prev

    num.prev!!.next = num.next
    num.next!!.prev = num.prev


    to.next = num
    num.prev = to
    num.next = toNext
    toNext!!.prev = num


}

fun printIdx(l: List<MyNum>) {

    val zero = l.find { it.value == 0 }!!
    println(l.indexOf(zero))
    var x = zero
    (l.indices).forEach {
        print("${x.value} ")
        x = x.next!!
    }
    println()
}

class MyNum(val idx: Int, val value: Int, var next: MyNum?, var prev: MyNum?)
