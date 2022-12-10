import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return input.toCycles()
            .filter { it.cycle in 20..220 && it.cycle % 40 == 20 }
            .sumOf { it.cycle * it.x }
    }

    fun part2(input: List<String>): String {
        var crtPos = 0
        var prevPos = 1
        return input.toCycles().joinToString("") {
            val rowEnd = crtPos == 39
            val pixel = (if (isLitPixel(prevPos, crtPos)) "#" else ".") + (if (rowEnd) "\n" else "")
            crtPos = if (rowEnd) 0 else crtPos + 1
            prevPos = it.x
            pixel
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    println(part2(testInput))

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

fun List<String>.toCycles(): List<Cycle> {
    val cycles = mutableListOf<Cycle>()
    var cycle = 1
    var x = 1
    this.forEach { input ->
        val split = input.split(" ")
        if (split.size == 1) {
            cycles.add(Cycle(++cycle, x))
        } else {
            cycles.add(Cycle(++cycle, x))
            val value = split[1].toInt()
            x += value
            cycles.add(Cycle(++cycle, x))
        }
    }
    return cycles
}

fun isLitPixel(spritePos: Int, crtPos: Int): Boolean = abs(spritePos - crtPos) <= 1

data class Cycle(val cycle: Int, val x: Int)
