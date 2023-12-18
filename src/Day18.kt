import DigDir.*

fun main() {

    fun part1(input: List<String>): Int {
        val mapped = input.map { s ->
            val splited = s.split(" ")
            Day18(digDir = getDigDir(splited[0]), dist = splited[1].toInt(), color = splited[2])
        }
        val mutable: MutableList<MutableList<String>> = emptyList<MutableList<String>>().toMutableList()

        val downSum = 1000
        val rightSum = 1000
        var currentPoint = 500 to 500
        repeat(downSum) {
            mutable.add(mutableListOf<String>().apply {
                repeat(rightSum) {
                    add(".")
                }
            })
        }
        mutable[currentPoint.first][currentPoint.second] = mapped.last().digDir.short
        mapped.forEach { day18 ->
            repeat(day18.dist) {
                when (day18.digDir) {
                    RIGHT -> currentPoint = currentPoint.first to currentPoint.second + 1
                    LEFT -> currentPoint = currentPoint.first to currentPoint.second - 1
                    DOWN -> currentPoint = currentPoint.first + 1 to currentPoint.second
                    UP -> currentPoint = currentPoint.first - 1 to currentPoint.second
                }
                mutable[currentPoint.first][currentPoint.second] = day18.digDir.short
            }
        }

        var leftIndex = Int.MAX_VALUE
        var rightIndex = 0
        val topIndex = mutable.indexOfFirst { it.any { it != "." } }
        val bottomIndex = mutable.indexOfLast { it.any { it != "." } }
        mutable.forEach {
            val left = it.indexOfFirst { it != "." }
            if (left in 0..<leftIndex) {
                leftIndex = left
            }
            val right = it.indexOfLast { it != "." }
            if (right > rightIndex) rightIndex = right
        }

        repeat(mutable.size - bottomIndex - 1) {
            mutable.removeLast()
        }
        repeat(topIndex - 1) {
            mutable.removeFirst()
        }
        val rowSize = mutable.first().size
        mutable.forEach {list ->
            repeat(rowSize - rightIndex - 1) {
                list.removeLast()
            }
            repeat(leftIndex) {
                list.removeFirst()
            }
        }

        mutable.forEachIndexed { rowIndex, list ->
            list.forEachIndexed { colIndex, s ->
                val leftList = list.take(colIndex).filter { it in DigDir.entries.map { it.short } }
                val rightList = list.drop(colIndex).filter { it in DigDir.entries.map { it.short } }
                if ((s == "." && leftList.isNotEmpty() && rightList.isNotEmpty()) &&
                        (leftList.last() == RIGHT.short && rightList.first() == DOWN.short ||
                                leftList.last() == UP.short && rightList.first() == DOWN.short ||
                                leftList.last() == UP.short && rightList.first() == LEFT.short ||
                                leftList.dropLastWhile { it == RIGHT.short }.last() == UP.short && rightList.dropWhile { it == LEFT.short }.first() == DOWN.short
                                )) {
                    mutable[rowIndex][colIndex] = "#"
                }
            }
        }
        var sum = 0
        mutable.forEach { sum += it.count { it == "#" || it in DigDir.entries.map { it.short } } }
        mutable.forEach {
            it.joinToString(separator = "").println()
        }
        return sum
    }


    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day18")
    part1(input).println()
    part2(input).println()
}

data class Day18(val digDir: DigDir, val dist: Int, val color: String)

enum class DigDir(val short: String) {
    RIGHT("R"),
    LEFT("L"),
    DOWN("D"),
    UP("U"),
}

fun getDigDir(short: String): DigDir {
    return DigDir.entries.first { it.short == short }
}