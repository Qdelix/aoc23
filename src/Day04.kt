import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            val allNumbers = line.substringAfter(":").split("|")
            val winningNumbers = allNumbers[0].split(" ").map { it.trim() }.filter { it.isNotEmpty() }
            val cardNumbers = allNumbers[1].split(" ").map { it.trim() }.filter { it.isNotEmpty() }
            val listSize = winningNumbers.intersect(cardNumbers).size
            sum += 2f.pow(listSize - 1).toInt()
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val sumList = input.map { 1 }.toMutableList()
        input.forEachIndexed { lineIndex, line ->
            val allNumbers = line.substringAfter(":").split("|")
            val winningNumbers = allNumbers[0].split(" ").map { it.trim() }.filter { it.isNotEmpty() }
            val cardNumbers = allNumbers[1].split(" ").map { it.trim() }.filter { it.isNotEmpty() }
            val listSize = winningNumbers.intersect(cardNumbers).size
            for (i in 1..listSize) {
                sumList[lineIndex + i] += sumList[lineIndex]
            }
        }
        return sumList.sum()
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()

}