package day2
import java.io.File

const val fileName = "input2.txt"

fun main() {
    val readLines = File(fileName).readLines()

    val firstStrategyResult = readLines
        .map {match -> Play(MyCode.valueOf(match[2].toString()).shape, TheirCode.valueOf(match[0].toString()).shape) }
        .sumOf { play -> resultPoints(play) + play.myPlay.point }

    println(firstStrategyResult)

    val secondStrategyResult = readLines.map { match ->
        Play(calculateMove(
            match[0],
            match[2]
        ),TheirCode.valueOf(match[0].toString()).shape)
    }.sumOf { play -> resultPoints(play) + play.myPlay.point }

    println(secondStrategyResult)
}

fun calculateMove(their: Char, result: Char): Shape {
    val theirPlay = TheirCode.valueOf(their.toString()).shape
    val points = MyCode2.valueOf(result.toString()).result

    return results.filter { (_, result) -> result == points }.filter { (play, _) -> play.theirPlay == theirPlay }.keys.first().myPlay
}

fun resultPoints(play: Play): Int {
    return results[play]?:0
}


enum class Shape(val point: Int) {
    ROCK(1),
    PAPER(2),
    SCISSOR(3)
}
enum class MyCode(val shape: Shape) {
    X(Shape.ROCK),
    Y(Shape.PAPER),
    Z(Shape.SCISSOR)
}

enum class MyCode2(val result: Int) {
    X(0),
    Y(3),
    Z(6)
}

enum class TheirCode(val shape: Shape) {
    A(Shape.ROCK),
    B(Shape.PAPER),
    C(Shape.SCISSOR)
}

val results: Map<Play, Int> = mapOf(
    Play(Shape.ROCK, Shape.ROCK) to 3,
    Play(Shape.PAPER, Shape.PAPER) to 3,
    Play(Shape.SCISSOR, Shape.SCISSOR) to 3,
    Play(Shape.ROCK, Shape.PAPER) to 0,
    Play(Shape.ROCK, Shape.SCISSOR) to 6,
    Play(Shape.PAPER, Shape.ROCK) to 6,
    Play(Shape.PAPER, Shape.SCISSOR) to 0,
    Play(Shape.SCISSOR, Shape.ROCK) to 0,
    Play(Shape.SCISSOR, Shape.PAPER) to 6

)

data class Play(val myPlay: Shape, val theirPlay: Shape)
