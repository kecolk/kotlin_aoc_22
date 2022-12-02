fun main() {
    fun evalRound(input: String): Int {
        return when (input) {
            "A X" -> 4
            "A Y" -> 8
            "A Z" -> 3
            "B X" -> 1
            "B Y" -> 5
            "B Z" -> 9
            "C X" -> 7
            "C Y" -> 2
            "C Z" -> 6
            else -> 0
        }
    }

    fun part1(input: List<String>): Int = input.sumOf { evalRound(it) }

    fun evalRound2(it: String): Int {
        return when (it) {
            "A X" -> 3
            "A Y" -> 4
            "A Z" -> 8
            "B X" -> 1
            "B Y" -> 5
            "B Z" -> 9
            "C X" -> 2
            "C Y" -> 6
            "C Z" -> 7
            else -> 0
        }
    }

    fun part2(input: List<String>): Int = input.sumOf { evalRound2(it) }

    val testInput = readTextInput("Day02_test")
    val input = readTextInput("Day02")

    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    println(part1(input))
    println(part2(input))
}
