import java.util.*

fun main() {

    fun part1(input: List<String>): Int {
        val mutable = input.map { it.toMutableList() }.toMutableList()

        var noMoves = false
        var countMoves = 0
        while (!noMoves) {
            mutable.forEachIndexed { rowIndex, row ->
                if (rowIndex == 0) countMoves = 0
                row.forEachIndexed { colIndex, col ->
                    if (rowIndex > 0 && col == 'O') {
                        if (mutable[rowIndex - 1][colIndex] == '.') {
                            mutable[rowIndex - 1][colIndex] = 'O'
                            mutable[rowIndex][colIndex] = '.'
                            countMoves++
                        }
                    }
                }
            }
            if (countMoves == 0) {
                noMoves = true
            }
        }
        return mutable.mapIndexed { index, line -> line.count { it == 'O' } * (mutable.size - index) }.sum()
    }

    fun part2(input: List<String>): Int {
        var mutable = input.map { it.toMutableList() }.toMutableList()
        val grides: MutableList<List<List<Char>>> = emptyList<List<List<Char>>>().toMutableList()
        var countToResolve = 0
        while(countToResolve == 0) {
            var noMovesN = false
            var countMovesN = 0
            while (!noMovesN) {
                mutable.forEachIndexed { rowIndex, row ->
                    if (rowIndex == 0) countMovesN = 0
                    row.forEachIndexed { colIndex, col ->
                        if (rowIndex > 0 && col == 'O') {
                            if (mutable[rowIndex - 1][colIndex] == '.') {
                                mutable[rowIndex - 1][colIndex] = 'O'
                                mutable[rowIndex][colIndex] = '.'
                                countMovesN++
                            }
                        }
                    }
                }
                if (countMovesN == 0) {
                    noMovesN = true
                }
            }
            var noMovesW = false
            var countMovesW = 0
            while (!noMovesW) {
                mutable.forEachIndexed { rowIndex, row ->
                    if (rowIndex == 0) countMovesW = 0
                    row.forEachIndexed { colIndex, col ->
                        if (colIndex > 0 && col == 'O') {
                            if (mutable[rowIndex][colIndex - 1] == '.') {
                                mutable[rowIndex][colIndex - 1] = 'O'
                                mutable[rowIndex][colIndex] = '.'
                                countMovesW++
                            }
                        }
                    }
                }
                if (countMovesW == 0) {
                    noMovesW = true
                }
            }
            var noMovesS = false
            var countMovesS = 0
            while (!noMovesS) {
                mutable.forEachIndexed { rowIndex, row ->
                    if (rowIndex == 0) countMovesS = 0
                    row.forEachIndexed { colIndex, col ->
                        if (rowIndex < mutable.size - 1 && col == 'O') {
                            if (mutable[rowIndex + 1][colIndex] == '.') {
                                mutable[rowIndex + 1][colIndex] = 'O'
                                mutable[rowIndex][colIndex] = '.'
                                countMovesS++
                            }
                        }
                    }
                }
                if (countMovesS == 0) {
                    noMovesS = true
                }
            }
            var noMovesE = false
            var countMovesE = 0
            while (!noMovesE) {
                mutable.forEachIndexed { rowIndex, row ->
                    if (rowIndex == 0) countMovesE = 0
                    row.forEachIndexed { colIndex, col ->
                        if (colIndex < row.size - 1 && col == 'O') {
                            if (mutable[rowIndex][colIndex + 1] == '.') {
                                mutable[rowIndex][colIndex + 1] = 'O'
                                mutable[rowIndex][colIndex] = '.'
                                countMovesE++
                            }
                        }
                    }
                }
                if (countMovesE == 0) {
                    noMovesE = true
                }
            }
            grides.add(mutable.map { line -> line.toList() }.toList())

            for (item in grides.distinct()) {
                if (Collections.frequency(grides, item) > 1) {
                    val firstIndex = grides.indexOfFirst { it == item }
                    val repeatIndex = grides.indexOfLast { it == item }
                    countToResolve = firstIndex - 1 + (1000000000 % (repeatIndex - firstIndex))
                }
            }
        }

        mutable = input.map { it.toMutableList() }.toMutableList()
        repeat(countToResolve + 28) {
            var noMovesN = false
            var countMovesN = 0
            while (!noMovesN) {
                mutable.forEachIndexed { rowIndex, row ->
                    if (rowIndex == 0) countMovesN = 0
                    row.forEachIndexed { colIndex, col ->
                        if (rowIndex > 0 && col == 'O') {
                            if (mutable[rowIndex - 1][colIndex] == '.') {
                                mutable[rowIndex - 1][colIndex] = 'O'
                                mutable[rowIndex][colIndex] = '.'
                                countMovesN++
                            }
                        }
                    }
                }
                if (countMovesN == 0) {
                    noMovesN = true
                }
            }
            var noMovesW = false
            var countMovesW = 0
            while (!noMovesW) {
                mutable.forEachIndexed { rowIndex, row ->
                    if (rowIndex == 0) countMovesW = 0
                    row.forEachIndexed { colIndex, col ->
                        if (colIndex > 0 && col == 'O') {
                            if (mutable[rowIndex][colIndex - 1] == '.') {
                                mutable[rowIndex][colIndex - 1] = 'O'
                                mutable[rowIndex][colIndex] = '.'
                                countMovesW++
                            }
                        }
                    }
                }
                if (countMovesW == 0) {
                    noMovesW = true
                }
            }
            var noMovesS = false
            var countMovesS = 0
            while (!noMovesS) {
                mutable.forEachIndexed { rowIndex, row ->
                    if (rowIndex == 0) countMovesS = 0
                    row.forEachIndexed { colIndex, col ->
                        if (rowIndex < mutable.size - 1 && col == 'O') {
                            if (mutable[rowIndex + 1][colIndex] == '.') {
                                mutable[rowIndex + 1][colIndex] = 'O'
                                mutable[rowIndex][colIndex] = '.'
                                countMovesS++
                            }
                        }
                    }
                }
                if (countMovesS == 0) {
                    noMovesS = true
                }
            }
            var noMovesE = false
            var countMovesE = 0
            while (!noMovesE) {
                mutable.forEachIndexed { rowIndex, row ->
                    if (rowIndex == 0) countMovesE = 0
                    row.forEachIndexed { colIndex, col ->
                        if (colIndex < row.size - 1 && col == 'O') {
                            if (mutable[rowIndex][colIndex + 1] == '.') {
                                mutable[rowIndex][colIndex + 1] = 'O'
                                mutable[rowIndex][colIndex] = '.'
                                countMovesE++
                            }
                        }
                    }
                }
                if (countMovesE == 0) {
                    noMovesE = true
                }
            }
        }
        return mutable.mapIndexed { index, line -> line.count { it == 'O' } * (mutable.size - index) }.sum()
    }

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}