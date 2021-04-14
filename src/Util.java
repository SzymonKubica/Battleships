import java.util.Arrays;

public class Util {

  private static int letterToIndex(char letter) {
    return Character.getNumericValue(letter) - Character.getNumericValue('A');
  }

  private static int numberToIndex(char number) {
    return Character.getNumericValue(number) - Character.getNumericValue('0');
  }

  public static Coordinate parseCoordinate(String s) {
    return new Coordinate(letterToIndex(s.charAt(0)), numberToIndex(s.charAt(1)));
  }

  public static Piece hideShip(Piece piece) {
    if (piece == Piece.SHIP) {
      return Piece.WATER;
    } else {
      return piece;
    }
  }

  public static void hideShips(Piece[][] grid) {
    for (Piece[] row : grid) {
      for (Piece piece : row) {
        piece = hideShip(piece);
      }
    }
  }

  public static Piece[][] deepClone(Piece[][] input) {
    Piece[][] output = new Piece[input.length][];
    for (int i = 0; i < input.length; i++) {
      output[i] = Arrays.copyOf(input[i], input[i].length);
    }
    return output;
  }
}
