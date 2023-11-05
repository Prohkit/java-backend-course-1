package edu.project2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MazeUtils {
    private MazeUtils() {
    }

    public static Cell[][] generateGrid(int inputHeight, int inputWidth) {
        int height = inputHeight;
        int width = inputWidth;
        if (height % 2 == 0) {
            height++;
        }
        if (width % 2 == 0) {
            width++;
        }
        Cell[][] grid = new Cell[height][width];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (row % 2 == 0 || col % 2 == 0) {
                    grid[row][col] = new Cell(row, col, Cell.Type.WALL);
                } else {
                    grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE);
                }
            }
        }
        return grid;
    }

    public static void removePartitionBetweenCells(Cell[][] grid, Cell currentCell, Cell neighboringCell) {
        if (currentCell.row() == neighboringCell.row()) {
            removePartitionInRow(grid, currentCell, neighboringCell);
        }
        if (currentCell.col() == neighboringCell.col()) {
            removePartitionInColumn(grid, currentCell, neighboringCell);
        }
    }

    private static void removePartitionInRow(Cell[][] grid, Cell currentCell, Cell neighboringCell) {
        if (currentCell.col() - neighboringCell.col() < 0) {
            grid[currentCell.row()][currentCell.col() + 1] =
                new Cell(currentCell.row(), currentCell.col() + 1, Cell.Type.PASSAGE);
        } else {
            grid[currentCell.row()][currentCell.col() - 1] =
                new Cell(currentCell.row(), currentCell.col() - 1, Cell.Type.PASSAGE);
        }
    }

    private static void removePartitionInColumn(Cell[][] grid, Cell currentCell, Cell neighboringCell) {
        if (currentCell.row() - neighboringCell.row() < 0) {
            grid[currentCell.row() + 1][currentCell.col()] =
                new Cell(currentCell.row() + 1, currentCell.col(), Cell.Type.PASSAGE);
        } else {
            grid[currentCell.row() - 1][currentCell.col()] =
                new Cell(currentCell.row() - 1, currentCell.col(), Cell.Type.PASSAGE);
        }
    }

    public static List<Cell> getUnvisitedNeighbors(Cell[][] grid, Cell currentCell, Set<Cell> visitedCells) {
        List<Cell> neighbouringCells = new ArrayList<>();
        getLeftCellIfUnvisited(grid, currentCell, visitedCells, neighbouringCells);
        getRightCellIfUnvisited(grid, currentCell, visitedCells, neighbouringCells);
        getBottomCellIfUnvisited(grid, currentCell, visitedCells, neighbouringCells);
        getTopCellIfUnvisited(grid, currentCell, visitedCells, neighbouringCells);
        return neighbouringCells;
    }

    private static void getLeftCellIfUnvisited(
        Cell[][] grid,
        Cell currentCell,
        Set<Cell> visitedCells,
        List<Cell> neighbouringCells
    ) {
        if (grid[currentCell.row() - 1][currentCell.col()].type() == Cell.Type.PASSAGE
            && !visitedCells.contains(grid[currentCell.row() - 1][currentCell.col()])) {
            neighbouringCells.add(grid[currentCell.row() - 1][currentCell.col()]);
        }
    }

    private static void getRightCellIfUnvisited(
        Cell[][] grid,
        Cell currentCell,
        Set<Cell> visitedCells,
        List<Cell> neighbouringCells
    ) {
        if (grid[currentCell.row() + 1][currentCell.col()].type() == Cell.Type.PASSAGE
            && !visitedCells.contains(grid[currentCell.row() + 1][currentCell.col()])) {
            neighbouringCells.add(grid[currentCell.row() + 1][currentCell.col()]);
        }
    }

    private static void getBottomCellIfUnvisited(
        Cell[][] grid,
        Cell currentCell,
        Set<Cell> visitedCells,
        List<Cell> neighbouringCells
    ) {
        if (grid[currentCell.row()][currentCell.col() - 1].type() == Cell.Type.PASSAGE
            && !visitedCells.contains(grid[currentCell.row()][currentCell.col() - 1])) {
            neighbouringCells.add(grid[currentCell.row()][currentCell.col() - 1]);
        }
    }

    private static void getTopCellIfUnvisited(
        Cell[][] grid,
        Cell currentCell,
        Set<Cell> visitedCells,
        List<Cell> neighbouringCells
    ) {
        if (grid[currentCell.row()][currentCell.col() + 1].type() == Cell.Type.PASSAGE
            && !visitedCells.contains(grid[currentCell.row()][currentCell.col() + 1])) {
            neighbouringCells.add(grid[currentCell.row()][currentCell.col() + 1]);
        }
    }

    public static List<Cell> getUnvisitedNeighborsBehindTheWall(
        Cell[][] grid,
        Cell currentCell,
        Set<Cell> visitedCells
    ) {
        List<Cell> neighbouringCells = new ArrayList<>();
        getLeftCellBehindTheWallIfUnvisited(grid, currentCell, visitedCells, neighbouringCells);
        getRightCellBehindTheWallIfUnvisited(grid, currentCell, visitedCells, neighbouringCells);
        getBottomCellBehindTheWallIfUnvisited(grid, currentCell, visitedCells, neighbouringCells);
        getTopCellBehindTheWallIfUnvisited(grid, currentCell, visitedCells, neighbouringCells);
        return neighbouringCells;
    }

    private static void getLeftCellBehindTheWallIfUnvisited(
        Cell[][] grid,
        Cell currentCell,
        Set<Cell> visitedCells,
        List<Cell> neighbouringCells
    ) {
        Cell leftCell = new Cell(currentCell.row() - 2, currentCell.col(), Cell.Type.PASSAGE);
        if (currentCell.row() - 2 > 0 && !visitedCells.contains(leftCell)) {
            neighbouringCells.add(leftCell);
        }
    }

    private static void getRightCellBehindTheWallIfUnvisited(
        Cell[][] grid,
        Cell currentCell,
        Set<Cell> visitedCells,
        List<Cell> neighbouringCells
    ) {
        Cell rightCell = new Cell(currentCell.row() + 2, currentCell.col(), Cell.Type.PASSAGE);
        if (currentCell.row() + 2 < grid.length && !visitedCells.contains(rightCell)) {
            neighbouringCells.add(rightCell);
        }
    }

    private static void getBottomCellBehindTheWallIfUnvisited(
        Cell[][] grid,
        Cell currentCell,
        Set<Cell> visitedCells,
        List<Cell> neighbouringCells
    ) {
        Cell bottomCell = new Cell(currentCell.row(), currentCell.col() - 2, Cell.Type.PASSAGE);
        if (currentCell.col() - 2 > 0 && !visitedCells.contains(bottomCell)) {
            neighbouringCells.add(bottomCell);
        }
    }

    private static void getTopCellBehindTheWallIfUnvisited(
        Cell[][] grid,
        Cell currentCell,
        Set<Cell> visitedCells,
        List<Cell> neighbouringCells
    ) {
        Cell topCell = new Cell(currentCell.row(), currentCell.col() + 2, Cell.Type.PASSAGE);
        if (currentCell.col() + 2 < grid[0].length && !visitedCells.contains(topCell)) {
            neighbouringCells.add(topCell);
        }
    }
}
