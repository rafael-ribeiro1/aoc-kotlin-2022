fun main() {
    fun part1(input: List<String>): Int {
        return input.caloriesPerElf().max()
    }

    fun part2(input: List<String>): Int {
        return input.caloriesPerElf().sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

fun List<String>.caloriesPerElf(): List<Int> {
    val res = mutableListOf<List<Int>>()
    var prev = 0
    this.withIndex()
        .filter { it.value.isBlank() }
        .forEach { i ->
            res.add(this.subList(prev, i.index).map { it.toInt() })
            prev = i.index + 1
        }
    res.add(this.subList(prev, this.size).map { it.toInt() })
    return res.map { it.sum() }
}
