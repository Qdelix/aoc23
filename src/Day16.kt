fun main() {

    fun part1(input: List<String>): Int {
        val mutable: MutableList<MutableList<MutableSet<Move>>> = emptyList<MutableList<MutableSet<Move>>>().toMutableList()
        input.forEachIndexed { i, s ->
            mutable.add(emptyList<MutableSet<Move>>().toMutableList())
            s.forEach { _ ->
                mutable[i].add(setOf<Move>().toMutableSet())
            }
        }
        goNext(0, 0, mutable, Move.E, input)
        var sum = 0
        mutable.forEach { sum += it.count { it.isNotEmpty() } }
        return sum
    }

    fun part2(input: List<String>): Int {
        var highestSum = 0
        input.first().forEachIndexed { index, c ->
            val mutable: MutableList<MutableList<MutableSet<Move>>> = emptyList<MutableList<MutableSet<Move>>>().toMutableList()
            input.forEachIndexed { i, s ->
                mutable.add(emptyList<MutableSet<Move>>().toMutableList())
                s.forEach { _ ->
                    mutable[i].add(setOf<Move>().toMutableSet())
                }
            }
            goNext(0, index, mutable, Move.S, input)
            var sum = 0
            mutable.forEach { sum += it.count { it.isNotEmpty() } }
            if (sum > highestSum) highestSum = sum
        }
        input.forEachIndexed { index, c ->
            val mutable: MutableList<MutableList<MutableSet<Move>>> = emptyList<MutableList<MutableSet<Move>>>().toMutableList()
            input.forEachIndexed { i, s ->
                mutable.add(emptyList<MutableSet<Move>>().toMutableList())
                s.forEach { _ ->
                    mutable[i].add(setOf<Move>().toMutableSet())
                }
            }
            goNext(index, 0, mutable, Move.E, input)
            var sum = 0
            mutable.forEach { sum += it.count { it.isNotEmpty() } }
            if (sum > highestSum) highestSum = sum
        }
        input.first().forEachIndexed { index, c ->
            val mutable: MutableList<MutableList<MutableSet<Move>>> = emptyList<MutableList<MutableSet<Move>>>().toMutableList()
            input.forEachIndexed { i, s ->
                mutable.add(emptyList<MutableSet<Move>>().toMutableList())
                s.forEach { _ ->
                    mutable[i].add(setOf<Move>().toMutableSet())
                }
            }
            goNext(input.size - 1, index, mutable, Move.N, input)
            var sum = 0
            mutable.forEach { sum += it.count { it.isNotEmpty() } }
            if (sum > highestSum) highestSum = sum
        }
        input.forEachIndexed { index, c ->
            val mutable: MutableList<MutableList<MutableSet<Move>>> = emptyList<MutableList<MutableSet<Move>>>().toMutableList()
            input.forEachIndexed { i, s ->
                mutable.add(emptyList<MutableSet<Move>>().toMutableList())
                s.forEach { _ ->
                    mutable[i].add(setOf<Move>().toMutableSet())
                }
            }
            goNext(index, input.first().count() - 1, mutable, Move.W, input)
            var sum = 0
            mutable.forEach { sum += it.count { it.isNotEmpty() } }
            if (sum > highestSum) highestSum = sum
        }

        return highestSum
    }

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}

fun goNext(rowIndex: Int, colIndex: Int, mutable: MutableList<MutableList<MutableSet<Move>>>, move: Move, input: List<String>) {
    if (rowIndex >= 0 && colIndex >= 0 && rowIndex < input.size && colIndex < input.first().count() && !mutable[rowIndex][colIndex].contains(move)) {
        val s = input[rowIndex][colIndex]
        mutable[rowIndex][colIndex].add(move)
        when {
            move == Move.E && s == '/' -> {
                goNext(rowIndex - 1, colIndex, mutable, Move.N, input)
            }

            move == Move.E && s == '\\' -> {
                goNext(rowIndex + 1, colIndex, mutable, Move.S, input)
            }

            move == Move.W && s == '/' -> {
                goNext(rowIndex + 1, colIndex, mutable, Move.S, input)
            }

            move == Move.W && s == '\\' -> {
                goNext(rowIndex - 1, colIndex, mutable, Move.N, input)
            }

            move == Move.N && s == '/' -> {
                goNext(rowIndex, colIndex + 1, mutable, Move.E, input)
            }

            move == Move.N && s == '\\' -> {
                goNext(rowIndex, colIndex - 1, mutable, Move.W, input)
            }

            move == Move.S && s == '/' -> {
                goNext(rowIndex, colIndex - 1, mutable, Move.W, input)
            }

            move == Move.S && s == '\\' -> {
                goNext(rowIndex, colIndex + 1, mutable, Move.E, input)
            }

            (move == Move.E || move == Move.W) && s == '|' -> {
                goNext(rowIndex - 1, colIndex, mutable, Move.N, input)
                goNext(rowIndex + 1, colIndex, mutable, Move.S, input)
            }

            (move == Move.N || move == Move.S) && s == '-' -> {
                goNext(rowIndex, colIndex - 1, mutable, Move.W, input)
                goNext(rowIndex, colIndex + 1, mutable, Move.E, input)
            }

            else -> {
                when (move) {
                    Move.W -> goNext(rowIndex, colIndex - 1, mutable, move, input)
                    Move.E -> goNext(rowIndex, colIndex + 1, mutable, move, input)
                    Move.N -> goNext(rowIndex - 1, colIndex, mutable, move, input)
                    Move.S -> goNext(rowIndex + 1, colIndex, mutable, move, input)
                }
            }
        }
    }
}

enum class Move {
    W, N, E, S
}