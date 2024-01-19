package day16

import kotlin.math.max

const val fileName = "input16.txt"


fun main() {

    val calcData = CalcData()
    val readLines = java.io.File(fileName).readLines()
    calcData.valves = readLines.map {
        parseLine(it)
    }.associateBy { it.name }
    calcData.timeTo = calcTimes(calcData.valves)
    var currentValve: Valve = calcData.valves["AA"]!!
    nextStep(currentValve, listOf("AA"),0,0, calcData, 30)

    println(calcData.maxFlow)

    val nodeCount = calcData.valves.values.count { it.flow > 0 }
    val nodeIds = calcData.valves.values.filter { it.flow > 0 }.map { it.name }
    val f = (0 until (1 shl nodeCount - 1)).maxOf { bitmask ->
        val myNodes = nodeIds.filterIndexed { index, _ -> (1 shl index).and(bitmask) == 0 }.toSet()
        val calcDataMy = CalcData(0, calcData.valves, calcData.timeTo.filter { k -> k.key in myNodes || k.key == "AA" })
        val calcDataEle = CalcData(0, calcData.valves, calcData.timeTo.filter { k -> k.key !in myNodes || k.key == "AA" })
        var aa: Valve = calcData.valves["AA"]!!
        nextStep(aa, listOf("AA"),0,0, calcDataMy, 26)
        nextStep(aa, listOf("AA"),0,0, calcDataEle, 26)

        calcDataMy.maxFlow + calcDataEle.maxFlow
    }

    println(f)
}

fun calcTimes(valves: Map<String, Valve>): Map<String, Map<String, Int>> {
    return valves.map { v ->
        if (v.value.flow == 0) v to emptyMap<String,Int>()
        val distances = mutableMapOf(v.key to 0)
        while (distances.size < valves.size) {
            val tmp = mutableMapOf<String, Int>()
            distances.forEach { (id, dist) ->
                val neighbors = valves[id]!!.tunnels
                neighbors.forEach { n ->
                    val curr = distances[n]
                    if (curr == null || curr > dist + 1)
                        tmp[n] = dist + 1
                }
            }
            distances += tmp
        }
        v.key to distances

    }.associateBy { it.first }.mapValues { it.value.second }
}

fun nextStep(currentValve: Valve, path: List<String>,flow: Int, min:Int, calcData: CalcData, maxTime: Int) {

    val possibleTunnels = calcData.timeTo[currentValve.name]?.filter { it.key !in path && calcData.valves[it.key]?.flow != 0}?: emptyMap()

    if (possibleTunnels.isEmpty()) {
        calcData.maxFlow = max(flow, calcData.maxFlow)
        return
    }

    possibleTunnels.forEach{ (valve, time) ->
        if (min + time + 1 <= maxTime) {
            nextStep(calcData.valves[valve]!!,
                path + valve,
                flow + ((maxTime - (min + time + 1))*calcData.valves[valve]!!.flow) ,
                min + time + 1,
                calcData, maxTime)
        } else
        {
            calcData.maxFlow = max(flow, calcData.maxFlow)
            return
        }

    }

}

fun parseLine(line: String): Valve {
    val name = line.substring(6, 8)
    val flow = line.substring(line.indexOf('=') + 1, line.indexOf(';')).toInt()
    val tunnels = line.substring(line.indexOf("valves ") + 7).split(',').map { it.trim() }
    return Valve(name, flow, tunnels)
}

data class Valve(val name: String, val flow: Int, val tunnels: List<String>)

data class CalcData(
    var maxFlow: Int = 0,
    var valves: Map<String,Valve> = emptyMap(),
    var timeTo: Map<String, Map<String, Int>> = emptyMap()
)
