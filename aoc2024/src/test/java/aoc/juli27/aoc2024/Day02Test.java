package aoc.juli27.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class Day02Test {

  @Test
  void testPart1() {
    final var input = Stream.of(
        "7 6 4 2 1",
        "1 2 7 8 9",
        "9 7 6 2 1",
        "1 3 2 4 5",
        "8 6 4 4 1",
        "1 3 6 7 9");

    final var solution = new Day02().solvePart1(input);
    assertEquals("2", solution);
  }

  @Test
  void testPart2() {
    final var input = Stream.of(
        "7 6 4 2 1",
        "1 2 7 8 9",
        "9 7 6 2 1",
        "1 3 2 4 5",
        "8 6 4 4 1",
        "1 3 6 7 9");

    final var solution = new Day02().solvePart2(input);
    assertEquals("4", solution);
  }
}
