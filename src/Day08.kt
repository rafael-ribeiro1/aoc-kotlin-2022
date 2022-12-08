fun main() {
    fun part1(input: List<String>): Int {
        return input.buildMatrix()
            .visibleTrees()
    }

    fun part2(input: List<String>): Int {
        return input.buildMatrix()
            .highestScenicScore()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

fun List<String>.buildMatrix(): Array<IntArray> {
    return Array(this.size) { IntArray(this[0].length) }.also {
        this.forEachIndexed { i, input ->
            it[i] = input.toList().map { it.digitToInt() }.toIntArray()
        }
    }
}

fun Array<IntArray>.visibleTrees(): Int {
    var visibleTrees = 0
    this.forEachIndexed { i, row ->
        row.forEachIndexed { j, _ ->
            if (this.isTreeVisible(i, j)) {
                visibleTrees++
            }
        }
    }
    return visibleTrees
}

fun Array<IntArray>.isTreeVisible(row: Int, column: Int): Boolean {
    if (row == 0 || column == 0 || row == this.size - 1 || column == this[0].size - 1) {
        return true
    }
    val treeHeight = this[row][column]
    // Left
    if (this[row].copyOfRange(0, column).all { it < treeHeight }) {
        return true
    }
    // Right
    if (this[row].copyOfRange(column + 1, this[row].size).all { it < treeHeight }) {
        return true
    }
    // Top
    if (this.copyOfRange(0, row).all { it[column] < treeHeight }) {
        return true
    }
    // Bottom
    if (this.copyOfRange(row + 1, this.size).all { it[column] < treeHeight }) {
        return true
    }
    return false
}

fun Array<IntArray>.highestScenicScore(): Int {
    return this.withIndex()
        .maxOf { row ->
            row.value.withIndex()
                .maxOf { column -> this.scenicScore(row.index, column.index) }
        }
}

fun Array<IntArray>.scenicScore(row: Int, column: Int): Int {
    if (row == 0 || column == 0 || row == this.size - 1 || column == this[0].size - 1) {
        return 0
    }
    val treeHeight = this[row][column]
    return this[row].copyOfRange(0, column).reversed().viewingDistance(treeHeight) * // Left
            this[row].copyOfRange(column + 1, this[row].size).toList().viewingDistance(treeHeight) * // Right
            this.copyOfRange(0, row).map { it[column] }.reversed().viewingDistance(treeHeight) * // Top
            this.copyOfRange(row + 1, this.size).map { it[column] }.viewingDistance(treeHeight) // Bottom
}

fun List<Int>.viewingDistance(treeHeight: Int): Int {
    return (this.indexOfFirst { it >= treeHeight }.takeIf { it != -1 } ?: (this.size - 1)) + 1
}
