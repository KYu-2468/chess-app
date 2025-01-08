package com.example.chessfinalproject.model.pieces;

import com.example.chessfinalproject.model.ChessBoard;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a Bishop chess piece.
 * A Bishop moves diagonally on the chessboard.
 */
public class Bishop extends Piece {
    /**
     * Constructor for a Bishop piece.
     * @param row The initial row position of the Bishop.
     * @param col The initial column position of the Bishop.
     * @param color The color of the Bishop ("white" or "black").
     */
    public Bishop(int row, int col, String color) {
        super(row, col, color);
    }

    /**
     * Computes all valid moves for the Bishop based on its current position and the state of the board.
     * @param board The current chess board.
     * @return A list of valid moves for the Bishop, each represented as an array of row and column.
     */
    @Override
    public ArrayList<int[]> getValidMove(ChessBoard board) {
        ArrayList<int[]> validMoves = new ArrayList<>();

        // Directions the Bishop can move in (diagonals)
        int[][] moves = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        // Check all diagonal moves
        for (int[] move : moves) {
            int row = super.row + move[0];
            int col = super.col + move[1];

            while (super.isLocationOnBoard(row, col)) {
                Piece piece = board.getGrid()[row][col];
                if (piece == null) {
                    validMoves.add(new int[]{row, col});
                } else if (!piece.color.equals(super.color)) {
                    validMoves.add(new int[]{row, col});
                    break;
                } else {
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
        return super.imageDirectory + this.color + "_bishop.png";
    }

    /**
     * Checks if a move to a specified position is valid for the Bishop.
     * @param board The current chess board.
     * @param row The row position of the move.
     * @param col The column position of the move.
     * @return True if the move is valid, false otherwise.
     */
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
        if (!(o instanceof Bishop)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "Bishop{" +
                "location=(" + this.row + ", " + this.col + ") " +
                ", color='" + color + '\'' +
                '}';
    }
}

