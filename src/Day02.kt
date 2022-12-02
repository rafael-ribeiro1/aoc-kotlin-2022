fun main() {
    fun part1(input: List<String>): Int {
        return input.gameRounds { GameRound.StrategyGuide1(it[0], it[1]) }
            .fold(0) { total, item -> total + item.calculateScore() }
    }

    fun part2(input: List<String>): Int {
        return input.gameRounds { GameRound.StrategyGuide2(it[0], it[1]) }
            .fold(0) { total, item -> total + item.calculateScore() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

fun List<String>.gameRounds(
    toGameRound: (List<String>) -> GameRound
): List<GameRound> = this.map { toGameRound(it.split(' ')) }

sealed class GameRound(
    protected val opponentMove: String,
    protected val player: String
) {
    abstract fun calculateScore(): Int

    class StrategyGuide1(opponentMove: String, player: String) : GameRound(opponentMove, player) {
        override fun calculateScore(): Int {
            return when (player) {
                "X" -> calculateMovePoints(moveScore = 1, beats = "C", drawsWith = "A")
                "Y" -> calculateMovePoints(moveScore = 2, beats = "A", drawsWith = "B")
                "Z" -> calculateMovePoints(moveScore = 3, beats = "B", drawsWith = "C")
                else -> 0
            }
        }

        private fun calculateMovePoints(moveScore: Int, beats: String, drawsWith: String): Int {
            return moveScore + when (opponentMove) {
                drawsWith -> 3
                beats -> 6
                else -> 0
            }
        }
    }

    class StrategyGuide2(opponentMove: String, player: String) : GameRound(opponentMove, player) {
        override fun calculateScore(): Int {
            return when (player) {
                "X" -> calculateMovePoints(moveScore = 0, vsRock = 3, vsPaper = 1, vsScissors = 2)
                "Y" -> calculateMovePoints(moveScore = 3, vsRock = 1, vsPaper = 2, vsScissors = 3)
                "Z" -> calculateMovePoints(moveScore = 6, vsRock = 2, vsPaper = 3, vsScissors = 1)
                else -> 0
            }
        }

        private fun calculateMovePoints(moveScore: Int, vsRock: Int, vsPaper: Int, vsScissors: Int): Int {
            return moveScore + when (opponentMove) {
                "A" -> vsRock
                "B" -> vsPaper
                "C" -> vsScissors
                else -> 0
            }
        }
    }
}
