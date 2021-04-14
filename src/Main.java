import java.util.*;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Grid grid;
    if (args.length > 0 && args[0].equals("random")) {
      grid = makeRandomGrid();
    } else {
      grid = makeInitialGrid();
    }
    System.out.println(grid);
    int attemptCounter = 0;

    while (!grid.areAllSunk()) {
      displayPlayerView(grid);
      displayAttackPrompt();
      String userInput = input.next();
      attemptCounter++;
      Coordinate attackCoordinate = Util.parseCoordinate(userInput);
      if (grid.wouldAttackSucceed(attackCoordinate)) {
        displayHitMessage();
      }
      grid.attackCell(attackCoordinate);
    }

    displayRequiredAttempts(attemptCounter);
    System.out.println(grid);
  }

  private static void displayPlayerView(Grid grid) {
    System.out.println(grid.toPlayerString());
  }

  private static void displayAttackPrompt() {
    System.out.println("Enter the coordinate you want to attack: ");
  }

  private static void displayHitMessage() {
    System.out.println("Direct Hit!");
  }

  private static void displayRequiredAttempts(int attempts) {
    System.out.println("All of the ships are sunk!");
    System.out.println(attempts + " were required to sink all ships.");
  }

  private static Grid makeInitialGrid() {
    Grid grid = new Grid();

    String[] coords = {"A7", "B1", "B4", "D3", "F7", "H1", "H4"};
    int[] sizes = {2, 4, 1, 3, 1, 2, 5};
    boolean[] isDowns = {false, true, true, false, false, true, false};

    for (int i = 0; i < coords.length; i++) {
      Coordinate c = Util.parseCoordinate(coords[i]);
      grid.placeShip(c, sizes[i], isDowns[i]);
    }

    return grid;
  }

  private static Grid makeRandomGrid() {
    Grid grid = new Grid();

    List<Coordinate> coordinates = randomizeCoordinates();
    List<Integer> sizes = List.of(2, 4, 1, 3, 1, 2, 5);
    Collections.shuffle(new ArrayList<>(sizes));

    int shipsPlaced = 0;

    while (shipsPlaced < 7) {
      boolean isDown = randomizeOrientation();
      Coordinate coordinate = coordinates.get(shipsPlaced);
      int size = sizes.get(shipsPlaced);
      if (grid.canPlace(coordinate, size, isDown)) {
        grid.placeShip(coordinate, size, isDown);
        shipsPlaced++;
      } else if (grid.canPlace(coordinate, size, !isDown)) {
        grid.placeShip(coordinate, size, !isDown);
        shipsPlaced++;
      } else {
        // Failure, the ship cannot be placed, wrong randomisation. Need to start from the begining.
        return makeRandomGrid();
      }
    }
    return grid;
  }

  private static List<Coordinate> randomizeCoordinates() {
    Random random = new Random();
    Set<Coordinate> coordinates = new HashSet<>();

    while (coordinates.size() < 7) {
      int row = Math.abs(random.nextInt() % 10);
      int column = Math.abs(random.nextInt() % 10);
      Coordinate newCoordinate = new Coordinate(row, column);
      coordinates.add(newCoordinate);
    }
    return new ArrayList<>(coordinates);
  }

  private static boolean randomizeOrientation() {
    Random random = new Random();
    return random.nextInt() % 2 == 0;
  }
}
