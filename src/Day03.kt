fun main() {

    val symbols = listOf('@', '#', '$', '%', '&', '*', '+', '-', '=', '/')
    fun part1(input: List<String>): Int {
        val sumList = mutableListOf<Int>()
        input.forEachIndexed { lineIndex, line ->
            var number = ""
            var saveNumber = false
            val previousLine = if (lineIndex != 0) input[lineIndex - 1] else null
            val nextLine = if (lineIndex < 139) input[lineIndex + 1] else null
            line.forEachIndexed { charIndex, char ->
                if ((!char.isDigit() && saveNumber)) {
                    sumList.add(number.toInt())
                }
                if (char.isDigit()) {
                    number += char
                    if (charIndex > 0) {
                        if (previousLine?.get(charIndex - 1) in symbols || line[charIndex - 1] in symbols || nextLine?.get(charIndex - 1) in symbols) saveNumber = true
                    }
                    if (previousLine?.get(charIndex) in symbols || nextLine?.get(charIndex) in symbols) saveNumber = true
                    if (charIndex < 139) {
                        if ((previousLine?.get(charIndex + 1) in symbols || line[charIndex + 1] in symbols || nextLine?.get(charIndex + 1) in symbols)) saveNumber = true
                    }
                } else {
                    number = ""
                    saveNumber = false
                }
                if (saveNumber && charIndex == 139) {
                    sumList.add(number.toInt())
                }
            }
        }
        return sumList.sum()
    }


    val symbol = listOf('*')
    fun part2(input: List<String>): Int {
        val sumList = mutableMapOf<Pair<Int, Int>, String>()
        val starList = mutableListOf<Pair<Int, Int>>()
        input.forEachIndexed { lineIndex, line ->
            var number = ""
            var numberIndex = 0
            var saveNumber = false
            val previousLine = if (lineIndex != 0) input[lineIndex - 1] else null
            val nextLine = if (lineIndex < 139) input[lineIndex + 1] else null
            line.forEachIndexed { charIndex, char ->
                if (!char.isDigit() && saveNumber) {
                    sumList[lineIndex to numberIndex] = number
                }
                if (char.isDigit()) {
                    if (number.isEmpty()) numberIndex = charIndex
                    number += char
                    if (charIndex > 0) {
                        if (previousLine?.get(charIndex - 1) in symbol || line[charIndex - 1] in symbol || nextLine?.get(charIndex - 1) in symbol) saveNumber = true
                    }
                    if (previousLine?.get(charIndex) in symbol || nextLine?.get(charIndex) in symbol) saveNumber = true
                    if (charIndex < 139) {
                        if ((previousLine?.get(charIndex + 1) in symbol || line[charIndex + 1] in symbol || nextLine?.get(charIndex + 1) in symbol)) saveNumber = true
                    }
                } else {
                    number = ""
                    saveNumber = false
                }
                if (saveNumber && charIndex == 139) {
                    sumList[lineIndex to numberIndex] = number
                }
                if (char == '*') starList.add(lineIndex to charIndex)
            }
        }


        var sum = 0
        starList.forEach { star ->
            val correspondingNumbers = sumList.filter { map ->
                val cords = map.key
                val rows = listOf(cords.first, cords.first - 1, cords.first + 1)

                val columns = mutableListOf(cords.second, cords.second - 1)
                for (i in -1..map.value.length) {
                    columns += cords.second + i
                }
                star.first in rows && star.second in columns
            }
            if (correspondingNumbers.size >= 2) {
                correspondingNumbers.forEach {
                    sumList.remove(it.key)
                }
                sum += correspondingNumbers.values.map { it.toInt() }.reduce(Int::times)
            }
        }


        return sum
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()

}