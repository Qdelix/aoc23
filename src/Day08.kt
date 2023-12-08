fun main() {

    fun part1(input: List<String>): Int {
        var steps = 0
        var point = "AAA"
        val day08 = input.drop(2).map { line ->
            Day08(value = line.takeWhile { it != ' ' }, left = line.substringAfter("(").take(3), right = line.substringAfterLast(" ").take(3))
        }
        while (point != "ZZZ") {
            input.first().forEach { rl ->
                steps++
                val map = day08.first { it.value == point }
                when (rl) {
                    'R' -> {
                        point = map.right
                    }

                    'L' -> {
                        point = map.left
                    }
                }
                if (point == "ZZZ") return@forEach
            }
        }
        return steps
    }

    fun part2(input: List<String>): Long {
        val steps = mutableListOf<Int>()
        val day08 = input.drop(2).map { line ->
            Day08(value = line.takeWhile { it != ' ' }, left = line.substringAfter("(").take(3), right = line.substringAfterLast(" ").take(3))
        }
        val points = day08.filter { it.value.endsWith("A") }
        val rls = input.first()
        points.forEach {
            var step = 0
            var point = it.value
            while (!point.endsWith("Z")) {
                rls.forEach { rl ->
                    step++
                    val map = day08.first { it.value == point }
                    when (rl) {
                        'R' -> {
                            point = map.right
                        }

                        'L' -> {
                            point = map.left
                        }
                    }
                    if (point.endsWith("Z")) {
                        steps.add(step)
                        return@forEach
                    }
                }
            }
        }
        return lcm(steps)
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

data class Day08(
        val value: String,
        val left: String,
        val right: String
)

private fun gcd(a: Long, b: Long): Long {
    var a = a
    var b = b
    while (b > 0) {
        val temp = b
        b = a % b
        a = temp
    }
    return a
}

private fun lcm(a: Long, b: Long): Long {
    return a * (b / gcd(a, b))
}

private fun lcm(input: List<Int>): Long {
    var result = input[0].toLong()
    for (i in 1 until input.size) result = lcm(result, input[i].toLong())
    return result
}