fun main() {
    fun part1(input: List<String>): Long {
        return input.buildFilesystemTree()
            .directorySizes()
            .filter { it <= 100000 }
            .sum()
    }

    fun part2(input: List<String>): Long {
        val tree = input.buildFilesystemTree()
        val sizes = tree.directorySizes()
        return sizes.filter { it >= 30000000L - (70000000L - sizes.max()) }.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437L)
    check(part2(testInput) == 24933642L)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

fun List<String>.buildFilesystemTree(): TreeNode<FilesystemItem> {
    val root = FilesystemTreeNode(FilesystemItem.Directory("/"))
    var actual = root
    this.forEach { input ->
        val split = input.split(" ")
        when (split[0]) {
            "$" -> {
                if (split[1] == "cd") {
                    actual = when (split[2]) {
                        "/" -> root
                        ".." -> (actual.parent ?: root) as FilesystemTreeNode
                        else -> actual.getOrAdd(FilesystemItem.Directory(split[2]))
                    }
                }
            }
            "dir" -> { actual.getOrAdd(FilesystemItem.Directory(split[1])) }
            else -> { actual.getOrAdd(FilesystemItem.File(split[1], split[0].toLong())) }
        }
    }
    return root
}

fun TreeNode<FilesystemItem>.directorySizes(): List<Long> {
    return mutableListOf<Long>().also { this.sumDirectorySizes(it) }
}

fun TreeNode<FilesystemItem>.sumDirectorySizes(sizes: MutableList<Long>): Long {
    var sum = 0L

    this.children.forEach {
        sum += if (it.value is FilesystemItem.File) {
            it.value.size
        } else {
            it.sumDirectorySizes(sizes)
        }
    }

    return sum.also { sizes.add(it) }
}

sealed class FilesystemItem(val name: String) {
    class Directory(name: String) : FilesystemItem(name)
    class File(name: String, val size: Long) : FilesystemItem(name)
}

class FilesystemTreeNode(value: FilesystemItem) : TreeNode<FilesystemItem>(value) {

    fun getOrAdd(item: FilesystemItem): FilesystemTreeNode {
        return (children.firstOrNull { item.name == it.value.name }
            ?: FilesystemTreeNode(item).also { add(it) }) as FilesystemTreeNode
    }
}

open class TreeNode<T>(val value: T) {

    val children: MutableList<TreeNode<T>> = mutableListOf()

    private var _parent: TreeNode<T>? = null
    val parent: TreeNode<T>?
        get() = _parent

    fun add(node: TreeNode<T>) {
        children.add(node)
        node._parent = this
    }
}
