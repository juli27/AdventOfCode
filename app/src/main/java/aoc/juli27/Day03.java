package aoc.juli27;

import java.util.List;
import java.util.stream.Stream;

public class Day03 implements Solution {
  @Override
  public String solvePart1(Stream<String> input) {
    final var engineSchematic = input.toList();

    var sumEnginePartNumbers = 0;
    for (var i = 0; i < engineSchematic.size(); i++) {
      final var row = engineSchematic.get(i);
      final var lexer = new Lexer(row);

      do {
        lexer.advanceToEndUntil(Character::isDigit);
        if (lexer.isAtEnd()) {
          break;
        }

        final var numberBegin = lexer.getIndexOfNext();
        final var number = lexer.nextInt();
        final var numberEnd = lexer.getIndexOfNext();

        // limit the columns to at most one above and below
        final var searchRowsStart = Math.max(0, i - 1);
        final var searchRowsEnd = Math.min(i + 2, engineSchematic.size());
        final var searchRows = engineSchematic.subList(searchRowsStart, searchRowsEnd);

        // check the surrounding box for a symbol (including diagonals)
        // e.g. check a (5 x 3) box when the number consists of 3 characters
        final var searchColStart = Math.max(0, numberBegin - 1);
        final var searchColEnd = Math.min(numberEnd + 1, row.length());

        if (hasSymbol(searchRows, searchColStart, searchColEnd)) {
          sumEnginePartNumbers += number;
        }
      } while (lexer.hasNext());
    }

    return Integer.toString(sumEnginePartNumbers);
  }

  @Override
  public String solvePart2(Stream<String> input) {
    final var engineSchematic = input.toList();

    var sumGearRatios = 0;
    for (var i = 0; i < engineSchematic.size(); i++) {
      final var row = engineSchematic.get(i);
      final var lexer = new Lexer(row);

      do {
        lexer.advanceToEndUntil(c -> c == '*');
        if (lexer.isAtEnd()) {
          break;
        }

        // skip '*'
        final var star = lexer.getIndexOfNext();
        lexer.advance();
        final var afterStar = lexer.getIndexOfNext();

        // limit the columns to one above and below
        final var searchRowsStart = Math.max(0, i - 1);
        final var searchRowsEnd = Math.min(i + 2, engineSchematic.size());
        final var searchRows = engineSchematic.subList(searchRowsStart, searchRowsEnd);

        final var searchColStart = Math.max(0, star - 1);
        final var searchColEnd = Math.min(afterStar + 1, row.length());
        final var gearRatioOr0 = calcGearRatio(searchRows, searchColStart, searchColEnd);

        sumGearRatios += gearRatioOr0;
      } while (lexer.hasNext());
    }

    return Integer.toString(sumGearRatios);
  }

  private static int calcGearRatio(List<String> rows, int searchColBegin, int searchColEnd) {
    var numbersFound = 0;
    var gearRatio = 1;
    for (var row : rows) {
      final var lexer = new Lexer(row);
      do {
        lexer.advanceToEndUntil(Character::isDigit);
        if (lexer.isAtEnd()) {
          break;
        }

        final var numberBegin = lexer.getIndexOfNext();
        final var number = lexer.nextInt();
        final var numberEnd = lexer.getIndexOfNext();

        // check if the number overlaps the search area
        if (searchColBegin < numberEnd && numberBegin < searchColEnd) {
          // we found more than two numbers -> not a gear
          if (numbersFound >= 2) {
            return 0;
          }

          numbersFound++;
          gearRatio *= number;
        }
      } while (lexer.hasNext());
    }

    if (numbersFound < 2) {
      return 0;
    }

    return gearRatio;
  }

  private static boolean hasSymbol(List<String> rows, int searchColBegin, int searchColEnd) {
    for (var row : rows) {
      for (var i = searchColBegin; i < searchColEnd; i++) {
        final var c = row.charAt(i);
        if (c != '.' && !Character.isDigit(c)) {
          return true;
        }
      }
    }

    return false;
  }
}
