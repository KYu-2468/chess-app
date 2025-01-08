package com.example.chessfinalproject.model.pieces;

import com.example.chessfinalproject.model.ChessBoard;

import java.util.ArrayList;
import java.util.Objects;

public class Rook extends Piece {
    private boolean isFirstMove;

    public Rook(int row, int col, String color) {
        super(row, col, color);
        this.isFirstMove = true;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    @Override
    public ArrayList<int[]> getValidMove(ChessBoard board) {
        ArrayList<int[]> validMoves = new ArrayList<>();
        int[][] moves = {
                {1, 0}, {-1, 0},
                {0, 1}, {0, -1}
        };

        for (int[] move : moves) {
            int row = super.row + move[0];
            int col = super.col + move[1];

            while (super.isLocationOnBoard(row, col)) {
                Piece piece = board.getGrid()[row][col];
                if (piece == null) {
                    validMoves.add(new int[]{row, col});
                } else {
                    if (!piece.getColor().equals(super.color)) {
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
        return super.imageDirectory + this.color + "_rook.png";
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
        if (!(o instanceof Rook)) return false;
        if (!super.equals(o)) return false;
        Rook rook = (Rook) o;
        return isFirstMove == rook.isFirstMove;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "Rook{" +
                "isFirstMove=" + isFirstMove +
                ", location=(" + this.row + ", " + this.col + ")" +
                ", color='" + color + '\'' +
                '}';
    }
}

