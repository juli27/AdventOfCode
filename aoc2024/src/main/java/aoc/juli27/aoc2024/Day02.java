package aoc.juli27.aoc2024;

import io.github.juli27.aoc.Lexer;
import io.github.juli27.aoc.Solution;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day02 implements Solution {

  @Override
  public String solvePart1(Stream<String> lines) {
    var numSafeReports = lines.map(Day02::parseReport)
        .mapToInt(report -> isReportSafe(report) ? 1 : 0)
        .sum();

    return Integer.toString(numSafeReports);
  }

  @Override
  public String solvePart2(Stream<String> lines) {
    var numSafeReports = lines.map(Day02::parseReport)
        .mapToInt(report -> {
          if (isReportSafe(report)) {
            return 1;
          }

          // brute force: remove an element then check if report is safe
          for (int i = 0; i < report.size(); i++) {
            var reportCopy = new ArrayList<>(report);
            reportCopy.remove(i);

            if (isReportSafe(reportCopy)) {
              return 1;
            }
          }

          return 0;
        }).sum();

    return Integer.toString(numSafeReports);
  }

  private static List<Integer> parseReport(String reportString) {
    var lexer = new Lexer(reportString);
    var report = new ArrayList<Integer>();

    while (lexer.hasNext()) {
      report.add(lexer.nextInt());
      lexer.advanceToEndWhile(Character::isWhitespace);
    }

    return report;
  }

  private static boolean isReportSafe(List<Integer> report) {
    if (report.size() <= 1) {
      return true;
    }

    var previousLevel = report.getFirst();
    final var isIncreasing = previousLevel < report.get(1);

    for (var level : report.subList(1, report.size())) {
      final var diff = isIncreasing ? level - previousLevel : previousLevel - level;
      if (diff < 1 || diff > 3) {
        return false;
      }

      previousLevel = level;
    }

    return true;
  }
}
