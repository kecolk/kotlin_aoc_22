fun main() {

    fun isDistinct(buffer: List<Char>): Boolean =
        buffer.all { chr -> buffer.count { it == chr } == 1 }

    fun findStart(input: String, size: Int): Int {
        val signal = input.toList()
        signal.windowed(size = size, step = 1, partialWindows = false)
            .forEachIndexed { index, window ->
                if (isDistinct(window)) return index + size
            }
        return 0
    }

    fun part1(input: String): Int {
        return findStart(input, 4)
    }

    fun part2(input: String): Int {
        return findStart(input, 14)
    }

    val testInput = readTextInput("Day06_test").first()
    val input = readTextInput("Day06").first()

    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    println(part1(input))
    println(part2(input))
}
