package aoc.juli27;

import io.github.juli27.aoc.Lexer;
import io.github.juli27.aoc.Solution;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day02 implements Solution {

  @Override
  public String solvePart1(Stream<String> input) {
    final var bagContents = new SetOfCubes(12, 13, 14);

    final var sumPossibleGameIds = input
        .map(PlayedGame::parse)
        .filter(playedGame -> playedGame.isPossible(bagContents))
        .mapToInt(PlayedGame::gameId)
        .sum();

    return Integer.toString(sumPossibleGameIds);
  }

  @Override
  public String solvePart2(Stream<String> input) {
    final var sumPowers = input
        .map(PlayedGame::parse)
        .map(PlayedGame::getMinimumBagContents)
        .mapToInt(SetOfCubes::calcPower)
        .sum();

    return Integer.toString(sumPowers);
  }

  private record PlayedGame(
      int gameId,
      List<SetOfCubes> revealedCubes
  ) {
    private static PlayedGame parse(String line) {
      final var lexer = new Lexer(line);

      // skip "Game "
      lexer.advance(5);

      final var gameId = lexer.nextInt();

      final var revealedCubes = new ArrayList<SetOfCubes>();
      do {
        var numRed = 0;
        var numGreen = 0;
        var numBlue = 0;

        do {
          // skip ": " or ", " or "; "
          lexer.advance(2);

          final var numCubes = lexer.nextInt();

          // skip space
          lexer.advance();

          switch (lexer.peek()) {
            case 'r' -> {
              numRed = numCubes;

              // skip 'red'
              lexer.advance(3);
            }
            case 'g' -> {
              numGreen = numCubes;

              // skip 'green'
              lexer.advance(5);
            }
            case 'b' -> {
              numBlue = numCubes;

              // skip 'blue'
              lexer.advance(4);
            }
          }
        } while (lexer.hasNext() && lexer.peek() == ',');

        revealedCubes.add(new SetOfCubes(numRed, numGreen, numBlue));
      } while (lexer.hasNext() && lexer.peek() == ';');

      return new PlayedGame(gameId, revealedCubes);
    }

    private boolean isPossible(SetOfCubes cubesInBag) {
      for (var setOfCubes : revealedCubes) {
        if (setOfCubes.numRed > cubesInBag.numRed) {
          return false;
        }

        if (setOfCubes.numGreen > cubesInBag.numGreen) {
          return false;
        }

        if (setOfCubes.numBlue > cubesInBag.numBlue) {
          return false;
        }
      }

      return true;
    }

    private SetOfCubes getMinimumBagContents() {
      var minRed = 0;
      var minGreen = 0;
      var minBlue = 0;
      for (var setOfCubes : revealedCubes) {
        minRed = Math.max(minRed, setOfCubes.numRed);
        minGreen = Math.max(minGreen, setOfCubes.numGreen);
        minBlue = Math.max(minBlue, setOfCubes.numBlue);
      }

      return new SetOfCubes(minRed, minGreen, minBlue);
    }
  }

  private record SetOfCubes(
      int numRed,
      int numGreen,
      int numBlue
  ) {
    private int calcPower() {
      return numRed * numGreen * numBlue;
    }
  }
}
