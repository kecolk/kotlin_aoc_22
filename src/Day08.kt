fun main() {

    data class Tree(val height: Int, var seen: Boolean = false, var scenic: Int = 0) {
        fun getCount(): Int = if (seen) 1 else 0
    }

    fun parseInput(input: List<String>): Array<Array<Tree>> = input.map {
        it.toList().map { tree -> Tree(tree.toString().toInt()) }.toTypedArray()
    }.toTypedArray()

    fun part1(forest: Array<Array<Tree>>): Int {
        val highestTop = MutableList(forest.size) { -1 }
        val highestBottom = MutableList(forest.size) { -1 }
        forest.forEach { item ->
            var highestLeft = -1
            var highestRight = -1
            item.forEachIndexed { columnIndex, tree ->
                if (tree.height > highestLeft) {
                    tree.seen = true
                    highestLeft = tree.height
                }
                if (tree.height > highestTop[columnIndex]) {
                    tree.seen = true
                    highestTop[columnIndex] = tree.height
                }
            }
            item.reversed().forEach {
                if (it.height > highestRight) {
                    it.seen = true
                    highestRight = it.height
                }
            }
        }
        forest.reversed().forEach { item ->
            item.forEachIndexed { columnIndex, tree ->
                if (tree.height > highestBottom[columnIndex]) {
                    tree.seen = true
                    highestBottom[columnIndex] = tree.height
                }
            }
        }

        return forest.sumOf { row -> row.sumOf { tree -> tree.getCount() } }
    }

    fun part2(input: Array<Array<Tree>>): Int {

        for (row: Int in 0 until input.size) {
            for (column: Int in 0 until input.first().size) {
                val treeHeight = input[row][column].height
                var currentTop = row
                var currentBottom = input.size - 1 - row
                var currentLeft = column
                var currentRight = input.size - 1 - column

                for (searchRow: Int in 0 until input.size) {
                    if (searchRow < row && input[searchRow][column].height >= treeHeight) {
                        currentTop = row - searchRow
                    }
                    if (searchRow > row && input[searchRow][column].height >= treeHeight && searchRow - row < currentBottom) {
                        currentBottom = searchRow - row
                    }
                }

                for (searchColumn: Int in 0 until input.first().size) {
                    if (searchColumn < column && input[row][searchColumn].height >= treeHeight) {
                        currentLeft = column - searchColumn
                    }
                    if (searchColumn > column && input[row][searchColumn].height >= treeHeight && searchColumn - column < currentRight) {
                        currentRight = searchColumn - column
                    }
                }

                input[row][column].scenic = currentTop * currentBottom * currentRight * currentLeft
            }
        }

        return input.maxOf { row -> row.maxOf { tree -> tree.scenic } }
    }

    val testForest = parseInput(readTextInput("Day08_test"))
    val forest = parseInput(readTextInput("Day08"))

    check(part1(testForest) == 21)
    check(part2(testForest) == 8)

    println(part1(forest))
    println(part2(forest))
}
