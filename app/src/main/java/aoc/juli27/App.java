package aoc.juli27;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;

public class App {

    private static final List<Solution> solutions = List.of(
        new Day01(),
        new Day02(),
        new Day03());

    public static void main(String[] args) throws Exception {
        final var day = args.length >= 1
                        ? Integer.parseInt(args[0])
                        : 1;
        final var part = args.length == 2
                         ? Integer.parseInt(args[1])
                         : 1;

        final var solution = invoke(() -> {
            final var daySolution = solutions.get(day - 1);
            final var dayInputFileName = String.format("day%02d.txt", day);

            try (var inputLines = Files.lines(Paths.get(dayInputFileName))) {
                if (part == 2) {
                    return daySolution.solvePart2(inputLines);
                }

                return daySolution.solvePart1(inputLines);
            }
        });

        System.out.printf("Solution for day %d, part %d:\n", day, part);
        System.out.println(solution);
    }

    private static <T> T invoke(Callable<T> callable) throws Exception {
        return callable.call();
    }

    private App() {
    }
}
