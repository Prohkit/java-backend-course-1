package edu.project2;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BreadthFirstSearchSolverTest {
    @Test
    void solveMazeByBreadthFirstSearchSolver() {
        // given
        Cell[][] grid = new Cell[5][5];
        fillGrid(grid);
        Maze maze = new Maze(5, 5, grid);
        List<Coordinate> exceptedPath = Stream.of(
            new Coordinate(1, 1),
            new Coordinate(2, 1),
            new Coordinate(3, 1),
            new Coordinate(3, 2),
            new Coordinate(3, 3),
            new Coordinate(2, 3),
            new Coordinate(1, 3)
        ).toList();
        Coordinate startCoordinate = new Coordinate(1, 1);
        Coordinate endCoordinate = new Coordinate(1, 3);
        BreadthFirstSearchSolver solver = new BreadthFirstSearchSolver();

        // when
        List<Coordinate> result = solver.solve(maze, startCoordinate, endCoordinate);
        System.out.println(new MazeRenderer().render(maze, result));

        // then
        assertEquals(exceptedPath, result);
    }

    private void fillGrid(Cell[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == 0 || i == 4 || j == 0 || j == 4 || (i == 1 && j == 2) || (i == 2 && j == 2)) {
                    grid[i][j] = new Cell(i, j, Cell.Type.WALL);
                } else {
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                }
            }
        }
    }
}
