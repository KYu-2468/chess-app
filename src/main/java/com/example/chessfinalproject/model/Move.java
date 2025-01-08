package com.example.chessfinalproject.model;

import com.example.chessfinalproject.model.pieces.Piece;
import java.util.Objects;

public class Move {
    private Player player;
    private Piece piece;
    private int prevRow;
    private int prevCol;
    private int newRow;
    private int newCol;

    public Move(Player player, Piece piece, int prevRow, int prevCol, int newRow, int newCol) {
        this.player = player;
        this.piece = piece;
        this.prevRow = prevRow;
        this.prevCol = prevCol;
        this.newRow = newRow;
        this.newCol = newCol;
    }

    public Move(Move move) {
        this(move.player, move.piece, move.prevRow, move.prevCol, move.newRow, move.newCol);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getPrevRow() {
        return prevRow;
    }

    public void setPrevRow(int prevRow) {
        this.prevRow = prevRow;
    }

    public int getPrevCol() {
        return prevCol;
    }

    public void setPrevCol(int prevCol) {
        this.prevCol = prevCol;
    }

    public int getNewRow() {
        return newRow;
    }

    public void setNewRow(int newRow) {
        this.newRow = newRow;
    }

    public int getNewCol() {
        return newCol;
    }

    public void setNewCol(int newCol) {
        this.newCol = newCol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return Objects.equals(player, move.player) &&
                Objects.equals(piece, move.piece) &&
                prevRow == move.prevRow &&
                prevCol == move.prevCol &&
                newRow == move.newRow &&
                newCol == move.newCol;
    }

    @Override
    public String toString() {
        return "Move{" +
                "player=" + this.player +
                ", piece=" + this.piece +
                ", previousCell=(" + this.prevRow + ", " + this.prevCol + ")" +
                ", currentCell=(" + this.newRow + ", " + this.newCol + ")" +
                '}';
    }
}
