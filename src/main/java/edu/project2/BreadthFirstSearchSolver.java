package edu.project2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import static edu.project2.MazeUtils.getUnvisitedNeighbors;

public class BreadthFirstSearchSolver implements Solver {

    private static final Set<Cell> VISITED_CELL = new HashSet<>();

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        List<Coordinate> result = new ArrayList<>();
        result.add(start);
        Cell[][] grid = maze.getGrid();
        Cell startCell = grid[start.row()][start.col()];

        Queue<Cell> cellQueue = new LinkedList<>();
        cellQueue.add(startCell);
        VISITED_CELL.add(startCell);

        while (!cellQueue.isEmpty()) {
            Cell currentCell = cellQueue.poll();
            if (currentCell.row() == end.row() && currentCell.col() == end.col()) {
                return result;
            }
            VISITED_CELL.add(currentCell);
            List<Cell> neighbouringCells = getUnvisitedNeighbors(grid, currentCell, VISITED_CELL);
            for (Cell neighbouringCell : neighbouringCells) {
                if (neighbouringCell.type() == Cell.Type.PASSAGE) {
                    cellQueue.offer(neighbouringCell);
                    result.add(new Coordinate(neighbouringCell.row(), neighbouringCell.col()));
                }
            }
        }
        return null;
    }
}
