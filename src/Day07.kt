sealed class FileSystemNode() {
    data class File(val name: String, val size: Int) : FileSystemNode()
    data class Directory(
        val name: String,
        val items: MutableList<FileSystemNode> = mutableListOf()
    ) : FileSystemNode() {
        fun size(): Int {
            return items.sumOf { item ->
                when (item) {
                    is Directory -> item.size()
                    is File -> item.size
                }
            }
        }
    }
}

class Parser(val input: List<String>) {

    private var currentIndex = 2
    private val root = FileSystemNode.Directory("/")

    fun parseStructure(): FileSystemNode {
        parseNext(root)
        return root
    }

    private fun parseNext(directory: FileSystemNode.Directory) {
        while (currentIndex < input.size) {
            val command = input[currentIndex]
            val parts = command.split(" ")

            when {
                command == "$ cd .." -> return
                command.startsWith("$ cd") -> {
                    directory.items.firstOrNull {
                        it is FileSystemNode.Directory && it.name == parts[2]
                    }?.let {
                        currentIndex += 1
                        parseNext(it as FileSystemNode.Directory)
                    }
                }
                command.startsWith("$") -> {}
                command.isBlank() -> {}
                parts[0] == "dir" -> directory.items.add(FileSystemNode.Directory(parts[1]))
                else -> directory.items.add(FileSystemNode.File(parts[1], parts[0].toInt()))
            }
            currentIndex += 1
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val filesystem = Parser(input).parseStructure()

        return filesystem.accumulateDirs(acc = 0) { item, acc ->
            val size = item.size()
            if (size <= 100000) size else 0
        }
    }

    fun part2(input: List<String>): Int {
        val filesystem: FileSystemNode.Directory = Parser(input).parseStructure() as FileSystemNode.Directory

        val totalUsed =  filesystem.size()
        val toBeFreed = totalUsed - 40000000

        var currentDirSize = totalUsed

        filesystem.accumulateDirs(acc = 0) { item, _ ->
            val size = item.size()
             if(size in toBeFreed until currentDirSize) {
                currentDirSize = size
            }
            0
        }

        return currentDirSize
    }

    val testInput = readTextInput("Day07_test")
    val input = readTextInput("Day07")

    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    println(part1(input))
    println(part2(input))
}

private fun FileSystemNode.accumulateDirs(
    acc: Int,
    onItem: (FileSystemNode.Directory, Int) -> Int
): Int {
    var current = acc
    if (this is FileSystemNode.Directory) {
        current += onItem(this, acc)
        this.items.forEach {
            current += it.accumulateDirs(0, onItem)
        }
    }
    return current
}
