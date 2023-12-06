fun main() {
    fun part1(input: List<String>): Int {
        val secs = input[0].substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val mets = input[1].substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        return secs.mapIndexed { index, sec ->
            var winnings = 0
            for (hold in 0..sec) {
                val result = hold * (sec - hold)
                if (result > mets[index]) {
                    winnings += 1
                }
            }
            winnings
        }.reduce(Int::times)
    }

    fun part2(input: List<String>): Int {
        val sec = input[0].replace(" ", "").substringAfter(":").toLong()
        val met = input[1].replace(" ", "").substringAfter(":").toLong()
        var winnings = 0
        for (hold in 0..sec) {
            val result = hold * (sec - hold)
            if (result > met) {
                winnings += 1
            }
        }
        return winnings
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}