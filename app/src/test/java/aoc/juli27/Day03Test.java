package aoc.juli27;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class Day03Test {
  @Test
  void testSolvePart1() {
    final var input = Stream.of("467..114..",
                                "...*......",
                                "..35..633.",
                                "......#...",
                                "617*......",
                                ".....+.58.",
                                "..592.....",
                                "......755.",
                                "...$.*....",
                                ".664.598..");

    final var solution = new Day03().solvePart1(input);
    assertEquals("4361", solution);
  }

  @Test
  void testSolvePart2() {
    final var input = Stream.of("467..114..",
                                "...*......",
                                "..35..633.",
                                "......#...",
                                "617*......",
                                ".....+.58.",
                                "..592.....",
                                "......755.",
                                "...$.*....",
                                ".664.598..");

    final var solution = new Day03().solvePart2(input);
    assertEquals("467835", solution);
  }
}
