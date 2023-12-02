private const val RED = "red"
private const val GREEN = "green"
private const val BLUE = "blue"
fun main() {

    val maxRed = RED to 12
    val maxGreen = GREEN to 13
    val maxBlue = BLUE to 14

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val splitLine = line.split(": ", ", ", "; ")
            val isAnyOverLimit = splitLine.drop(1).any {
                val number = it.takeWhile { char -> char.isDigit() }.toInt()
                it.contains(maxRed.first) && number > maxRed.second ||
                        it.contains(maxGreen.first) && number > maxGreen.second ||
                        it.contains(maxBlue.first) && number > maxBlue.second
            }
            val gameNumber = splitLine.first().takeLastWhile { char -> char.isDigit() }.toInt()
            if (isAnyOverLimit) 0 else gameNumber
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val splitLine = line.split(": ", ", ", "; ").drop(1)
            val map = mutableMapOf(RED to 0, GREEN to 0, BLUE to 0)
            splitLine.forEach {
                val number = it.takeWhile { char -> char.isDigit() }.toInt()
                val color = it.removePrefix("$number ")
                val colorValue = map[color]
                if (colorValue != null && number > colorValue) {
                    map[color] = number
                }
            }
            val redValue = map[RED]
            val greenValue = map[GREEN]
            val blueValue = map[BLUE]
            if (redValue != null && greenValue != null && blueValue != null) {
                redValue * greenValue * blueValue
            } else 0
        }
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}