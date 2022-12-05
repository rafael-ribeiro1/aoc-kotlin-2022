import java.util.*

fun main() {
    fun part1(input: List<String>): String {
        val stacks = input.getStacks()
        input.getRearrangementProcedures().forEach {
            for (i in 1..it.quantity) {
                stacks[it.to].push(stacks[it.from].pop())
            }
        }
        return stacks.message
    }

    fun part2(input: List<String>): String {
        val stacks = input.getStacks()
        input.getRearrangementProcedures().forEach {
            val temp = Stack<Char>()
            for (i in 1..it.quantity) {
                temp.push(stacks[it.from].pop())
            }
            for (i in 1..it.quantity) {
                stacks[it.to].push(temp.pop())
            }
        }
        return stacks.message
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun List<String>.getStacks(): List<Stack<Char>> {
    val stacks = mutableListOf<Stack<Char>>()
    for (i in 1..(this[indexOf("") - 1].length / 4 + 1)) {
        stacks.add(Stack())
    }
    this.subList(0, indexOf("") - 1)
        .reversed()
        .forEach {
            for (i in 1..it.length step 4) {
                if (it[i].isLetter()) {
                    stacks[i/4].push(it[i])
                }
            }
        }
    return stacks
}

val List<Stack<Char>>.message: String
    get() = this.fold("") { msg, stack -> "$msg${stack.peek()}" }

fun List<String>.getRearrangementProcedures(): List<Procedure> {
    return this.subList(indexOf("") + 1, size)
        .map { Procedure.fromString(it) }
}

class Procedure(
    val quantity: Int,
    val from: Int,
    val to: Int
) {

    companion object {
        fun fromString(procedure: String): Procedure {
            return procedure.split(" ").let {
                Procedure(it[1].toInt(), it[3].toInt() - 1, it[5].toInt() - 1)
            }
        }
    }

}
