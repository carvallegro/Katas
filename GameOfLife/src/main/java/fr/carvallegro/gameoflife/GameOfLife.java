package fr.carvallegro.gameoflife;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.VisibleForTesting;

public class GameOfLife {

	private static final boolean ALIVE_CELL = true;
	private static final boolean DEAD_CELL = false;
	private static final int V_INDEX = 1;
	private static final int H_INDEX = 0;
	private static final int GRID_SIZE_INDEX = 0;
	private static final String GRID_SIZE_SEPARATOR = " ";
	private static final String INPUT_SEPARATOR = "\n";
	private Integer horizontalSize;
	private Integer verticalSize;
	private List<List<Boolean>> grid;

	public GameOfLife(String input) {
		if (input == null || input.isEmpty()) {
			throw new IllegalArgumentException("parameters should not be null or empty !");
		}

		List<String> inputLines = Arrays.asList(input.split(INPUT_SEPARATOR));
		extractGridSize(inputLines.get(GRID_SIZE_INDEX));

		List<String> gridLines = inputLines.subList(GRID_SIZE_INDEX + 1, inputLines.size());
		grid = generateGrid(gridLines);
		
	}

	private void extractGridSize(String sizeLine) {
		List<Integer> gridSize = findGridSize(sizeLine);
		horizontalSize = gridSize.get(H_INDEX);
		verticalSize = gridSize.get(V_INDEX);
	}

	private static List<Integer> findGridSize(String gridSizeLine) {
		String[] stringGridSize = gridSizeLine.split(GRID_SIZE_SEPARATOR);
		try {
			return Arrays.asList(Integer.valueOf(stringGridSize[H_INDEX]), Integer.valueOf(stringGridSize[V_INDEX]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Grid size should be positives integer !");
		}
	}

	private List<List<Boolean>> generateGrid(List<String> gridLines) {
		if (isVerticalLineSizeInBound(gridLines)) {
			throw new IllegalArgumentException("bad vertical size !");
		}

		List<List<Boolean>> grid = new ArrayList<List<Boolean>>();
		for (String strLine : gridLines) {
			if (isHorizontalLineSizeInBound(strLine)) {
				throw new IllegalArgumentException("bad Horizontal size !");
			}
			List<String> gridLine = Arrays.asList(strLine.split(""));
			grid.add(
					gridLine.stream()
					.map(GameOfLife::isCharAlive)
					.collect(Collectors.toList()));
		}

		return grid;
	}

	private boolean isVerticalLineSizeInBound(List<String> gridLines) {
		return gridLines.size() != verticalSize;
	}

	private boolean isHorizontalLineSizeInBound(String strLine) {
		return strLine.length() != horizontalSize;
	}

	private static boolean isCharAlive(String elem) {
		if (elem.equalsIgnoreCase("X")) {
			return true;
		}

		if (elem.equalsIgnoreCase(".")) {
			return false;
		}

		throw new IllegalArgumentException("The grid should contain either 'X' or '.' !");
	}

	public void newGeneration() {
		for (int y = 0; y < grid.size(); y++) {
			List<Boolean> horizontalLine = grid.get(y);
			for (int x = 0; x < horizontalLine.size(); x++) {
				int nbNeighbours = calculateNbNeighbours(x, y);
				if (isCellAlive(x, y)) {
					if (isUnderPopulated(nbNeighbours) || isOverPopulated(nbNeighbours)) {
						setCellState(y, x, DEAD_CELL);
					} else if (isCorrectlyPopulated(nbNeighbours)) {
						setCellState(y, x, ALIVE_CELL);
					}
				} else if (!isCellAlive(x, y) && isCellHasToLive(nbNeighbours)) {
					setCellState(y, x, ALIVE_CELL);
				}
			}
		}

	}

	private int calculateNbNeighbours(int x, int y) {
		int nbNeighbours = 0;
		// upper left
		nbNeighbours += upperLeftNeighbour(x, y);
		// upper
		nbNeighbours += upperNeighbour(x, y);
		// upper right
		nbNeighbours += upperRightNeighbour(x, y);
		// right
		nbNeighbours += rightNeighbour(x, y);
		// lower right
		nbNeighbours += lowerRightNeighbour(x, y);
		// lower
		nbNeighbours += lowerNeighbour(x, y);
		// lower left
		nbNeighbours += lowerLeftNeighbour(x, y);
		// left
		nbNeighbours += leftNeighbour(x, y);

		return nbNeighbours;
	}

	private int upperLeftNeighbour(int x, int y) {
		return isCellAlive(x - 1, y - 1) ? 1 : 0;
	}

	private int upperNeighbour(int x, int y) {
		return isCellAlive(x, y - 1) ? 1 : 0;
	}

	private int upperRightNeighbour(int x, int y) {
		return isCellAlive(x + 1, y - 1) ? 1 : 0;
	}

	private int rightNeighbour(int x, int y) {
		return isCellAlive(x + 1, y) ? 1 : 0;
	}

	private int lowerRightNeighbour(int x, int y) {
		return isCellAlive(x + 1, y + 1) ? 1 : 0;
	}

	private int lowerNeighbour(int x, int y) {
		return isCellAlive(x, y + 1) ? 1 : 0;
	}

	private int lowerLeftNeighbour(int x, int y) {
		return isCellAlive(x - 1, y + 1) ? 1 : 0;
	}

	private int leftNeighbour(int x, int y) {
		return isCellAlive(x - 1, y) ? 1 : 0;
	}

	private Boolean isCellAlive(int x, int y) {
		try {
			return grid.get(y).get(x);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	private boolean isCorrectlyPopulated(int nbNeighbours) {
		return nbNeighbours == 2 || nbNeighbours == 3;
	}

	private boolean isOverPopulated(int nbNeighbours) {
		return nbNeighbours > 3;
	}

	private boolean isUnderPopulated(int nbNeighbours) {
		return nbNeighbours < 2;
	}

	private void setCellState(int y, int x, Boolean cellState) {
		grid.get(y).set(x, cellState);
	}

	private boolean isCellHasToLive(int nbNeighbours) {
		return nbNeighbours == 3;
	}
	
	public List<List<Boolean>> getGrid() {
		return grid;
	}

	@Override
	public String toString() {
		return grid.stream().map(GameOfLife::horizontalLineAsString).collect(Collectors.joining("\n"));
	}

	private static String horizontalLineAsString (List<Boolean> line) {
		return line.stream().map(elem -> elem ? "+" : " ").collect(Collectors.joining());
	}
}
