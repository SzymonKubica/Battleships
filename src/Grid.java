import java.util.Arrays;

public class Grid {

  private static final int WIDTH = 10;
  private static final int HEIGHT = 10;

  private final Piece[][] grid = new Piece[HEIGHT][WIDTH];

  public Grid() {
    for (Piece[] row : grid) {
      Arrays.fill(row, Piece.WATER);
    }
  }

  public boolean canPlace(Coordinate c, int size, boolean isDown) {
    if (isDown) {
      return canPlaceVertically(c, size);
    } else {
      return canPlaceHorizontally(c, size);
    }
  }

  private boolean canPlaceVertically(Coordinate c, int size) {
    boolean result = true;
    for (int i = 0; i < size; i++) {
      result = result && isFreePlace(c.getRow() + i, c.getColumn());
    }
    return result;
  }

  private boolean canPlaceHorizontally(Coordinate c, int size) {
    boolean result = true;
    for (int i = 0; i < size; i++) {
      result = result && isFreePlace(c.getRow(), c.getColumn() + i);
    }
    return result;
  }

  private boolean isFreePlace(int row, int column) {
    if (row >= HEIGHT || column >= WIDTH) {
      return false;
    } else {
      return getPiece(row, column) == Piece.WATER && hasFreeSpaceAround(row, column);
    }
  }

  private boolean hasFreeSpaceAround(int row, int column) {
    boolean result = true;
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (isWithinBounds(row + i, column + j)) {
          result = result && getPiece(row + i, column + j) == Piece.WATER;
        }
      }
    }
    return result;
  }

  public boolean isWithinBounds(int row, int column) {
    return 0 <= row && row <= 9 && 0 <= column && column <= 9;
  }

  public void placeShip(Coordinate c, int size, boolean isDown) {
    assert canPlace(c, size, isDown);
    if (isDown) {
      placeShipVertically(c, size);
    } else {
      placeShipHorizontally(c, size);
    }
  }

  private void placeShipVertically(Coordinate c, int size) {
    for (int i = 0; i < size; i++) {
      grid[c.getRow() + i][c.getColumn()] = Piece.SHIP;
    }
  }

  private void placeShipHorizontally(Coordinate c, int size) {
    for (int i = 0; i < size; i++) {
      grid[c.getRow()][c.getColumn() + i] = Piece.SHIP;
    }
  }

  public boolean wouldAttackSucceed(Coordinate c) {
    return getPiece(c) == Piece.SHIP;
  }

  public void attackCell(Coordinate c) {
    if (wouldAttackSucceed(c)) {
      replacePiece(c, Piece.DAMAGED_SHIP);
    } else if (getPiece(c) == Piece.WATER) {
      replacePiece(c, Piece.MISS);
    }
  }

  private void replacePiece(Coordinate c, Piece newPiece) {
    grid[c.getRow()][c.getColumn()] = newPiece;
  }

  private Piece getPiece(Coordinate c) {
    return getPiece(c.getRow(), c.getColumn());
  }

  private Piece getPiece(int row, int column) {
    return grid[row][column];
  }

  public boolean areAllSunk() {
    boolean result = true;
    for (Piece[] row : grid) {
      for (Piece piece : row) {
        // If all of the ships are sunk then neither of the pieces will be Piece.SHIP.
        result = result && piece != Piece.SHIP;
      }
    }
    return result;
  }

  public String toPlayerString() {
    Piece[][] clone = Util.deepClone(grid);
    Util.hideShips(clone);
    return renderGrid(clone);
  }

  @Override
  public String toString() {
    return renderGrid(grid);
  }

  private static String renderGrid(Piece[][] grid) {
    StringBuilder sb = new StringBuilder();
    sb.append(" 0123456789\n");
    for (int i = 0; i < grid.length; i++) {
      sb.append((char) ('A' + i));
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] == null) {
          return "!";
        }
        switch (grid[i][j]) {
          case SHIP -> sb.append('#');
          case DAMAGED_SHIP -> sb.append('*');
          case MISS -> sb.append('o');
          case WATER -> sb.append('.');
        }

      }
      sb.append('\n');
    }

    return sb.toString();
  }
}
