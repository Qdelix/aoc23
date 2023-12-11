import kotlin.math.abs

fun main() {
    fun getPair(a: Int, b: Int): Pair<Int, Int> {
        return if (a > b) Pair(b, a) else Pair(a, b)
    }

    fun part1(input: List<String>): Int {
        val expansionRowList = mutableListOf<String>()
        input.forEach { line ->
            if (line.all { it == '.' }) {
                expansionRowList.add(line)
            }
            expansionRowList.add(line)
        }

        val expansionList = emptyList<MutableList<String>>().toMutableList()
        repeat(expansionRowList.size) { expansionList.add(mutableListOf()) }

        var step = 0
        expansionRowList.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { index, c ->
                if (c == '.' && expansionRowList.all { it[index] == '.' }) {
                    expansionList[lineIndex].add(c.toString())
                }
                if (c == '#') {
                    step++
                    expansionList[lineIndex].add(step.toString())
                } else expansionList[lineIndex].add(c.toString())
            }
        }

        expansionList.forEach {
            it.println()
        }

        val results: MutableMap<Pair<Int, Int>, Int> = emptyMap<Pair<Int, Int>, Int>().toMutableMap()
        for (i in 1..step) {
            val lineIndex = expansionList.indexOfFirst { it.contains(i.toString()) }
            val sIndex = expansionList[lineIndex].indexOf(i.toString())
            for (j in 2..step) {
                val pair = getPair(i, j)
                if (!results.contains(pair) && i != j) {
                    val lineIndex2 = expansionList.indexOfFirst { it.contains(j.toString()) }
                    val sIndex2 = expansionList[lineIndex2].indexOf(j.toString())
                    val distance = abs(lineIndex - lineIndex2) + abs(sIndex - sIndex2)
                    results[pair] = distance
                }
            }
        }

        return results.values.sum()
    }


    fun part2(input: List<String>): Long {
        val expansionRowList = mutableListOf<String>()
        val rows = emptyList<Int>().toMutableList()
        val cols = emptyList<Int>().toMutableList()
        input.forEachIndexed { index, line ->
            if (line.all { it == '.' }) {
                rows.add(index)
            }
            expansionRowList.add(line)
        }

        val expansionList = emptyList<MutableList<String>>().toMutableList()
        repeat(expansionRowList.size) { expansionList.add(mutableListOf()) }

        var step = 0
        expansionRowList.first().forEachIndexed { index, c ->
            if (c == '.' && expansionRowList.all { it[index] == '.' }) {
                cols.add(index)
            }
        }

        expansionRowList.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { index, c ->
                if (c == '#') {
                    step++
                    expansionList[lineIndex].add(step.toString())
                } else expansionList[lineIndex].add(c.toString())
            }
        }

        expansionList.forEach {
            it.println()
        }

        val results: MutableMap<Pair<Int, Int>, Long> = emptyMap<Pair<Int, Int>, Long>().toMutableMap()
        for (i in 1..step) {
            val lineIndex = expansionList.indexOfFirst { it.contains(i.toString()) }
            val sIndex = expansionList[lineIndex].indexOf(i.toString())
            for (j in 2..step) {
                val pair = getPair(i, j)
                if (!results.contains(pair) && i != j) {
                    val lineIndex2 = expansionList.indexOfFirst { it.contains(j.toString()) }
                    val sIndex2 = expansionList[lineIndex2].indexOf(j.toString())
                    var distance = (abs(lineIndex - lineIndex2) + abs(sIndex - sIndex2)).toLong()
                    rows.forEach { row ->
                        val linesPair = getPair(lineIndex, lineIndex2)
                        if (row > linesPair.first && row < linesPair.second) distance += 999999
                    }
                    cols.forEach { col ->
                        val colsPair = getPair(sIndex, sIndex2)
                        if (col > colsPair.first && col < colsPair.second) distance += 999999
                    }
                    results[pair] = distance
                }
            }
        }

        return results.values.sum()
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}