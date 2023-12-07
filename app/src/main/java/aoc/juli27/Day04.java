package aoc.juli27;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

public class Day04 implements Solution {
  @Override
  public String solvePart1(Stream<String> input) {
    final var sumOfPoints = input
        .mapToInt(Day04::calcNumWinningNumbers)
        .map(numWinningNumbers -> numWinningNumbers == 0
                                  ? numWinningNumbers
                                  : 1 << (numWinningNumbers - 1))
        .sum();

    return Integer.toString(sumOfPoints);
  }

  @Override
  public String solvePart2(Stream<String> input) {
    final var numsWinningNumbers = input
        .mapToInt(Day04::calcNumWinningNumbers)
        .toArray();
    final var numsCopies = new int[numsWinningNumbers.length];
    Arrays.fill(numsCopies, 1);

    for (var card = 0; card < numsWinningNumbers.length; card++) {
      final var numWinningNumbers = numsWinningNumbers[card];
      final var numCopies = numsCopies[card];

      for (var wonCard = card + 1; wonCard <= card + numWinningNumbers; wonCard++) {
        numsCopies[wonCard] += numCopies;
      }
    }

    final var numCards = Arrays.stream(numsCopies)
                               .sum();

    return Integer.toString(numCards);
  }

  private static int calcNumWinningNumbers(String line) {
    final var lexer = new Lexer(line);
    // skip "Card ", card number, ": "
    lexer.advanceToEndUntil(Character::isDigit);
    lexer.nextInt();

    final var winningNumbers = new HashSet<Integer>();

    while (true) {
      lexer.advanceToEndUntil(Day04::isVBarOrDigit);
      if (lexer.peek() == '|') {
        lexer.advance();

        break;
      }

      final var winningNumber = lexer.nextInt();
      winningNumbers.add(winningNumber);
    }

    var numWinningNumbers = 0;
    while (true) {
      lexer.advanceToEndUntil(Character::isDigit);
      if (lexer.isAtEnd()) {
        break;
      }

      final var number = lexer.nextInt();
      if (winningNumbers.contains(number)) {
        numWinningNumbers++;
      }
    }

    return numWinningNumbers;
  }

  private static boolean isVBarOrDigit(int c) {
    return c == '|' || Character.isDigit(c);
  }
}
