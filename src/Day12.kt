import kotlin.math.pow

fun main() {

    fun binaryToDecimal(binary: String): Int {
        val reversedDigits = binary.reversed().toCharArray().map { it.digitToInt() }
        var decimalNumber = 0
        var i = 0

        for (n in reversedDigits) {
            decimalNumber += (n * 2.0.pow(i)).toInt()
            ++i
        }
        return decimalNumber
    }

    fun decimalToBinary(n: Int, count: Int): String {
        val intList = mutableListOf<Int>()
        var decimalNumber = n
        var result = ""
        var i = 0

        while (decimalNumber > 0) {
            intList.add(decimalNumber % 2)
            decimalNumber /= 2
        }

        var binText = intList.reversed().joinToString("")

        for (i in 1..count - binText.count()) {
            binText = "0$binText"
        }
        return binText
    }

    fun part1(input: List<String>): Int {
        var arrangements = 0
        input.forEach { line ->
            var maxBin = ""
            line.filter { it == '?' }.forEach {
                maxBin += "1"
            }
            val max = binaryToDecimal(maxBin)
            for (i in 0..max) {
                var bin = decimalToBinary(i, maxBin.count())
                var result = ""
                line.forEach { c ->
                    if (c == '?') {
                        when (bin.first()) {
                            '0' -> result += '.'
                            '1' -> result += '#'
                        }
                        bin = bin.drop(1)
                    } else {
                        result += c
                    }
                }
                var allValid = true
                val countResult = result.split(" ")[1].split(",")
                val hashs = result.split(" ").first().split(".").filter { it.isNotEmpty() }
                if (hashs.count() == countResult.count()) {
                    hashs.forEachIndexed { index, s ->
                        if (s.count() != countResult.getOrNull(index)?.toInt()) {
                            allValid = false
                        }
                    }
                } else allValid = false
                if (allValid) {
                    arrangements++
                }
            }
        }

        return arrangements
    }

    //not working :(
    fun part2(input: List<String>): Int {
        var arrangements = 0
        input.forEach { line ->

            var line2 = ""
            line.split(" ").forEach { text ->
                repeat(2) {
                    if (line2.isNotEmpty() && line2.last().isDigit()) line2 += ","
                    line2 += text
                }
                if (!line2.last().isDigit()) line2 += " "
            }
            line2 = line2.trim()

            var maxBin = ""
            line2.filter { it == '?' }.forEach {
                maxBin += "1"
            }
            val max = binaryToDecimal(maxBin)

            repeat(max) {
                var bin = decimalToBinary(it, maxBin.count())
                var result = ""
                var allValid = true
                val countResult = line2.split(" ")[1].split(",").map { it.toInt() }
                while (allValid) {
                    line2.split(" ").first().forEach { c ->
                        val filtered = result.split(".").filter { it.isNotEmpty() }
                        val lastFiltered = filtered.lastOrNull()
                        if ((result.isNotEmpty() && lastFiltered != null && lastFiltered?.count() ?: 0 > countResult[filtered.size - 1]) ||
                                (filtered.size > 1 && filtered[filtered.size - 2].count() != countResult[filtered.size - 2])
                        ) {
                            allValid = false
                            return@repeat
                        } else {
                            if (c == '?') {
                                when (bin.first()) {
                                    '0' -> result += '.'
                                    '1' -> result += '#'
                                }
                                bin = bin.drop(1)
                            } else {
                                result += c
                            }
                        }
                    }
                }
//                var allValid = true
//                val countResult = result.split(" ")[1].split(",")
//                val hashs = result.split(" ").first().split(".").filter { it.isNotEmpty() }
//                if (hashs.count() == countResult.count()) {
//                    hashs.forEachIndexed { index, s ->
//                        if (s.count() != countResult.getOrNull(index)?.toInt()) {
//                            allValid = false
//                        }
//                    }
//                } else allValid = false
                if (allValid) {
                    arrangements++
                }
            }
        }

        return arrangements
    }

    val input = readInput("Day12")
    part1(input).println()
//    part2(input).println()
}