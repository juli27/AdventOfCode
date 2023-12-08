package aoc.juli27;

import static java.util.Objects.requireNonNull;

import java.util.function.IntPredicate;

public class Lexer {
  private final String input;
  private int nextCharIndex = 0;

  public Lexer(String input) {
    this.input = requireNonNull(input);
  }

  public int nextInt() {
    final var intSubstringStart = nextCharIndex;
    advanceToEndWhile(Character::isDigit);

    return Integer.parseInt(input, intSubstringStart, nextCharIndex, 10);
  }

  public long nextLong() {
    final var longSubstringStart = nextCharIndex;
    advanceToEndWhile(Character::isDigit);

    return Long.parseLong(input, longSubstringStart, nextCharIndex, 10);
  }

  public char next() {
    final var next = peek();
    advance();

    return next;
  }

  public void advanceToEndWhile(IntPredicate predicate) {
    while (hasNext() && predicate.test(peek())) {
      advance();
    }
  }

  public void advanceToEndUntil(IntPredicate predicate) {
    advanceToEndWhile(predicate.negate());
  }

  public void advance() {
    nextCharIndex++;
  }

  public void advance(int n) {
    nextCharIndex += n;
  }

  public char peek() {
    return input.charAt(nextCharIndex);
  }

  public boolean isAtEnd() {
    return !hasNext();
  }

  public boolean hasNext() {
    return nextCharIndex < input.length();
  }

  public int getIndexOfNext() {
    return nextCharIndex;
  }
}
