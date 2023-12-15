fun main() {

    fun part1(input: List<String>): Int {
        var result = 0
        input.first().split(",").forEach { code ->
            var r = 0
            code.forEach {
                r = ((r + it.code) * 17) % 256
            }
            result += r
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val hashMap = HashMap<Int, MutableMap<String, Int>>()
        input.first().split(",").forEach { code ->
            var r = 0
            val text = if (code.contains("=")) code.substringBefore("=") else code.substringBefore("-")
            text.forEach {
                r = ((r + it.code) * 17) % 256
            }

            val value = if (code.contains("=")) code.substringAfter("=").toInt() else 0


            if (hashMap[r] == null && code.contains("=")) {
                hashMap[r] = mutableMapOf(text to value)
            } else {
                if (hashMap[r]?.contains(text) == true) {
                    if (code.contains("=")) hashMap[r]?.set(text, value) else hashMap[r]?.remove(text)
                } else if (code.contains("=")) {
                    hashMap[r]?.set(text, value)
                }
            }
        }

        var sum = 0
        hashMap.forEach { i, map ->
            map.forEach { key, value ->
                sum += (1+i) * (map.keys.indexOf(key) + 1) * value
            }
        }

        return sum
    }

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}