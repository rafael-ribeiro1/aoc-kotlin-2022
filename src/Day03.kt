fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.toList().windowed(it.length / 2, it.length / 2) }
            .sumOf { it[0].intersect(it[1]).sumOf { letter -> letter.priority } }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toList() }
            .windowed(3, 3)
            .sumOf { it[0].intersect(it[1]).intersect(it[2]).sumOf { letter -> letter.priority } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

val Char.priority: Int
    get() = LETTERS.indexOf(this) + 1

val LETTERS = (('a'..'z')+('A'..'Z'))
