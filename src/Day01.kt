fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = line.first {
                it.isDigit()
            }
            val lastDigit = line.last {
                it.isDigit()
            }
            "$firstDigit$lastDigit".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val list = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        return input.sumOf { line ->
            var lowestIndex = 1234567
            var firstTextValue = 0
            var highestIndex = -1
            var lastTextValue = 0
            val firstDigitIndex = line.indexOfFirst { it.isDigit() }
            val firstDigit = line.first { it.isDigit() }
            val lastDigitIndex = line.indexOfLast { it.isDigit() }
            val lastDigit = line.last { it.isDigit() }
            list.forEachIndexed { index, s ->
                if (line.contains(s)) {
                    val numIndex = line.indexOf(s)
                    if (numIndex < lowestIndex) {
                        lowestIndex = numIndex
                        firstTextValue = index + 1
                    }
                    val lastNumIndex = line.lastIndexOf(s)
                    if (lastNumIndex > highestIndex) {
                        highestIndex = lastNumIndex
                        lastTextValue = index + 1
                    }
                }
            }
            val firstValue = if (lowestIndex < firstDigitIndex) firstTextValue else firstDigit
            val lastValue = if (highestIndex > lastDigitIndex) lastTextValue else lastDigit
            "$firstValue$lastValue".toInt()
        }
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}