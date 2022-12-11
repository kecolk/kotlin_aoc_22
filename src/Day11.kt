enum class Operation { ADD, MULTIPLY, SQUARE }

fun main() {

    data class Monkey(
        var items: ArrayDeque<Long> = ArrayDeque(),
        val operation: Operation = Operation.MULTIPLY,
        val operand: Long = 0,
        val divisibleBy: Long = 1,
        val onTrue: Int = 0,
        val onFalse: Int = 0,
        var inspections: Long = 0,
    )

    fun parseInput(input: List<String>): List<Monkey> {
        val result: MutableList<Monkey> = mutableListOf()
        var current = Monkey()
        input.forEach { line ->
            val parts = line.split(" ").filter { it.isNotBlank() }
            when {
                parts.isEmpty() -> {}
                parts.first() == "Starting" -> {
                    parts.forEach {
                        it.split(",").first().toLongOrNull()?.let { item ->
                            current.items.add(item)
                        }
                    }
                }
                parts.first() == "Operation:" -> {
                    val operation =
                        when {
                            parts.last() == "old" -> Operation.SQUARE
                            parts.any { it == "+" } -> Operation.ADD
                            else -> Operation.MULTIPLY
                        }
                    current = if (operation == Operation.SQUARE) {
                        current.copy(operation = operation)

                    } else {
                        val operand = parts.last().toLong()
                        current.copy(operation = operation, operand = operand)
                    }
                }
                parts.first() == "Test:" -> {
                    current = current.copy(divisibleBy = parts.last().toLong())
                }
                parts[1] == "true:" -> {
                    current = current.copy(onTrue = parts.last().toInt())
                }
                parts[1] == "false:" -> {
                    current = current.copy(onFalse = parts.last().toInt())
                    result.add(current)
                    current = Monkey()
                }
                else -> {}
            }
        }
        return result
    }

    fun doRound(monkeys: List<Monkey>, handleWorry: Long.() -> Long) {
        monkeys.forEach { monkey ->
            while (monkey.items.isNotEmpty()) {
                var item = monkey.items.removeFirst()
                when (monkey.operation) {
                    Operation.ADD -> item += monkey.operand
                    Operation.MULTIPLY -> item *= monkey.operand
                    Operation.SQUARE -> item *= item
                }
                item = item.handleWorry()
                val moveTo =
                    if (item % monkey.divisibleBy == 0L) monkey.onTrue else monkey.onFalse
                monkeys[moveTo].items.add(item)
                monkey.inspections += 1
            }
        }
    }

    fun monkeyBusiness(monkeys: List<Monkey>, rounds: Int, advancedWorry: Boolean): Long {
        val globalModulo = monkeys.map { it.divisibleBy }.reduce { acc, item -> acc * item }
        val worryHandler: Long.() -> Long = if (advancedWorry) {
            { this % globalModulo }
        } else {
            { this / 3 }
        }

        for (round in 1..rounds) doRound(monkeys, worryHandler)

        return monkeys
            .map { it.inspections }
            .sortedDescending()
            .take(2)
            .reduce { acc, inspections -> inspections * acc }
    }

    val testInput = readTextInput("Day11_test")
    val input = readTextInput("Day11")

    check(monkeyBusiness(parseInput(testInput), 20, false) == 10605L)
    println(monkeyBusiness(parseInput(input), 20, false))

    check(monkeyBusiness(parseInput(testInput), 10000, true) == 2713310158)
    println(monkeyBusiness(parseInput(input), 10000, true))
}
