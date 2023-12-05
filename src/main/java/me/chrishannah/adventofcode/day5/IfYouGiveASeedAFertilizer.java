package me.chrishannah.adventofcode.day5;

import me.chrishannah.adventofcode.AOCUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IfYouGiveASeedAFertilizer {

    private static Long getLowestLocationNumberFromPairedSeedData() {
        var reader = AOCUtils.readFile("src/main/resources/day-5-input.txt");
        List<List<String>> sectionedInput = new ArrayList<>();
        List<String> buffer = new ArrayList<>();
        for (String line : reader.lines().toList()) {
            if ("".equals(line)) {
                if (buffer.size() > 0) {
                    sectionedInput.add(buffer);
                    buffer = new ArrayList<>();
                    continue;
                }
            }
            buffer.add(line);
        }
        sectionedInput.add(buffer);

        // Parse Data
        var seeds = new ArrayList<SeedRange>();
        var seedData = sectionedInput.get(0).get(0).substring(7);
        var start = -1L;
        for (String value : seedData.split(" ")) {
            if (start == -1L) {
                start = Long.parseLong(value);
            } else {
                var range = Long.parseLong(value);
                seeds.add(new SeedRange(start, range));
                start = -1L;
            }
        }
        var seedsToSoilMap = extractSourceToRangedValueList(sectionedInput.get(1));
        var soilToFertilizerList = extractSourceToRangedValueList(sectionedInput.get(2));
        var fertilizerToWaterList = extractSourceToRangedValueList(sectionedInput.get(3));
        var waterToLightList = extractSourceToRangedValueList(sectionedInput.get(4));
        var lightToTemperatureList = extractSourceToRangedValueList(sectionedInput.get(5));
        var temperatureToHumidityList = extractSourceToRangedValueList(sectionedInput.get(6));
        var humidityToLocationList = extractSourceToRangedValueList(sectionedInput.get(7));

        // Analyse seeds
        var lowestLocation = Long.MAX_VALUE;
        for (SeedRange seed : seeds) {
            for (long i = 0; i < seed.range; i++) {
                var resolvedSeed = seed.start + i;
                var location = getLocationFromSeed(resolvedSeed, seedsToSoilMap, soilToFertilizerList, fertilizerToWaterList, waterToLightList, lightToTemperatureList, temperatureToHumidityList, humidityToLocationList);
                if (location < lowestLocation) {
                    lowestLocation = location;
                }
            }
        }
        return lowestLocation;
    }

    private static Long getLocationFromSeed(Long seed, List<RangedValue> seedsToSoilMap, List<RangedValue> soilToFertilizerList, List<RangedValue> fertilizerToWaterList, List<RangedValue> waterToLightList, List<RangedValue> lightToTemperatureList, List<RangedValue> temperatureToHumidityList, List<RangedValue> humidityToLocationList) {
        var soil = getValueForKeyFromRangedValueList(seed, seedsToSoilMap);
        var fertilizer = getValueForKeyFromRangedValueList(soil, soilToFertilizerList);
        var water = getValueForKeyFromRangedValueList(fertilizer, fertilizerToWaterList);
        var light = getValueForKeyFromRangedValueList(water, waterToLightList);
        var temperature = getValueForKeyFromRangedValueList(light, lightToTemperatureList);
        var humidity = getValueForKeyFromRangedValueList(temperature, temperatureToHumidityList);
        var location = getValueForKeyFromRangedValueList(humidity, humidityToLocationList);
        return location;
    }

    private static Long getLowestLocationNumberFromSeedData() {
        var reader = AOCUtils.readFile("src/main/resources/day-5-input.txt");
        List<List<String>> sectionedInput = new ArrayList<>();
        List<String> buffer = new ArrayList<>();
        for (String line : reader.lines().toList()) {
            if ("".equals(line)) {
                if (buffer.size() > 0) {
                    sectionedInput.add(buffer);
                    buffer = new ArrayList<>();
                    continue;
                }
            }
            buffer.add(line);
        }
        sectionedInput.add(buffer);

        // Parse Data
        var seeds = Arrays.stream(sectionedInput.get(0).get(0).substring(7).split(" ")).map(Long::parseLong).toList();
        var seedsToSoilMap = extractSourceToRangedValueList(sectionedInput.get(1));
        var soilToFertilizerList = extractSourceToRangedValueList(sectionedInput.get(2));
        var fertilizerToWaterList = extractSourceToRangedValueList(sectionedInput.get(3));
        var waterToLightList = extractSourceToRangedValueList(sectionedInput.get(4));
        var lightToTemperatureList = extractSourceToRangedValueList(sectionedInput.get(5));
        var temperatureToHumidityList = extractSourceToRangedValueList(sectionedInput.get(6));
        var humidityToLocationList = extractSourceToRangedValueList(sectionedInput.get(7));

        // Analyse seeds
        var lowestLocation = Long.MAX_VALUE;
        for (Long seed : seeds) {
            var location = getLocationFromSeed(seed, seedsToSoilMap, soilToFertilizerList, fertilizerToWaterList, waterToLightList, lightToTemperatureList, temperatureToHumidityList, humidityToLocationList);
            if (location < lowestLocation) {
                lowestLocation = location;
            }
        }
        return lowestLocation;
    }

    private static Long getValueForKeyFromRangedValueList(Long key, List<RangedValue> valueList) {
        for (RangedValue rangedValue : valueList) {
            if (key >= rangedValue.sourceRangeStart && key <= rangedValue.sourceRangeStop) {
                return key + rangedValue.valueOffset;
            }
        }
        return key;
    }

    private static List<RangedValue> extractSourceToRangedValueList(List<String> data) {
        List<RangedValue> valueList = new ArrayList<>();
        data.removeFirst();
        data.stream().forEach(line -> {
            var lineData = line.split(" ");
            var destinationRangeStart = Long.parseLong(lineData[0]);
            var sourceRangeStart = Long.parseLong(lineData[1]);
            var rangeLength = Long.parseLong(lineData[2]);
            valueList.add(new RangedValue(sourceRangeStart, sourceRangeStart + rangeLength - 1, destinationRangeStart - sourceRangeStart));
        });
        return valueList;
    }

    private record RangedValue(Long sourceRangeStart, Long sourceRangeStop, Long valueOffset) {
    }

    private record SeedRange(Long start, Long range) {
    }

    public static void main(String[] args) {
        // Part 1
        System.out.println(getLowestLocationNumberFromSeedData());

        // Part 2
        System.out.println(getLowestLocationNumberFromPairedSeedData());
    }
}
