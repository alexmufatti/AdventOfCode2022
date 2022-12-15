package day11

const val fileName = "input11.txt"

fun main() {
    val readLines = java.io.File(fileName).readLines()

    val monkeys = mutableListOf<Monkey>()

    for (i in readLines.indices step 7) {
        val monkey = readLines[i][readLines[i].length-2].toString().toInt()
        val items = readLines[i+1].split(':')[1].trim().split(",").map { it.trim().toLong() }
        val fn = readLines[i+2].split("=")[1].trim().trim()
        val div = readLines[i+3].split(' ').last().toString().trim().toLong()
        val ifTrue = readLines[i+4].last().toString().trim().toInt()
        val ifFalse = readLines[i+5].last().toString().trim().toInt()
        monkeys.add(Monkey(monkey, items.toMutableList(), fn, div, ifTrue, ifFalse))
    }

    val x = monkeys.map { it.div }.fold(1L) { acc, x -> acc*x }

    for (i in 0 until 10000) {
        monkeys.forEach {monkey ->
            monkey.items.forEach {
                val new = calFunction(monkey.operation, it) % x

                if (new % monkey.div == 0L) {
                    monkeys[monkey.monkeyIfTrue].items.add(new)
                } else
                    monkeys[monkey.monkeyIfFalse].items.add(new)

                monkey.inspections ++
            }
            monkey.items.clear()
        }
        println(i)
    }

    val sorted = monkeys.sortedByDescending { it.inspections }


    println(sorted[0].inspections*sorted[1].inspections)
}

fun calFunction(fn: String, old: Long): Long {
    val op1 = fn.split(' ')[0]
    val op2 = fn.split(' ')[2]
    val op1val = if (op1 == "old") old else op1.toLong()
    val op2val = if (op2 == "old") old else op2.toLong()
    return if (fn.split(' ')[1] == "+") op1val + op2val else op1val * op2val
}

data class Monkey(val id:Int, val items: MutableList<Long> = mutableListOf(), val operation: String,val div: Long,  val monkeyIfTrue: Int, val monkeyIfFalse:Int, var inspections: Long = 0)
