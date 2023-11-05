package edu.project2;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import static edu.project2.MazeUtils.generateGrid;
import static edu.project2.MazeUtils.getUnvisitedNeighborsBehindTheWall;
import static edu.project2.MazeUtils.removePartitionBetweenCells;

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
            List<Cell> neighbouringCells = getUnvisitedNeighborsBehindTheWall(grid, currentCell, VISITED_CELL);
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
}
