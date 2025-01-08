package com.example.chessfinalproject.model.pieces;

import com.example.chessfinalproject.model.ChessBoard;

import java.util.ArrayList;
import java.util.Objects;

public class Pawn extends Piece {

    // Constructor for the Pawn piece, initializing its position and color
    public Pawn(int row, int col, String color) {
        super(row, col, color);
    }

    /**
     * Generates all valid moves for the Pawn.
     * Pawns move forward one square, or two squares from their initial position, and capture diagonally.
     *
     * @param board The current state of the chessboard.
     * @return A list of valid moves as int[] arrays where int[0] is the row and int[1] is the column.
     */
    @Override
    public ArrayList<int[]> getValidMove(ChessBoard board) {
        ArrayList<int[]> validMoves = new ArrayList<>();

        // Determine move direction based on pawn color
        int direction = this.color.equals("white") ? -1 : 1;

        // Move forward one square
        int forwardRow = super.row + direction;
        if (super.isLocationOnBoard(forwardRow, super.col)) {
            Piece piece = board.getGrid()[forwardRow][super.col];
            if (piece == null) {
                validMoves.add(new int[]{forwardRow, super.col});
            }
        }

        // Move forward two squares if on the starting row
        int twoStepsForwardRow = super.row + 2 * direction;
        if (super.color.equals("white") && super.row == 6) {
            Piece piece = board.getGrid()[twoStepsForwardRow][super.col];
            if (piece == null) {
                validMoves.add(new int[]{twoStepsForwardRow, super.col});
            }
        }
        if (super.color.equals("black") && super.row == 1) {
            Piece piece = board.getGrid()[twoStepsForwardRow][super.col];
            if (piece == null) {
                validMoves.add(new int[]{twoStepsForwardRow, super.col});
            }
        }

        // Capture diagonally
        int[][] diagonalMoves = {{direction, -1}, {direction, 1}};
        for (int[] move : diagonalMoves) {
            int newRow = super.row + move[0];
            int newCol = super.col + move[1];

            // Check if the diagonal position is within the board and contains an opponent's piece
            if (super.isLocationOnBoard(newRow, newCol)) {
                Piece piece = board.getGrid()[newRow][newCol];
                if ( piece != null && !piece.color.equals(this.color)) {
                    validMoves.add(new int[]{newRow, newCol});
                }
            }
        }

        return validMoves;
    }

    @Override
    public String getImagePath() {
        return super.imageDirectory + this.color + "_pawn.png";
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
        if (!(o instanceof Pawn)) return false;
        if (!super.equals(o)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "Pawn{" +
                ", location=(" + this.row + ", " + this.col + ")" +
                ", color='" + color + '\'' +
                '}';
    }
}

