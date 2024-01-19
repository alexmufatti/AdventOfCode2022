package day15

import kotlin.math.abs

const val fileName = "input15.txt"
fun main() {

    val readLines = java.io.File(fileName).readLines()
    val sensors = readLines.map {
        parseLine(it)
    }

    val r = sensors.map { it.atRow(2_000_000) }.fold(emptySet<Long>()) { acc, a -> acc+a.toSet()}.sorted()


    println((r.size - sensors.filter { it.beaconY == 2_000_000L }.distinctBy { it.beaconX }.count()))

    for (i in 0..4_000_000L){
        val r1 = sensors.flatMap{ it.atRowLimited(i, 0..4_000_000L) }.toSet()

        if (r1.size != 4_000_001) {
            val x = findHole(r1)
            println(tuning(x,i))
            println("""$x, $i""")
            return
        }
        //if (i%100L == 0L)
            println(i)
    }
}

fun findHole(r: Set<Long>): Long {
    for (i in 0..4_000_000L){
        if (!r.contains(i))
            return i
    }
    throw Exception()
}

fun tuning(x: Long, y: Long) = x * 4_000_000L + y
fun parseLine(line: String): Sensor {
    val s = line.split(":")
    val x = getX(s[0])
    val y = getY(s[0])
    val sx =  getX(s[1])
    val sy = getY(s[1])
    return Sensor(x,y,sx,sy)
}

private fun getX(s: String) = s.substring(s.indexOf("x=") + 2, s.indexOf(",")).toLong()
private fun getY(s: String) = s.substring(s.indexOf("y=") + 2).toLong()

data class Sensor(val x: Long, val y:Long, val beaconX: Long, val beaconY:Long) {
    val distance = abs(x - beaconX) + abs(y - beaconY)
    fun atRow(row:Long): LongRange {
        val rowDistance = abs(y - row)
        if (rowDistance > distance) return LongRange.EMPTY

        return x - (distance - rowDistance)..x+(distance - rowDistance)
    }

    fun atRowLimited(row:Long, columnRange:LongRange): LongRange {
        val rowDistance = abs(y - row)
        if (rowDistance > distance) return LongRange.EMPTY

        val min = if (x - (distance - rowDistance) <= columnRange.first) columnRange.first else x - (distance - rowDistance)
        val max = if (x+(distance - rowDistance) >= columnRange.last) columnRange.last else x+(distance - rowDistance)

        return min..max
    }
}
