package com.example.chessfinalproject.model.pieces;

import com.example.chessfinalproject.model.ChessBoard;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents an abstract chess piece on a chess board.
 * Each piece has a position, color, and state of being captured or not.
 */
public abstract class Piece {
    protected int row; // The row position of the piece on the chessboard (0-7)
    protected int col; // The column position of the piece on the chessboard (0-7)
    protected boolean isCaptured; // Indicates if the piece is captured
    protected String color; // The color of the piece ("white" or "black")
    public final String imageDirectory = "/com/example/chessfinalproject/image/";

    /**
     * Constructor for a chess piece.
     * @param row The initial row position of the piece.
     * @param col The initial column position of the piece.
     * @param color The color of the piece.
     */
    public Piece(int row, int col, String color) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.isCaptured = false;
    }

    public int getRow() {
        return this.row;
    }

    /**
     * Sets the row position of the piece.
     * @param row The new row position (must be between 0 and 7).
     * @throws IllegalArgumentException if the row is not in the valid range.
     */
    public void setRow(int row) throws IllegalArgumentException {
        if (row < 0 || row > 7) {
            throw new IllegalArgumentException("ERROR: Row can only be between 0 ~ 7.");
        }
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    /**
     * Sets the column position of the piece.
     * @param col The new column position (must be between 0 and 7).
     * @throws IllegalArgumentException if the column is not in the valid range.
     */
    public void setCol(int col) throws IllegalArgumentException {
        if (col < 0 || col > 7) {
            throw new IllegalArgumentException("ERROR: Col can only be between 0 ~ 7.");
        }
        this.col = col;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }

    public String getColor() {
        return color;
    }

    /**
     * Sets the color of the piece.
     * @param color The new color of the piece.
     */
    public void setColor(String color) throws IllegalArgumentException {
        if (!color.equals("white") && color.equals("black")) {
            throw new IllegalArgumentException("ERROR: Color can only be white or black.");
        }
        this.color = color;
    }

    /**
     * Checks if a given location is within the bounds of the chess board.
     * @param row The row position to check.
     * @param col The column position to check.
     * @return True if the location is on the board, false otherwise.
     */
    public boolean isLocationOnBoard(int row, int col) {
        return row < 8 && row >= 0 && col >= 0 && col < 8;
    }

    /**
     * Abstract method to get all valid moves for this piece on the given board.
     * @param board The current chess board.
     * @return A list of valid moves, each represented as an array of row and column.
     */
    public abstract ArrayList<int[]> getValidMove(ChessBoard board);

    /**
     * Abstract method to get the image path for this piece.
     * @return The image path for the piece.
     */
    public abstract String getImagePath();

    /**
     * Abstract method to check if a given move is valid for this piece.
     * @param board The current chess board.
     * @param row The row position of the move.
     * @param col The column position of the move.
     * @return True if the move is valid, false otherwise.
     */
    public abstract boolean isValidMove(ChessBoard board, int row, int col);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;
        Piece piece = (Piece) o;
        return isCaptured == piece.isCaptured &&
                this.row == piece.row &&
                this.col == piece.col &&
                this.color.equals(piece.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, isCaptured, color);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "location=(" + this.row + ", " + this.col +
                ", isCaptured=" + isCaptured +
                ", color='" + color + '\'' +
                '}';
    }
}