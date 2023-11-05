package edu.project2;

import java.util.List;

public class MazeRenderer implements Renderer {
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m   ";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m   ";
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m   ";
    private static final String ANSI_RESET = "\u001B[0m";

    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();
        Cell[][] grid = maze.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].type() == Cell.Type.WALL) {
                    sb.append(ANSI_WHITE_BACKGROUND);
                } else {
                    sb.append(ANSI_BLACK_BACKGROUND);
                }
            }
            sb.append(ANSI_RESET + '\n');
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
                    sb.append(ANSI_YELLOW_BACKGROUND);
                } else if (grid[i][j].type().equals(Cell.Type.WALL)) {
                    sb.append(ANSI_BLACK_BACKGROUND);
                } else {
                    sb.append(ANSI_WHITE_BACKGROUND);
                }
            }
            sb.append(ANSI_RESET + '\n');
        }
        return new String(sb);
    }
}
