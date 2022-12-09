import kotlin.math.abs
import kotlin.math.sign

fun main() {

    data class Move(val move: Char, val count: Int)
    data class Knot(var x: Int = 0, var y: Int = 0)

    fun parseInput(input: List<String>): List<Move> = input.map {
        val parts = it.split(" ")
        Move(parts[0].first(), parts[1].toInt())
    }

    fun shouldMoveKnot(head: Knot, tail: Knot): Boolean =
        abs(head.y - tail.y) > 1 || abs(head.x - tail.x) > 1

    fun moveTail(head: Knot, tail: Knot) {
        if (shouldMoveKnot(head, tail)) {
            tail.x += (head.x - tail.x).sign
            tail.y += (head.y - tail.y).sign
        }
    }

    fun executeMoves(moves: List<Move>, numberOfKnots: Int): Int {

        val knots = List(numberOfKnots) { Knot() }
        val visited = HashSet<Int>()

        moves.forEach { move ->
            for (i in 0 until move.count) {

                when (move.move) {
                    'R' -> knots.first().x += 1
                    'L' -> knots.first().x -= 1
                    'U' -> knots.first().y += 1
                    'D' -> knots.first().y -= 1
                }

                knots.windowed(2, 1) {
                    moveTail(it.first(), it.last())
                }

                knots.last().apply {
                    visited.add(1000 * x + y)
                }
            }
        }
        return visited.count()
    }

    val testMoves = parseInput(readTextInput("Day09_test"))
    val testMoves2 = parseInput(readTextInput("Day09_test2"))
    val moves = parseInput(readTextInput("Day09"))

    check(executeMoves(testMoves, 2) == 13)
    check(executeMoves(testMoves2, 10) == 36)

    println(executeMoves(moves, 2))
    println(executeMoves(moves, 10))
}
