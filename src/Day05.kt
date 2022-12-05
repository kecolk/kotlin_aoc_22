fun main() {

    data class Move(val count: Int, val from: Int, val to: Int)

    fun extractStacks(input: List<String>): List<ArrayDeque<Char>> {
        val result = MutableList<ArrayDeque<Char>>(9) { ArrayDeque() }
        input.forEach { line ->
            if (line.isEmpty() || !line.contains("[")) return result
            line.chunked(4).forEachIndexed { index, part ->
                if (part.startsWith("[")) {
                    result[index].add(part[1])
                }
            }
        }
        return result
    }

    fun extractMoves(input: List<String>): List<Move> {
        val result: MutableList<Move> = mutableListOf()
        input.forEach { line ->
            if (line.startsWith("move")) {
                val parts = line.split(" ")
                result.add(
                    Move(
                        count = parts[1].toInt(),
                        from = parts[3].toInt() - 1,
                        to = parts[5].toInt() - 1
                    )
                )
            }
        }
        return result
    }

    fun executeMove(from: ArrayDeque<Char>, to: ArrayDeque<Char>, count: Int) {
        for (i in 1..count) {
            to.addFirst(from.removeFirst())
        }
    }

    fun executeBulkMove(from: ArrayDeque<Char>, to: ArrayDeque<Char>, count: Int) {
        for (i in 1..count) {
            to.add(i - 1, from[i - 1])
        }
        for (i in 1..count) {
            from.removeAt(0)
        }
    }

    fun executeMoves(
        stacks: List<ArrayDeque<Char>>,
        moves: List<Move>,
        executeMove: (ArrayDeque<Char>, ArrayDeque<Char>, Int) -> Unit
    ): String {
        moves.forEach { move ->
            executeMove(stacks[move.from], stacks[move.to], move.count)
        }
        return stacks.fold("") { acc, item -> acc + (item.firstOrNull() ?: "") }
    }

    fun part1(input: List<String>): String =
        executeMoves(extractStacks(input), extractMoves(input), ::executeMove)

    fun part2(input: List<String>): String =
        executeMoves(extractStacks(input), extractMoves(input), ::executeBulkMove)

    val testInput = readTextInput("Day05_test")
    val input = readTextInput("Day05")

    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    println(part1(input))
    println(part2(input))
}
