package edu.project2;

import java.util.List;

public class MazeRenderer implements Renderer {
    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();
        Cell[][] grid = maze.getGrid();
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.type().equals(Cell.Type.WALL)) {
                    sb.append('*');
                } else {
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }
        return new String(sb);
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder sb = new StringBuilder();
        Cell[][] grid = maze.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (path.contains(new Coordinate(i, j))) {
                    sb.append('#');
                } else if (grid[i][j].type().equals(Cell.Type.WALL)) {
                    sb.append('*');
                } else {
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }
        return new String(sb);
    }
}
