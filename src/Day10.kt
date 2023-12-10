import kotlin.math.round

fun main() {

    fun part1(input: List<String>): Int {
        var previousPair: Pair<Int, Int>
        var step = 0
        val lists: List<List<String>> = input.map { it.map { it.toString() }.toList() }.toList()
        val lineIndex = lists.indexOfFirst { it.contains("S") }
        val letterIndex = lists[lineIndex].indexOfFirst { it == "S" }
        previousPair = lineIndex to letterIndex

        var nextPoint: Pair<Int, Int> = when {
            lineIndex > 0 && lists[lineIndex - 1][letterIndex] in listOf("7", "F", "|") -> lineIndex - 1 to letterIndex
            letterIndex > 0 && lists[lineIndex][letterIndex - 1] in listOf("-", "F", "L") -> lineIndex to letterIndex - 1
            letterIndex > 0 && lists[lineIndex][letterIndex - 1] in listOf("-", "J", "7") -> lineIndex to letterIndex - 1
            lists[lineIndex + 1][letterIndex] in listOf("|", "J", "L") -> lineIndex + 1 to letterIndex
            else -> 0 to 0
        }

        var currentValue = ""
        var point: Pair<Int, Int> = nextPoint
        while (currentValue != "S") {
            currentValue = lists[point.first][point.second]
            nextPoint = when {
                currentValue == "7" && previousPair.first == point.first -> point.first + 1 to point.second
                currentValue == "7" && previousPair.second == point.second -> point.first to point.second - 1
                currentValue == "F" && previousPair.first == point.first -> point.first + 1 to point.second
                currentValue == "F" && previousPair.second == point.second -> point.first to point.second + 1
                currentValue == "|" && previousPair.first + 1 == point.first -> point.first + 1 to point.second
                currentValue == "|" && previousPair.first - 1 == point.first -> point.first - 1 to point.second
                currentValue == "-" && previousPair.second + 1 == point.second -> point.first to point.second + 1
                currentValue == "-" && previousPair.second - 1 == point.second -> point.first to point.second - 1
                currentValue == "L" && previousPair.first == point.first -> point.first - 1 to point.second
                currentValue == "L" && previousPair.second == point.second -> point.first to point.second + 1
                currentValue == "J" && previousPair.first == point.first -> point.first - 1 to point.second
                currentValue == "J" && previousPair.second == point.second -> point.first to point.second - 1
                else -> -1 to -1
            }
            if (nextPoint.first in 0..<140 && nextPoint.second in 0..<140) {
                previousPair = point
                point = nextPoint
                step++
            }
        }
        return round((step.toDouble() + 0.1) / 2).toInt()
    }

    // not working :(
    fun part2(input: List<String>): Int {
        val pairList: MutableList<Pair<Int, Int>> = mutableListOf()
        var step = 0
        val lists: List<List<String>> = input.map { it.map { it.toString() }.toList() }.toList()
        val lineIndex = lists.indexOfFirst { it.contains("S") }
        val letterIndex = lists[lineIndex].indexOfFirst { it == "S" }
        pairList.add(lineIndex to letterIndex)

        var nextPoint: Pair<Int, Int> = when {
            lineIndex > 0 && lists[lineIndex - 1][letterIndex] in listOf("7", "F", "|") -> lineIndex - 1 to letterIndex
            letterIndex > 0 && lists[lineIndex][letterIndex - 1] in listOf("-", "F", "L") -> lineIndex to letterIndex - 1
            letterIndex > 0 && lists[lineIndex][letterIndex - 1] in listOf("-", "J", "7") -> lineIndex to letterIndex - 1
            lists[lineIndex + 1][letterIndex] in listOf("|", "J", "L") -> lineIndex + 1 to letterIndex
            else -> 0 to 0
        }

        var currentValue = ""
        var point: Pair<Int, Int> = nextPoint
        while (currentValue != "S") {
            currentValue = lists[point.first][point.second]
            val previousPair = pairList.last()
            nextPoint = when {
                currentValue == "7" && previousPair.first == point.first -> point.first + 1 to point.second
                currentValue == "7" && previousPair.second == point.second -> point.first to point.second - 1
                currentValue == "F" && previousPair.first == point.first -> point.first + 1 to point.second
                currentValue == "F" && previousPair.second == point.second -> point.first to point.second + 1
                currentValue == "|" && previousPair.first + 1 == point.first -> point.first + 1 to point.second
                currentValue == "|" && previousPair.first - 1 == point.first -> point.first - 1 to point.second
                currentValue == "-" && previousPair.second + 1 == point.second -> point.first to point.second + 1
                currentValue == "-" && previousPair.second - 1 == point.second -> point.first to point.second - 1
                currentValue == "L" && previousPair.first == point.first -> point.first - 1 to point.second
                currentValue == "L" && previousPair.second == point.second -> point.first to point.second + 1
                currentValue == "J" && previousPair.first == point.first -> point.first - 1 to point.second
                currentValue == "J" && previousPair.second == point.second -> point.first to point.second - 1
                else -> -1 to -1
            }
            if (nextPoint.first in 0..<140 && nextPoint.second in 0..<140) {
                pairList.add(point)
                point = nextPoint
                step++
            }
        }



        for (i in input.indices) {
            pairList.filter { it.first == i }.sortedBy { it.second }
        }


        val result = mutableListOf(mutableListOf<Int>())

        result.removeAt(0)
        for (i in lists.indices) {
            result.add(IntArray(lists.first().size) { 0 }.toMutableList())
        }

        pairList.forEach {
            result[it.first][it.second] = 1
        }
        result.forEach { it.println() }

        return round((step.toDouble() + 0.1) / 2).toInt()
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}