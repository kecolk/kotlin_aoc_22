fun main() {
    fun Char.toPriority(): Int {
        return when {
            this.isLowerCase() -> this - 'a' + 1
            else -> this - 'A' + 27
        }
    }

    fun String.toPriorities() = this.toCharArray().map { it.toPriority() }.toSet()

    fun getErrorPriority(rucksack: String): Int {
        val compartment1 = rucksack.take(rucksack.length / 2).toPriorities()
        val compartment2 = rucksack.substring(rucksack.length / 2).toPriorities()
        return compartment1.intersect(compartment2).firstOrNull() ?: 0
    }

    fun getGroupBadgePriority(group: List<String>): Int {
        require(group.size == 3)
        val rucksacks = group.map { rucksack -> rucksack.toPriorities() }
        return rucksacks[0].intersect(rucksacks[1]).intersect(rucksacks[2]).firstOrNull() ?: 0
    }

    fun part1(input: List<String>): Int = input.sumOf { getErrorPriority(it) }

    fun part2(input: List<String>): Int = input.chunked(3).sumOf { getGroupBadgePriority(it) }

    val testInput = readTextInput("Day03_test")
    val input = readTextInput("Day03")

    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    println(part1(input))
    println(part2(input))
}
