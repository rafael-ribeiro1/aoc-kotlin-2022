fun main() {
    fun part1(input: String): Int {
        return input.detectMarkerFinalPosition(4)
    }

    fun part2(input: String): Int {
        return input.detectMarkerFinalPosition(14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")[0]
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")[0]
    println(part1(input))
    println(part2(input))
}

fun String.detectMarkerFinalPosition(size: Int): Int {
    return 1 + this.withIndex()
        .filter { it.index >= size }
        .first {
            val marker = this.substring(it.index - size + 1, it.index + 1)
            marker.length == marker.toSet().size
        }.index
}
