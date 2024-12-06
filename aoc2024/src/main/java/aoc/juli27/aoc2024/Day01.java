package aoc.juli27.aoc2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class Day01 implements Solution {

  @Override
  public String solvePart1(Stream<String> lines) {
    var firstList = new ArrayList<Integer>();
    var secondList = new ArrayList<Integer>();

    lines.map(Lexer::new)
        .forEach(lexer -> {
          firstList.add(lexer.nextInt());
          lexer.advanceToEndWhile(Character::isWhitespace);
          secondList.add(lexer.nextInt());
        });

    firstList.sort(Integer::compareTo);
    secondList.sort(Integer::compareTo);

    var sumDistances = 0;

    for (int i = 0; i < firstList.size(); i++) {
      sumDistances += Math.abs(firstList.get(i) - secondList.get(i));
    }

    return Integer.toString(sumDistances);
  }

  @Override
  public String solvePart2(Stream<String> lines) {
    var firstList = new ArrayList<Integer>();
    var secondList = new ArrayList<Integer>();

    lines.map(Lexer::new)
        .forEach(lexer -> {
          firstList.add(lexer.nextInt());
          lexer.advanceToEndWhile(Character::isWhitespace);
          secondList.add(lexer.nextInt());
        });

    firstList.sort(Integer::compareTo);
    secondList.sort(Integer::compareTo);

    var numOccurrences = new HashMap<Integer, Integer>();
    for (var id : secondList) {
      numOccurrences.compute(id, (k, v) -> v == null ? 1 : v + 1);
    }

    var similarityScore = 0;

    for (var id : firstList) {
      var num = numOccurrences.getOrDefault(id, 0);

      similarityScore += id * num;
    }

    return Integer.toString(similarityScore);
  }
}
