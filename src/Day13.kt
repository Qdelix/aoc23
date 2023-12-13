fun main() {

    fun part1(input: List<String>): Int {

        val sublists: MutableList<MutableList<String>> = emptyList<MutableList<String>>().toMutableList()

        var emptyLinesIndexes: MutableList<Int> = emptyList<Int>().toMutableList()
        input.forEachIndexed { index, s ->
            if (s.isEmpty()) {
                emptyLinesIndexes.add(index)
            }
        }

        for (i in 0..emptyLinesIndexes.size) {
            sublists.add(emptyList<String>().toMutableList())
        }

        var indexx = 0
        input.forEachIndexed { index, s ->
            if (s.isNotEmpty()) {
                if (index < (emptyLinesIndexes.firstOrNull() ?: Int.MAX_VALUE)) {
                    sublists[indexx].add(s)
                }
            } else {
                indexx++
                emptyLinesIndexes.removeAt(0)
            }
        }

        var sum = 0
        sublists.forEach { sublist ->
            sublist.windowed(2).indexesOf { it[0] == it[1] }.forEach { index ->
                var lowerIndex = index - 1
                var higherIndex = index + 2
                var isValid = true

                repeat(index) {
                    if (lowerIndex >= 0 && higherIndex < sublist.size && sublist[lowerIndex] == sublist[higherIndex]) {
                        lowerIndex--
                        higherIndex++
                    } else if (lowerIndex >= 0 && higherIndex < sublist.size && sublist[lowerIndex] != sublist[higherIndex]) {
                        isValid = false
                    }
                }
                if (isValid) {
                    sum += (index + 1) * 100
                }
            }

            val rotated = rotate(sublist.map { it.map { it.toString() }.toMutableList() }.toMutableList())
            rotated.windowed(2).indexesOf { it[0] == it[1] }.forEach { index ->
                var lowerIndex = index - 1
                var higherIndex = index + 2
                var isValid = true

                repeat(index) {
                    if (lowerIndex >= 0 && higherIndex < rotated.size && rotated[lowerIndex] == rotated[higherIndex]) {
                        lowerIndex--
                        higherIndex++
                    } else if (lowerIndex >= 0 && higherIndex < rotated.size && rotated[lowerIndex] != rotated[higherIndex]) {
                        isValid = false
                    }
                }
                if (isValid) {
                    sum += index + 1
                }
            }

        }


        return sum
    }

    fun part2(input: List<String>): Int {
        val sublists: MutableList<MutableList<String>> = emptyList<MutableList<String>>().toMutableList()

        var emptyLinesIndexes: MutableList<Int> = emptyList<Int>().toMutableList()
        input.forEachIndexed { index, s ->
            if (s.isEmpty()) {
                emptyLinesIndexes.add(index)
            }
        }

        for (i in 0..emptyLinesIndexes.size) {
            sublists.add(emptyList<String>().toMutableList())
        }

        var indexx = 0
        input.forEachIndexed { index, s ->
            if (s.isNotEmpty()) {
                if (index < (emptyLinesIndexes.firstOrNull() ?: Int.MAX_VALUE)) {
                    sublists[indexx].add(s)
                }
            } else {
                indexx++
                emptyLinesIndexes.removeAt(0)
            }
        }

        var sum = 0
        sublists.forEachIndexed siema@{ i, sublist ->
            val rotated = rotate(sublist.map { it.map { it.toString() }.toMutableList() }.toMutableList())
            rotated.windowed(2).forEachIndexed { index, window ->
                if (window[0] == window[1] || containOneDiff(window[0], window[1])) {
                    var diffs = if (containOneDiff(window[0], window[1])) 1 else 0
                    var lowerIndex = index - 1
                    var higherIndex = index + 2

                    repeat(index) {
                        if (lowerIndex >= 0 && higherIndex < rotated.size && rotated[lowerIndex] == rotated[higherIndex]) {
                            lowerIndex--
                            higherIndex++
                        } else if (lowerIndex >= 0 && higherIndex < rotated.size && containOneDiff(rotated[lowerIndex], rotated[higherIndex])) {
                            lowerIndex--
                            higherIndex++
                            diffs++
                        } else if (lowerIndex >= 0 && higherIndex < rotated.size && !containOneDiff(rotated[lowerIndex], rotated[higherIndex])) {
                            diffs += 100
                        }
                    }
                    if (diffs == 1) {
                        sum += index + 1
                        println("$i: ${index + 1}")
                        return@siema
                    }
                }
            }
            sublist.windowed(2).forEachIndexed { index, window ->
                if (window[0] == window[1] || containOneDiff(window[0], window[1])) {
                    var diffs = if (containOneDiff(window[0], window[1])) 1 else 0
                    var lowerIndex = index - 1
                    var higherIndex = index + 2
                    repeat(index) {
                        if (lowerIndex >= 0 && higherIndex < sublist.size && sublist[lowerIndex] == sublist[higherIndex]) {
                            lowerIndex--
                            higherIndex++
                        } else if (lowerIndex >= 0 && higherIndex < sublist.size && containOneDiff(sublist[lowerIndex], sublist[higherIndex])) {
                            lowerIndex--
                            higherIndex++
                            diffs++
                        } else if (lowerIndex >= 0 && higherIndex < sublist.size && !containOneDiff(sublist[lowerIndex], sublist[higherIndex])) {
                            diffs += 100
                        }
                    }
                    if (diffs == 1) {
                        sum += (index + 1) * 100
                        println("$i: ${(index + 1) * 100}")
                        return@siema
                    }
                }
            }


        }

        return sum
    }

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}

inline fun <E> Iterable<E>.indexesOf(predicate: (E) -> Boolean) = mapIndexedNotNull { index, elem -> index.takeIf { predicate(elem) } }

fun rotate(a: MutableList<MutableList<String>>): List<List<String>> {
    val result = mutableListOf<MutableList<String>>()
    for (col in 0..<a.first().size) {
        val list: MutableList<String> = emptyList<String>().toMutableList()
        for (row in 0..<a.size) {
            list.add(a[row][col])
        }
        result.add(list.reversed().toMutableList())
    }
    return result
}

fun containOneDiff(first: String, second: String): Boolean {
    var diff = 0
    for (i in first.indices) {
        if (first[i] != second[i]) diff++
    }
    return diff == 1
}

fun containOneDiff(first: List<String>, second: List<String>): Boolean {
    var diff = 0
    for (i in first.indices) {
        if (first[i] != second[i]) diff++
    }
    return diff == 1
}