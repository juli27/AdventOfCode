package aoc.juli27.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class Day01Test {

  @Test
  void testPart1() {
    final var input = Stream.of(
        "3   4",
        "4   3",
        "2   5",
        "1   3",
        "3   9",
        "3   3");

    final var solution = new Day01().solvePart1(input);
    assertEquals("11", solution);
  }

  @Test
  void testPart2() {
    final var input = Stream.of(
        "3   4",
        "4   3",
        "2   5",
        "1   3",
        "3   9",
        "3   3");

    final var solution = new Day01().solvePart2(input);
    assertEquals("31", solution);
  }
}
