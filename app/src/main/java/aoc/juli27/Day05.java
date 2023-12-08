package aoc.juli27;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.LongUnaryOperator;
import java.util.stream.Stream;

public class Day05 implements Solution {
  @Override
  public String solvePart1(Stream<String> input) {
    final var it = input.iterator();

    final var seedIds = parseSeedIds(it.next());

    // ""
    it.next();

    final var maps = Maps.parse(it);

    final var minLocation = seedIds
        .stream()
        .mapToLong(Long::longValue)
        .map(mapIdWith(maps.seedToSoil))
        .map(mapIdWith(maps.soilToFertilizer))
        .map(mapIdWith(maps.fertilizerToWater))
        .map(mapIdWith(maps.waterToLight))
        .map(mapIdWith(maps.lightToTemperature))
        .map(mapIdWith(maps.temperatureToHumidity))
        .map(mapIdWith(maps.humidityToLocation))
        .min()
        .orElseThrow();

    return Long.toString(minLocation);
  }

  @Override
  public String solvePart2(Stream<String> input) {
    final var it = input.iterator();

    final var seedIdRanges = parseSeedIdRanges(it.next());

    // ""
    it.next();

    final var maps = Maps.parse(it);

    final var minLocation = seedIdRanges
        .asRanges()
        .stream()
        .flatMap(mapIdRangeToRangesWith(maps.seedToSoil))
        .flatMap(mapIdRangeToRangesWith(maps.soilToFertilizer))
        .flatMap(mapIdRangeToRangesWith(maps.fertilizerToWater))
        .flatMap(mapIdRangeToRangesWith(maps.waterToLight))
        .flatMap(mapIdRangeToRangesWith(maps.lightToTemperature))
        .flatMap(mapIdRangeToRangesWith(maps.temperatureToHumidity))
        .flatMap(mapIdRangeToRangesWith(maps.humidityToLocation))
        .mapToLong(Range::lowerEndpoint)
        .min()
        .orElseThrow();

    return Long.toString(minLocation);
  }

  private static Function<Range<Long>, Stream<Range<Long>>> mapIdRangeToRangesWith(RangeMap<Long, Range<Long>> map) {
    return srcIdRange -> {
      final var unmappedSrcIdRanges = TreeRangeSet.<Long>create();
      unmappedSrcIdRanges.add(srcIdRange);
      final var dstIdRanges = new ImmutableRangeSet.Builder<Long>();

      for (final var mappedIdRange : map.asMapOfRanges()
                                        .entrySet()) {
        final var mappedSrcIdRange = mappedIdRange.getKey();
        final var mappedDstIdRange = mappedIdRange.getValue();
        if (!srcIdRange.isConnected(mappedSrcIdRange)) {
          continue;
        }

        final var srcIdRangeIntersection = srcIdRange.intersection(mappedSrcIdRange);
        if (srcIdRangeIntersection.isEmpty()) {
          continue;
        }

        unmappedSrcIdRanges.remove(mappedSrcIdRange);

        final var offset = srcIdRangeIntersection.lowerEndpoint() - mappedSrcIdRange.lowerEndpoint();
        final var length = srcIdRangeIntersection.upperEndpoint() - srcIdRangeIntersection.lowerEndpoint();
        final var dstIdRangeBegin = mappedDstIdRange.lowerEndpoint() + offset;
        final var dstIdRangeEnd = dstIdRangeBegin + length;
        dstIdRanges.add(Range.closedOpen(dstIdRangeBegin, dstIdRangeEnd));
      }

      dstIdRanges.addAll(unmappedSrcIdRanges);

      return dstIdRanges.build()
                        .asRanges()
                        .stream();
    };
  }

  private static LongUnaryOperator mapIdWith(RangeMap<Long, Range<Long>> map) {
    return id -> {
      final var entryOrNull = map.getEntry(id);
      if (entryOrNull == null) {
        return id;
      }

      final var srcRange = entryOrNull.getKey();
      final var dstRange = entryOrNull.getValue();
      final var offset = id - srcRange.lowerEndpoint();

      return dstRange.lowerEndpoint() + offset;
    };
  }

  private static RangeSet<Long> parseSeedIdRanges(String line) {
    final var lexer = new Lexer(line);
    final var seedRangesBuilder = new ImmutableRangeSet.Builder<Long>();
    do {
      lexer.advanceToEndUntil(Character::isDigit);
      final var seedIdBegin = lexer.nextLong();
      lexer.advance();

      final var length = lexer.nextLong();

      seedRangesBuilder.add(Range.closedOpen(seedIdBegin, seedIdBegin + length));
    } while (lexer.hasNext());

    return seedRangesBuilder.build();
  }

  private static List<Long> parseSeedIds(String line) {
    final var lexer = new Lexer(line);

    final var seedIds = new ArrayList<Long>();
    do {
      lexer.advanceToEndUntil(Character::isDigit);
      if (lexer.isAtEnd()) {
        break;
      }

      final var seedId = lexer.nextLong();
      seedIds.add(seedId);
    } while (lexer.hasNext());

    return seedIds;
  }

  private record Maps(
      RangeMap<Long, Range<Long>> seedToSoil,
      RangeMap<Long, Range<Long>> soilToFertilizer,
      RangeMap<Long, Range<Long>> fertilizerToWater,
      RangeMap<Long, Range<Long>> waterToLight,
      RangeMap<Long, Range<Long>> lightToTemperature,
      RangeMap<Long, Range<Long>> temperatureToHumidity,
      RangeMap<Long, Range<Long>> humidityToLocation
  ) {
    static Maps parse(Iterator<String> lines) {
      // "seed-to-soil map:"
      lines.next();
      final var seedToSoil = parseMap(lines);

      // "soil-to-fertilizer map:"
      lines.next();
      final var soilToFertilizer = parseMap(lines);

      // "fertilizer-to-water map:"
      lines.next();
      final var fertilizerToWater = parseMap(lines);

      // "water-to-light map:"
      lines.next();
      final var waterToLight = parseMap(lines);

      // "light-to-temperature map:"
      lines.next();
      final var lightToTemperature = parseMap(lines);

      // "temperature-to-humidity map:"
      lines.next();
      final var temperatureToHumidity = parseMap(lines);

      // "humidity-to-location map:"
      lines.next();
      final var humidityToLocation = parseMap(lines);

      return new Maps(
          seedToSoil,
          soilToFertilizer,
          fertilizerToWater,
          waterToLight,
          lightToTemperature,
          temperatureToHumidity,
          humidityToLocation);
    }

    private static RangeMap<Long, Range<Long>> parseMap(Iterator<String> lines) {
      final var mapBuilder = new ImmutableRangeMap.Builder<Long, Range<Long>>();
      while (lines.hasNext()) {
        final var line = lines.next();
        if (line.isEmpty()) {
          break;
        }

        final var lexer = new Lexer(line);
        final var destBegin = lexer.nextLong();
        lexer.advance();
        final var sourceBegin = lexer.nextLong();
        lexer.advance();
        final var length = lexer.nextLong();

        mapBuilder.put(Range.closedOpen(sourceBegin, sourceBegin + length),
                       Range.closedOpen(destBegin, destBegin + length));
      }

      return mapBuilder.build();
    }
  }
}
