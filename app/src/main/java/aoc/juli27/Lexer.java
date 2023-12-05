package aoc.juli27;

import static java.util.Objects.requireNonNull;

public class Lexer {
  private final String input;
  private int nextCharIndex = 0;

  public Lexer(String input) {
    this.input = requireNonNull(input);
  }

  public int nextInt() {
    final var intSubstringStart = nextCharIndex;
    while (Character.isDigit(peek())) {
      advance();
    }

    return Integer.parseInt(input, intSubstringStart, nextCharIndex, 10);
  }

  public char next() {
    final var next = peek();
    advance();

    return next;
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

  public boolean hasNext() {
    return nextCharIndex < input.length();
  }
}
