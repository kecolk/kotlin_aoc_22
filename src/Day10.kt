data class Instruction(val operation: String, val operand: Int = 0)

fun parseInput(input: List<String>): List<Instruction> = input.map {
    val parts = it.split(" ")
    Instruction(
        operation = parts[0],
        operand = if (parts[0] == "addx") parts[1].toInt() else 0
    )
}

class Processor() {
    private var acc: Int = 1
    private var pcounter = 1

    private val signal: MutableList<Int> = mutableListOf()
    private val screen: MutableList<String> = mutableListOf()
    private var currentLine = ""

    fun execute(program: List<Instruction>) {
        program.forEach { instruction ->
            if (instruction.operation == "noop") {
                tick()
            }
            if (instruction.operation == "addx") {
                tick()
                tick()
                acc += instruction.operand
            }
        }
    }

    fun getSignalStrength() = signal.sum()

    fun printScreen() {
        screen.forEach { println(it) }
    }

    private fun tick() {
        if ((pcounter + 20).mod(40) == 0) signal.add(pcounter * acc)
        drawPixel()
        pcounter += 1
    }

    private fun drawPixel() {
        val column = (pcounter - 1).mod(40)
        currentLine += if (column >= acc - 1 && column <= acc + 1) "#" else "."
        if (column == 39) {
            screen.add(currentLine)
            currentLine = ""
        }
    }
}

fun main() {
    val testInput = parseInput(readTextInput("Day10_test"))
    val input = parseInput(readTextInput("Day10"))

    check(part1(testInput) == 13140)
    part2(testInput)

    println(part1(input))
    part2(input)
}

fun part1(program: List<Instruction>): Int {
    Processor().apply {
        execute(program)
        return getSignalStrength()
    }
}

fun part2(program: List<Instruction>) {
    Processor().apply {
        execute(program)
        printScreen()
    }
}
