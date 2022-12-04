data class ElfAssignment(val elf1: IntRange, val elf2: IntRange){
    fun isContained(): Boolean =
        (elf1.contains(elf2.first) && elf1.contains(elf2.last)) ||
                (elf2.contains(elf1.first) && elf2.contains(elf1.last))

    fun isOverlapping(): Boolean =
        elf1.contains(elf2.first) ||
                elf1.contains(elf2.last) ||
                elf2.contains(elf1.first)

    companion object{
        fun fromString(input: String): ElfAssignment{
            val (elf1, elf2) = input.split(",")
            return ElfAssignment(elf1.toRange(), elf2.toRange())
        }
    }
}

fun String.toRange() = IntRange(
    start = split("-").first().toInt(),
    endInclusive = split("-").last().toInt()
)

fun main() {

    fun part1(input: List<String>): Int = input
        .map { ElfAssignment.fromString(it) }
        .count { it.isContained() }

    fun part2(input: List<String>): Int = input
        .map { ElfAssignment.fromString(it) }
        .count { it.isOverlapping() }

    val testInput = readTextInput("Day04_test")
    val input = readTextInput("Day04")

    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    println(part1(input))
    println(part2(input))
}
