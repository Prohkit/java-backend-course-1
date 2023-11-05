package edu.project2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import static edu.project2.MazeUtils.getUnvisitedNeighbors;

public class BacktrackingMethodSolver implements Solver {

    private static final Set<Cell> VISITED_CELL = new HashSet<>();

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        List<Coordinate> result = new ArrayList<>();
        Cell[][] grid = maze.getGrid();
        Cell currentCell = grid[start.row()][start.col()];
        VISITED_CELL.add(currentCell);
        Stack<Cell> cellStack = new Stack<>();
        Random random = new Random();
        while (!(currentCell.row() == end.row() && currentCell.col() == end.col())) {
            List<Cell> neighbouringCells = getUnvisitedNeighbors(grid, currentCell, VISITED_CELL);
            if (!neighbouringCells.isEmpty()) {
                cellStack.push(currentCell);
                result.add(new Coordinate(currentCell.row(), currentCell.col()));
                currentCell = neighbouringCells.get(random.nextInt(neighbouringCells.size()));
                VISITED_CELL.add(currentCell);
            } else if (!cellStack.isEmpty()) {
                currentCell = cellStack.pop();
            } else {
                return null;
            }
        }
        result.add(new Coordinate(currentCell.row(), currentCell.col()));
        return result;
    }
}
