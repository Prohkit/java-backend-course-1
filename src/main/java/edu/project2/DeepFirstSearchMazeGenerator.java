package edu.project2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class DeepFirstSearchMazeGenerator implements Generator {

    private static final Set<Cell> VISITED_CELL = new HashSet<>();

    @Override
    public Maze generate(int height, int width) {
        Cell[][] grid = generateGrid(height, width);

        Cell currentCell = new Cell(1, 1, Cell.Type.PASSAGE);
        VISITED_CELL.add(currentCell);
        Stack<Cell> cellStack = new Stack<>();
        cellStack.push(currentCell);
        Random random = new Random();
        while (!cellStack.isEmpty()) {
            currentCell = cellStack.pop();
            List<Cell> neighbouringCells = getUnvisitedNeighborsBehindTheWall(grid, currentCell);
            if (!neighbouringCells.isEmpty()) {
                cellStack.push(currentCell);
                Cell neighboringCell = neighbouringCells.get(random.nextInt(neighbouringCells.size()));
                removePartitionBetweenCells(grid, currentCell, neighboringCell);
                VISITED_CELL.add(neighboringCell);
                cellStack.push(neighboringCell);
            }
        }
        return new Maze(height, width, grid);
    }

    private Cell[][] generateGrid(int inputHeight, int inputWidth) {
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

    public void removePartitionBetweenCells(Cell[][] grid, Cell currentCell, Cell neighboringCell) {
        if (currentCell.row() == neighboringCell.row()) {
            if (currentCell.col() - neighboringCell.col() < 0) {
                grid[currentCell.row()][currentCell.col() + 1] =
                    new Cell(currentCell.row(), currentCell.col() + 1, Cell.Type.PASSAGE);
            } else {
                grid[currentCell.row()][currentCell.col() - 1] =
                    new Cell(currentCell.row(), currentCell.col() - 1, Cell.Type.PASSAGE);
            }
        }
        if (currentCell.col() == neighboringCell.col()) {
            if (currentCell.row() - neighboringCell.row() < 0) {
                grid[currentCell.row() + 1][currentCell.col()] =
                    new Cell(currentCell.row() + 1, currentCell.col(), Cell.Type.PASSAGE);
            } else {
                grid[currentCell.row() - 1][currentCell.col()] =
                    new Cell(currentCell.row() - 1, currentCell.col(), Cell.Type.PASSAGE);
            }
        }
    }

    private List<Cell> getUnvisitedNeighborsBehindTheWall(Cell[][] grid, Cell currentCell) {
        List<Cell> neighbouringCells = new ArrayList<>();
        Cell leftCell = new Cell(currentCell.row() - 2, currentCell.col(), Cell.Type.PASSAGE);
        if (currentCell.row() - 2 > 0 && !VISITED_CELL.contains(leftCell)) {
            neighbouringCells.add(leftCell);
        }
        Cell rightCell = new Cell(currentCell.row() + 2, currentCell.col(), Cell.Type.PASSAGE);
        if (currentCell.row() + 2 < grid.length && !VISITED_CELL.contains(rightCell)) {
            neighbouringCells.add(rightCell);
        }
        Cell bottomCell = new Cell(currentCell.row(), currentCell.col() - 2, Cell.Type.PASSAGE);
        if (currentCell.col() - 2 > 0 && !VISITED_CELL.contains(bottomCell)) {
            neighbouringCells.add(bottomCell);
        }
        Cell topCell = new Cell(currentCell.row(), currentCell.col() + 2, Cell.Type.PASSAGE);
        if (currentCell.col() + 2 < grid[0].length && !VISITED_CELL.contains(topCell)) {
            neighbouringCells.add(topCell);
        }
        return neighbouringCells;
    }
}
