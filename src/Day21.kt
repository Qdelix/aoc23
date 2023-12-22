fun main() {

    fun part1(input: List<String>): Int {
        val mutable = input.map { it.toMutableList() }.toMutableList()
        val sRowI = mutable.indexOfFirst { it.contains('S') }
        val sColI = mutable[sRowI].indexOfFirst { it == 'S' }
        if (mutable[sRowI - 1][sColI] != '#') mutable[sRowI - 1][sColI] = 'O'
        if (mutable[sRowI + 1][sColI] != '#') mutable[sRowI + 1][sColI] = 'O'
        if (mutable[sRowI][sColI - 1] != '#') mutable[sRowI][sColI - 1] = 'O'
        if (mutable[sRowI][sColI + 1] != '#') mutable[sRowI][sColI + 1] = 'O'
        repeat(99) {
            val oIndices = mutableListOf<Pair<Int, Int>>()
            mutable.forEachIndexed { rowI, chars ->
                chars.forEachIndexed { colI, c ->
                    if (c == 'O') oIndices.add(rowI to colI)
                }
            }
            oIndices.forEach { o ->
                if (o.first > 0 && mutable[o.first - 1][o.second] != '#') mutable[o.first - 1][o.second] = 'O'
                if (o.first < input.size - 1 && mutable[o.first + 1][o.second] != '#') mutable[o.first + 1][o.second] = 'O'
                if (o.second > 0 && mutable[o.first][o.second - 1] != '#') mutable[o.first][o.second - 1] = 'O'
                if (o.second < input.first().count() - 1 && mutable[o.first][o.second + 1] != '#') mutable[o.first][o.second + 1] = 'O'
            }
            oIndices.forEach { o ->
                mutable[o.first][o.second] = '.'
            }
        }
        var sum = 0
        mutable.forEach { sum += it.count { it == 'O' } }
        return sum
    }


    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("test")
    part1(input).println()
    part2(input).println()
}