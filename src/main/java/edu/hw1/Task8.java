package edu.hw1;

public class Task8 {

    private Task8() {
    }

    private static boolean canCapture(byte[][] chessBoard, int i, int j) {
        // конь ходит из точки A в точку B
        boolean canCapture = false;
        // 0 0 A
        // B 0 0
        if (i < chessBoard.length - 1 && j > 1) {
            if (chessBoard[i + 1][j - 2] == 1) {
                canCapture = true;
            }
        }
        // 0 A
        // 0 0
        // B 0
        if (i < chessBoard.length - 2 && j > 0) {
            if (chessBoard[i + 2][j - 1] == 1) {
                canCapture = true;
            }
        }
        // A 0 0
        // 0 0 B
        if (i < chessBoard.length - 1 && j < chessBoard[i].length - 2) {
            if (chessBoard[i + 1][j + 2] == 1) {
                canCapture = true;
            }
        }
        // A 0
        // 0 0
        // 0 B
        if (i < chessBoard.length - 2 && j < chessBoard[i].length - 1) {
            if (chessBoard[i + 2][j + 1] == 1) {
                canCapture = true;
            }
        }
        return canCapture;
    }

    public static boolean knightBoardCapture(byte[][] chessBoard) {
        // обходим шахматную доску сверху вниз
        boolean result = true;
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                if (chessBoard[i][j] == 1) {
                    result = !canCapture(chessBoard, i, j);
                }
                if (!result) {
                    return false;
                }
            }
        }
        return true;
    }
}
