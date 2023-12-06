fun main() {

    fun getValues(input: List<String>, prefix: String): List<String> {
        val index = input.indexOfFirst { it.contains(prefix) }
        return input.drop(index + 1).takeWhile { it.isNotEmpty() }
    }

    fun getNextValue(value: Long, nextValues: List<String>): Long {
        var nextValue = Long.MAX_VALUE
        nextValues.map { it.split(" ").map { it.toLong() } }.firstOrNull {
            nextValue = if (it[1] <= value && it[1] + it[2] > value) {
                it[0] + value - it[1]
            } else value
            it[1] <= value && it[1] + it[2] > value
        }
        return nextValue
    }

    fun part1(input: List<String>): String {
        var lowestLocation = Long.MAX_VALUE
        val seedToSoil = getValues(input, "seed-to-soil map")
        val soilToFertilizer = getValues(input, "soil-to-fertilizer map")
        val fertilizerToWater = getValues(input, "fertilizer-to-water map")
        val waterToLight = getValues(input, "water-to-light map")
        val lightToTemperature = getValues(input, "light-to-temperature map")
        val temperatureToHumidity = getValues(input, "temperature-to-humidity map")
        val humidityToLocation = getValues(input, "humidity-to-location map")
        input.first().removePrefix("seeds: ").split(" ").forEach {
            val loc = getNextValue(getNextValue(getNextValue(getNextValue(getNextValue(getNextValue(getNextValue(it.toLong(), seedToSoil), soilToFertilizer), fertilizerToWater), waterToLight), lightToTemperature), temperatureToHumidity), humidityToLocation)
            if (loc < lowestLocation) {
                lowestLocation = loc
            }
        }
        return lowestLocation.toString()
    }

    fun getIntersection(range1: LongRange, range2: LongRange): LongRange? {
        val start = maxOf(range1.first, range2.first)
        val end = minOf(range1.last, range2.last)
        return if (start <= end) LongRange(start, end) else null
    }

    fun findGaps(mainRange: LongRange, rangesList: List<LongRange>): List<LongRange> {
        val gaps = mutableListOf<LongRange>()
        var currentStart = mainRange.first
        for (range in rangesList.sortedBy { it.first }) {
            if (range.first > currentStart) {
                gaps.add(LongRange(currentStart, range.first - 1))
            }
            currentStart = range.last + 1
        }
        if (currentStart <= mainRange.last) {
            gaps.add(LongRange(currentStart, mainRange.last))
        }
        return gaps
    }

    fun getNextRanges(ranges: List<LongRange>, nextValues: List<String>): List<LongRange> {
        val nextValuesLong = nextValues.map { it.split(" ").map { it.toLong() } }
        val nextRanges = mutableListOf<LongRange>()
        ranges.forEach { range ->
            val usedFromRange = mutableListOf<LongRange>()
            nextValuesLong.forEach {
                val desStart = it[0]
                val sourceStart = it[1]
                val rangeLength = it[2]
                val nextValueRange = sourceStart..<sourceStart + rangeLength
                val intersection = getIntersection(range, nextValueRange)
                if (intersection != null) {
                    usedFromRange.add(intersection)
                    nextRanges.add(desStart + intersection.first - sourceStart..desStart + intersection.last - sourceStart)
                }
            }
            nextRanges.addAll(findGaps(range, usedFromRange))
        }
        val mergedRanges = nextRanges.map { LongRangeWrapper(it.first, it.last) }
                .sortedBy { it.start }
                .fold(mutableListOf<LongRangeWrapper>()) { acc, range ->
                    when {
                        acc.isEmpty() || acc.last().end < range.start -> acc.add(range)
                        else -> acc.last().end = maxOf(acc.last().end, range.end)
                    }
                    acc
                }.map { LongRange(it.start, it.end) }

        return mergedRanges
    }

    fun part2(input: List<String>): String {
        val seedToSoil = getValues(input, "seed-to-soil map")
        val soilToFertilizer = getValues(input, "soil-to-fertilizer map")
        val fertilizerToWater = getValues(input, "fertilizer-to-water map")
        val waterToLight = getValues(input, "water-to-light map")
        val lightToTemperature = getValues(input, "light-to-temperature map")
        val temperatureToHumidity = getValues(input, "temperature-to-humidity map")
        val humidityToLocation = getValues(input, "humidity-to-location map")

        val seedsRanges = input.first().removePrefix("seeds: ").split(" ").chunked(2).map {
            val first = it[0].toLong()
            val range = it[1].toLong()
            first..<first + range
        }
        val soilRanges = getNextRanges(seedsRanges, seedToSoil)
        val fertRanges = getNextRanges(soilRanges, soilToFertilizer)
        val waterRanges = getNextRanges(fertRanges, fertilizerToWater)
        val lightRanges = getNextRanges(waterRanges, waterToLight)
        val tempRanges = getNextRanges(lightRanges, lightToTemperature)
        val humRanges = getNextRanges(tempRanges, temperatureToHumidity)
        val locRanges = getNextRanges(humRanges, humidityToLocation)
        return locRanges.minBy { it.first }.first.toString()
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

data class LongRangeWrapper(var start: Long, var end: Long)