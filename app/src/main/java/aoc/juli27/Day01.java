package aoc.juli27;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day01 implements Solution {
  // a two pointer solution would be faster, but less elegant

  @Override
  public String solvePart1(Stream<String> input) {
    final var sumCalibrationValues =
        input.mapToInt(line -> {
               final var digits = line.chars()
                                      .filter(c -> c >= '0' && c <= '9')
                                      .map(c -> c - '0')
                                      .toArray();
               return digits[0] * 10 + digits[digits.length - 1];
             })
             .sum();

    return Integer.toString(sumCalibrationValues);
  }

  @Override
  public String solvePart2(Stream<String> input) {
    // This pattern captures the "digit" in the first group via lookahead and then matches any single character.
    // This is required in order to handle merged digits like "nineight" which should be 9 and 8
    // but since matching starts at the end of the previous match a naive pattern would only match "nine" since
    // "ight" is not a "digit". Hence, we match with lookahead within a separate group while the whole pattern
    // matches only a single character, causing the matcher to advance by one character only.
    final var digitPattern = Pattern.compile("(?=(\\d|one|two|three|four|five|six|seven|eight|nine)).");
    final var digitWords = Map.of("one", 1,
                                  "two", 2,
                                  "three", 3,
                                  "four", 4,
                                  "five", 5,
                                  "six", 6,
                                  "seven", 7,
                                  "eight", 8,
                                  "nine", 9);
    final var sumCalibrationValues =
        input.mapToInt(line -> {
               final var digits = digitPattern.matcher(line)
                                              .results()
                                              .map(matchResult -> matchResult.group(1))
                                              .mapToInt(digit -> digitWords.containsKey(digit)
                                                                 ? digitWords.get(digit)
                                                                 : digit.charAt(0) - '0')
                                              .toArray();

               return digits[0] * 10 + digits[digits.length - 1];
             })
             .sum();

    return Integer.toString(sumCalibrationValues);
  }
}
