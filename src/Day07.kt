fun main() {

    fun getRankedList(array: List<String>, values: List<List<String>>, isJoker: Boolean = false): List<Int> {
        val sortedNumbs = array.map {
            it.toList().map { it.toString().toNumb(isJoker) }
        }.sortedWith(compareBy<List<Int>> { it[0] }
                .thenBy { it[1] }
                .thenBy { it[2] }
                .thenBy { it[3] }
                .thenBy { it[4] }
        )
        val sortedInput = sortedNumbs.map {
            it.map { it.fromNumb() }.joinToString(separator = "")
        }
        return sortedInput.map { sortedValue -> values.first { it[0] == sortedValue }[1].toInt() }
    }

    fun part1(input: List<String>): Int {
        val rankedList = mutableListOf<Int>()
        val nothing = mutableListOf<String>()
        val pair = mutableListOf<String>()
        val doublePair = mutableListOf<String>()
        val three = mutableListOf<String>()
        val threeAndPair = mutableListOf<String>()
        val four = mutableListOf<String>()
        val five = mutableListOf<String>()
        val values = input.map { it.split(" ") }
        values.forEach { line ->
            val counts = line.first().groupingBy { it }.eachCount()
            when {
                counts.size == 5 -> nothing.add(line.first())
                counts.size == 4 -> pair.add(line.first())
                counts.size == 3 && !counts.containsValue(3) -> doublePair.add(line.first())
                counts.size == 3 && counts.containsValue(3) -> three.add(line.first())
                counts.size == 2 && counts.containsValue(3) -> threeAndPair.add(line.first())
                counts.containsValue(4) -> four.add(line.first())
                counts.size == 1 -> five.add(line.first())
            }
        }
        rankedList.addAll(getRankedList(nothing, values))
        rankedList.addAll(getRankedList(pair, values))
        rankedList.addAll(getRankedList(doublePair, values))
        rankedList.addAll(getRankedList(three, values))
        rankedList.addAll(getRankedList(threeAndPair, values))
        rankedList.addAll(getRankedList(four, values))
        rankedList.addAll(getRankedList(five, values))
        var sum = 0
        rankedList.forEachIndexed { index, item ->
            sum += (index + 1) * item
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val rankedList = mutableListOf<Int>()
        val nothing = mutableListOf<String>()
        val pair = mutableListOf<String>()
        val doublePair = mutableListOf<String>()
        val three = mutableListOf<String>()
        val threeAndPair = mutableListOf<String>()
        val four = mutableListOf<String>()
        val five = mutableListOf<String>()
        val values = input.map { it.split(" ") }
        val joker = 'J'

        values.forEach { line ->
            listOf("" to 1).sortedBy { it.second }
            val counts = line.first().groupingBy { it }.eachCount().map { it.key to it.value }.sortedBy { it.second }.reversed().toMutableList()
            if (counts.size > 1 && counts.map { it.first }.contains(joker)) {
                val jokerCount = counts.first { it.first == joker }.second
                counts.remove(joker to jokerCount)
                counts[0] = counts[0].first to counts[0].second + jokerCount
            }
            when {
                counts.size == 5 -> nothing.add(line.first())
                counts.size == 4 -> pair.add(line.first())
                counts.size == 3 && !counts.any { it.second == 3 } -> doublePair.add(line.first())
                counts.size == 3 && counts.any { it.second == 3 } -> three.add(line.first())
                counts.size == 2 && counts.any { it.second == 3 } -> threeAndPair.add(line.first())
                counts.any { it.second == 4 } -> four.add(line.first())
                counts.size == 1 -> five.add(line.first())
            }
        }
        rankedList.addAll(getRankedList(nothing, values, true))
        rankedList.addAll(getRankedList(pair, values, true))
        rankedList.addAll(getRankedList(doublePair, values, true))
        rankedList.addAll(getRankedList(three, values, true))
        rankedList.addAll(getRankedList(threeAndPair, values, true))
        rankedList.addAll(getRankedList(four, values, true))
        rankedList.addAll(getRankedList(five, values, true))
        var sum = 0
        rankedList.forEachIndexed { index, item ->
            sum += (index + 1) * item
        }
        return sum
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

fun String.toNumb(isJoker: Boolean = false): Int {
    return when (this) {
        "T" -> 10
        "J" -> if (isJoker) 11 else 0
        "Q" -> 12
        "K" -> 13
        "A" -> 12345
        else -> this.toInt()
    }
}

fun Int.fromNumb(): String {
    return when (this) {
        0 -> "J"
        10 -> "T"
        11 -> "J"
        12 -> "Q"
        13 -> "K"
        12345 -> "A"
        else -> this.toString()
    }
}
