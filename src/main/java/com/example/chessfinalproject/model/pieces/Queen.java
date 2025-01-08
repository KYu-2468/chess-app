package com.example.chessfinalproject.model.pieces;

import com.example.chessfinalproject.model.ChessBoard;

import java.util.ArrayList;
import java.util.Objects;

public class Queen extends Piece {

    // Constructor for the Queen piece, initializing its position and color
    public Queen(int row, int col, String color) {
        super(row, col, color);
    }

    /**
     * Generates all valid moves for the Queen.
     * The Queen can move any number of squares along a rank, file, or diagonal, combining the power of the Rook and Bishop.
     *
     * @param board The current state of the chessboard.
     * @return A list of valid moves as int[] arrays where int[0] is the row and int[1] is the column.
     */
    @Override
    public ArrayList<int[]> getValidMove(ChessBoard board) {
        ArrayList<int[]> validMoves = new ArrayList<>();

        // Directions in which the Queen can move: vertical, horizontal, and diagonal
        int[][] moves = {
                {1, 0}, {-1, 0},  // Vertical moves
                {0, 1}, {0, -1},  // Horizontal moves
                {1, 1}, {1, -1},  // Diagonal moves
                {-1, 1}, {-1, -1} // Diagonal moves
        };

        // Iterate over each direction
        for (int[] move : moves) {
            int row = super.row + move[0];
            int col = super.col + move[1];

            // Continue moving in the same direction until the edge of the board or an obstacle
            while (super.isLocationOnBoard(row, col)) {
                Piece piece = board.getGrid()[row][col];

                // If the square is empty, it's a valid move
                if (piece == null) {
                    validMoves.add(new int[]{row, col});
                } else {
                    // If there's a piece, check if it's an opponent's piece
                    if (!piece.getColor().equals(this.color)) {
                        validMoves.add(new int[]{row, col});
                    }
                    break;
                }
                row += move[0];
                col += move[1];
            }
        }
        return validMoves;
    }

    @Override
    public String getImagePath() {
        return super.imageDirectory + this.color + "_queen.png";
    }

    @Override
    public boolean isValidMove (ChessBoard board, int row, int col) {
        ArrayList<int[]> validMoves = this.getValidMove(board);
        for (int[] move : validMoves) {
            if (move[0] == row && move[1] == col) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Queen)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "Queen{" +
                "location=(" + this.row + ", " + this.col + ") " +
                ", color='" + color + '\'' +
                '}';
    }
}

