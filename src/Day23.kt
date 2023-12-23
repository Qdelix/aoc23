fun main() {

    var longestPath1 = 0
    val slopes = listOf('^', 'v', '>', '<')
    fun goNext(point: Pair<Int, Int>, map: List<List<Char>>) {
        val mutable = map.map { it.toMutableList() }.toMutableList()
        if (mutable[point.first][point.second] != 'S') mutable[point.first][point.second] = 'O'
        if (point.first == map.size - 1) {
            var oSum = 0
            mutable.forEach { oSum += it.count { it == 'O' } }
            if (oSum > longestPath1) longestPath1 = oSum
            mutable.forEach { it.joinToString(separator = "").println() }
        } else {
            if (point.first > 0 && (map[point.first - 1][point.second] == '.' || map[point.first - 1][point.second] in slopes - 'v')) {
                goNext(point.first - 1 to point.second, mutable)
            }
            if (point.second > 0 && (map[point.first][point.second - 1] == '.' || map[point.first][point.second - 1] in slopes - '>')) {
                goNext(point.first to point.second - 1, mutable)
            }
            if (point.first <= map.size && (map[point.first + 1][point.second] == '.' || map[point.first + 1][point.second] in slopes - '^')) {
                goNext(point.first + 1 to point.second, mutable)
            }
            if (point.second <= map.first().size && (map[point.first][point.second + 1] == '.' || map[point.first][point.second + 1] in slopes - '<')) {
                goNext(point.first to point.second + 1, mutable)
            }
        }
    }

    var longestPath2 = 0
    val slopesDot = listOf('^', 'v', '>', '<', '.')
    fun goNext2(point: Pair<Int, Int>, map: List<List<Char>>) {
        val mutable = map.map { it.toMutableList() }.toMutableList()
        if (mutable[point.first][point.second] != 'S') mutable[point.first][point.second] = 'O'
        if (point.first == map.size - 1) {
            var oSum = 0
            mutable.forEach { oSum += it.count { it == 'O' } }
            if (oSum > longestPath2) longestPath2 = oSum
        } else {
            if (point.first > 0 && map[point.first - 1][point.second] in slopesDot) {
                goNext2(point.first - 1 to point.second, mutable)
            }
            if (point.second > 0 && map[point.first][point.second - 1] in slopesDot) {
                goNext2(point.first to point.second - 1, mutable)
            }
            if (point.first <= map.size && map[point.first + 1][point.second] in slopesDot) {
                goNext2(point.first + 1 to point.second, mutable)
            }
            if (point.second <= map.first().size && map[point.first][point.second + 1] in slopesDot) {
                goNext2(point.first to point.second + 1, mutable)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val start = 0 to input.first().indexOfFirst { it == '.' }
        val mutable = input.map { it.toMutableList() }.toMutableList()
        mutable[start.first][start.second] = 'S'
        goNext(start.first to start.second, mutable)
        return longestPath1
    }


    //works only for test input
    fun part2(input: List<String>): Int {
        val start = 0 to input.first().indexOfFirst { it == '.' }
        val mutable = input.map { it.toMutableList() }.toMutableList()
        mutable[start.first][start.second] = 'S'
        goNext2(start.first to start.second, mutable)
        return longestPath2
    }

    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}