fun main() {
    fun findNextNumber(list: List<Int>): Int {
        val nextList = list.windowed(2).map { it[1] - it[0] }
        return if (nextList.all { it == 0 }) {
            list.last()
        } else list.last() + findNextNumber(nextList)
    }

    fun findNextNumberReversed(list: List<Int>): Int {
        val nextList = list.windowed(2).map { it[1] - it[0] }
        return if (nextList.all { it == 0 }) {
            list.first()
        } else list.reversed().last() - findNextNumberReversed(nextList)
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val numbers = line.split(" ").map { it.toInt() }
            findNextNumber(numbers)
        }
    }


    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val numbers = line.split(" ").map { it.toInt() }
            findNextNumberReversed(numbers)
        }
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}