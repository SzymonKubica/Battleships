public class Coordinate {

  private final int row;
  private final int column;

  public Coordinate(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof  Coordinate) {
      return row == ((Coordinate) other).getRow() && column == ((Coordinate) other).getColumn();
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return row * column;
  }
}
