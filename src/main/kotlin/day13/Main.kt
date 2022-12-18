package day13

const val fileName = "input13.txt"
fun main() {

    val result = mutableListOf<Int>()
    val readLines = java.io.File(fileName).readLines()
    val fullList = mutableListOf<Part>()

    for (i in readLines.indices step 3) {
        val first = parsePart(readLines[i])
        val second = parsePart(readLines[i + 1])

        val res = compare(first, second)

        fullList.add(first)
        fullList.add(second)

        println(if (res == -1) "correct" else if (res == 1) "no correct" else "same")

        result.add(res)
    }

    println(result.foldIndexed(0) { idx, acc, it -> if (it != 1) acc + idx + 1 else acc })


    val elA = PartList(listOf(PartInt(2)))
    fullList.add(elA)
    val elB = PartList(listOf(PartInt(6)))
    fullList.add(elB)

    fullList.sortWith(myCustomComparator)

    println((fullList.indexOf(elA) +1) * (fullList.indexOf(elB)+1))
}

private val myCustomComparator =  Comparator<Part> { a, b ->
    compare(a,b)
}

fun compare(first: Part, second: Part): Int {
    if (first.type() == PartType.INT && second.type() == PartType.INT) {
        val value1 = (first as PartInt).value
        val value2 = (second as PartInt).value
        return value1.compareTo(value2)
    } else if (first.type() == PartType.LIST && second.type() == PartType.LIST) {
        val value1 = (first as PartList).value
        val value2 = (second as PartList).value
        var comp = 0
        run breaking@{
            value1.forEachIndexed { idx, v ->
                val res = if (value2.size > idx)
                    compare(v, value2[idx])
                else {
                    comp = 0
                    return@breaking
                }

                if (res != 0) {
                    comp = res
                    return@breaking
                }
            }
        }
        if (comp == 0)
            comp = value1.size.compareTo(value2.size)
        return comp

    } else if (first.type() == PartType.INT && second.type() == PartType.LIST) {
        return compare(PartList(listOf(first)), second)
    } else if (first.type() == PartType.LIST && second.type() == PartType.INT) {
        return compare(first, PartList(listOf(second)))
    } else throw Exception()

}

fun parseList(content: String): List<Part> {
    var p = 0
    var start = 0
    val ret = mutableListOf<Part>()
    var intoBracket = false

    content.forEachIndexed { idx, c ->
        if (c == '[') {
            intoBracket = true
            p++
        }
        if (c == ']') p--

        if (c == ',' && !intoBracket && content[idx -1] != ']') {
            ret.add(parsePart(content.substring(start..idx).trim(',')))
            start = idx + 1
        } else if (c != ',' && p == 0 && intoBracket ) {
            intoBracket = false
            ret.add(parsePart(content.substring(start..idx).trim(',')))
            start = idx + 1
        } else if (idx == content.length -1) {
            ret.add(parsePart(content.substring(start..idx).trim(',')))
            start = idx + 1
        }
    }

    return ret
}

fun parsePart(s: String): Part {
    return if (s.isEmpty()) {
        throw Exception()
    } else if (s[0] == '[') {
        val content = s.substring(1..s.length - 2)
        PartList(parseList(content))
    } else {
        PartInt(s.toInt())
    }

}

interface Part {
    fun type(): PartType
}

data class PartList(val value: List<Part>) : Part {
    override fun type(): PartType {
        return PartType.LIST
    }
}

data class PartInt(val value: Int) : Part {
    override fun type(): PartType {
        return PartType.INT
    }
}

enum class PartType {
    LIST,
    INT
}
