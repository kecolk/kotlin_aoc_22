fun main() {
    fun elvesCalories(input: List<Int?>): Collection<Int> {
        return input
            .scan(
                Pair<Int, Int?>(
                    0,
                    0
                )
            ) { acc, value -> Pair(if (value == null) acc.first + 1 else acc.first, value) }
            .groupingBy { it.first }
            .fold(0) { acc, element -> element.second?.let { acc + it } ?: acc }
            .values
    }

    fun part1(input: List<Int?>): Int = elvesCalories(input).max()

    fun part2(input: List<Int?>): Int =
        elvesCalories(input).sorted().takeLast(3).sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
