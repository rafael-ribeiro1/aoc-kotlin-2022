fun main() {
    fun part1(input: List<String>): Int {
        return input.toPairsWithRanges()
            .count { pair ->
                pair.first.intersect(pair.second).let {
                    it.size == pair.first.size || it.size == pair.second.size
                }
            }
    }

    fun part2(input: List<String>): Int {
        return input.toPairsWithRanges()
            .count { it.first.intersect(it.second).isNotEmpty() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

fun List<String>.toPairsWithRanges(): List<Pair<List<Int>, List<Int>>> {
    return this.map { input ->
        input.split(",").map { elf ->
            elf.split("-").map { it.toInt() }.let {
                (it[0]..it[1]).toList()
            }
        }.let { Pair(it[0], it[1]) }
    }
}
