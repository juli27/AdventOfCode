package aoc.juli27;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class Day01Test {
    @Test
    void testPart1() {
        final var input = Stream.of("1abc2",
                                    "pqr3stu8vwx",
                                    "a1b2c3d4e5f",
                                    "treb7uchet");

        final var solution = new Day01().solvePart1(input);
        assertEquals("142", solution);
    }

    @Test
    void testPart2() {
        final var input = Stream.of("two1nine",
                                    "eightwothree",
                                    "abcone2threexyz",
                                    "xtwone3four",
                                    "4nineeightseven2",
                                    "zoneight234",
                                    "7pqrstsixteen");

        final var solution = new Day01().solvePart2(input);
        assertEquals("281", solution);
    }

    @Test
    void testPart2EdgeCase1() {
        final var input = Stream.of("nineight");
        final var solution = new Day01().solvePart2(input);
        assertEquals("98", solution);
    }
}
