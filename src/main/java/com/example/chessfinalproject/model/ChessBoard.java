package com.example.chessfinalproject.model;

import com.example.chessfinalproject.model.pieces.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ChessBoard {
    private Piece[][] grid;
    private String[][] gridOriginalColor;
    public final King whiteKing;
    public final King blackKing;

    public ChessBoard() {
        grid = new Piece[8][8];
        this.blackKing = new King(0, 4, "black");
        this.whiteKing = new King(7, 4, "white");
        initializeChessBoard();
        initializeGridColor();
    }

    public void initializeGridColor() {
        this.gridOriginalColor = new String[8][8];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                this.gridOriginalColor[row * 2][col * 2] = "white";
                this.gridOriginalColor[row * 2][col * 2 + 1] = "green";
            }
        }

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                this.gridOriginalColor[row * 2 + 1][col * 2] = "green";
                this.gridOriginalColor[row * 2 + 1][col * 2 + 1] = "white";
            }
        }

    }

    public String getGridOriginalColor(int row, int col) {
        return gridOriginalColor[row][col];
    }

    public void initializeChessBoard() {
        initializeBlackPieces();
        initializeWhitePieces();
        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                grid[row][col] = null;
            }
        }
    }

    public void initializeBlackPieces() {
        this.grid[0][0] = new Rook(0,0,"black");
        this.grid[0][1] = new Knight(0, 1, "black");
        this.grid[0][2] = new Bishop(0, 2, "black");
        this.grid[0][3] = new Queen(0, 3, "black");
        this.grid[0][4] = this.blackKing;
        this.grid[0][5] = new Bishop(0, 5, "black");
        this.grid[0][6] = new Knight(0, 6, "black");
        this.grid[0][7] = new Rook(0, 7, "black");

        for (int col = 0; col < 8; col++) {
            this.grid[1][col] = new Pawn(1, col, "black");
        }
    }

    public void initializeWhitePieces() {
        this.grid[7][0] = new Rook(7, 0, "white");
        this.grid[7][1] = new Knight(7, 1, "white");
        this.grid[7][2] = new Bishop(7, 2, "white");
        this.grid[7][3] = new Queen(7, 3, "white");
        this.grid[7][4] = this.whiteKing;
        this.grid[7][5] = new Bishop(7, 5, "white");
        this.grid[7][6] = new Knight(7, 6, "white");
        this.grid[7][7] = new Rook(7, 7, "white");

        for (int col = 0; col < 8; col++) {
            this.grid[6][col] = new Pawn(6, col, "white");
        }

    }

    public Piece[][] getGrid() {
        return grid;
    }

    public ArrayList<Piece> getAllPieces(String color) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (this.grid[row][col] == null) {
                    continue;
                }
                if (this.grid[row][col].getColor().equals(color)) {
                    pieces.add(this.grid[row][col]);
                }
            }
        }
        return pieces;
    }

    public boolean[][] getAttackingLocationsByColor(String color) {
        ArrayList<Piece> pieces = this.getAllPieces(color);
        boolean[][] attackingLocations = new boolean[8][8];

        for (Piece piece : pieces) {
            if (piece instanceof King) {
                for (int row = piece.getRow() - 1; row <= piece.getRow() + 1; row++) {
                    for (int col = piece.getCol() - 1; col <= piece.getCol() + 1; col++) {
                        if (!piece.isLocationOnBoard(row, col) ||
                                (row == piece.getRow() && col == piece.getCol())) {
                            continue;
                        }
                        attackingLocations[row][col] = true;
                    }
                }
                continue;
            }
            for (int[] location : piece.getValidMove(this)) {
                attackingLocations[location[0]][location[1]] = true;
            }
        }
        return attackingLocations;
    }

    public void setGrid(Piece[][] grid) {
        this.grid = grid;
    }

    public void move(Piece piece, int row, int col) {
        int originalRow = piece.getRow();
        int originalCol = piece.getCol();
        this.grid[originalRow][originalCol] = null;
        this.grid[row][col] = piece;
        piece.setRow(row);
        piece.setCol(col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard)) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(grid, that.grid);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "grid=" + Arrays.deepToString(grid) +
                '}';
    }
}

