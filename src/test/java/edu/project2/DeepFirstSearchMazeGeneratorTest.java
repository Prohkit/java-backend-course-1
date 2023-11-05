package edu.project2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static edu.project2.MazeUtils.removePartitionBetweenCells;

public class DeepFirstSearchMazeGeneratorTest {
    @Test
    void generateMazeByDeepFirstSearchGenerator() {
        DeepFirstSearchMazeGenerator generator = new DeepFirstSearchMazeGenerator();
        int height = 15;
        int width = 15;
        Maze maze = generator.generate(height, width);
        assertNotNull(maze);
        assertEquals(height, maze.getHeight());
        assertEquals(width, maze.getWidth());
        System.out.println(new MazeRenderer().render(maze));
    }

    @Test
    void removeWall() {
        // given
        Cell currentCell = new Cell(0, 1, Cell.Type.PASSAGE);
        Cell wallCell = new Cell(0, 2, Cell.Type.WALL);
        Cell neighboringCell = new Cell(0, 3, Cell.Type.PASSAGE);
        Cell[][] grid = {{currentCell, wallCell, neighboringCell}};

        // when
        removePartitionBetweenCells(grid, currentCell, neighboringCell);
        Cell expectedCell = new Cell(0, 2, Cell.Type.PASSAGE);

        // then
        assertEquals(expectedCell, grid[wallCell.row()][wallCell.col()]);
    }
}
