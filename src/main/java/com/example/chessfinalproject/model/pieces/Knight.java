package com.example.chessfinalproject.model.pieces;

import com.example.chessfinalproject.model.ChessBoard;

import java.util.ArrayList;
import java.util.Objects;

public class Knight extends Piece {

    // Constructor for the Knight piece, initializes position and color
    public Knight(int row, int col, String color) {
        super(row, col, color);
    }

    /**
     * Generates all valid moves for the Knight.
     * A knight moves in an "L" shape: two squares in one direction and one in a perpendicular direction.
     *
     * @param board The current state of the chessboard.
     * @return A list of valid moves as int[] arrays where int[0] is the row and int[1] is the column.
     */
    @Override
    public ArrayList<int[]> getValidMove(ChessBoard board) {
        ArrayList<int[]> validMoves = new ArrayList<>();

        // Possible moves for a knight in terms of row and column offsets
        int[][] moves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        // Check each potential move
        for (int[] move : moves) {
            int targetRow = this.row + move[0];
            int targetCol = this.col + move[1];

            // Ensure the move is within the board limits
            if (super.isLocationOnBoard(targetRow, targetCol)) {
                Piece piece = board.getGrid()[targetRow][targetCol];

                // Add move if the target square is either empty or occupied by an opponent's piece
                if (piece == null || !piece.getColor().equals(this.color)) {
                    validMoves.add(new int[]{targetRow, targetCol});
                }
            }
        }

        return validMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Knight)) return false;
        return super.equals(o);
    }

    @Override
    public String getImagePath() {
        return super.imageDirectory + this.color + "_knight.png";
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
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "Knight{" +
                "location=(" + this.row + ", " + this.col + ") " +
                ", color='" + color + '\'' +
                '}';
    }
}

