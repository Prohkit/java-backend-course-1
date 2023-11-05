package edu.project2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import static edu.project2.MazeUtils.generateGrid;
import static edu.project2.MazeUtils.getUnvisitedNeighborsBehindTheWall;
import static edu.project2.MazeUtils.removePartitionBetweenCells;

public class BreadthFirstSearchMazeGenerator implements Generator {

    private static final Set<Cell> VISITED_CELL = new HashSet<>();

    @Override
    public Maze generate(int height, int width) {
        Cell[][] grid = generateGrid(height, width);
        Random random = new Random();
        Queue<Cell> cellQueue = new LinkedList<>();

        Cell startCell = new Cell(1, 1, Cell.Type.PASSAGE);
        cellQueue.add(startCell);
        VISITED_CELL.add(startCell);

        while (!cellQueue.isEmpty()) {
            Cell currentCell = cellQueue.poll();
            List<Cell> neighbouringCells = getUnvisitedNeighborsBehindTheWall(grid, currentCell, VISITED_CELL);

            for (int i = 0; i < neighbouringCells.size(); i++) {
                Cell neighbouringCell = neighbouringCells.get(random.nextInt(neighbouringCells.size()));
                int row = neighbouringCell.row();
                int col = neighbouringCell.col();
                if (!VISITED_CELL.contains(neighbouringCell)) {
                    removePartitionBetweenCells(grid, currentCell, neighbouringCell);
                    cellQueue.add(grid[row][col]);
                    VISITED_CELL.add(grid[row][col]);
                }
            }
        }
        return new Maze(height, width, grid);
    }
}
