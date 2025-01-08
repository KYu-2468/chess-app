package com.example.chessfinalproject.model.pieces;

import com.example.chessfinalproject.model.ChessBoard;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a King chess piece.
 * The King can move one square in any direction and has special moves like castling.
 */
public class King extends Piece {
    private boolean isFirstMove;

    /**
     * Constructor for a King piece.
     * @param row The initial row position of the King.
     * @param col The initial column position of the King.
     * @param color The color of the King ("white" or "black").
     */
    public King(int row, int col, String color) {
        super(row, col, color);
        this.isFirstMove = true;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    /**
     * Computes all valid moves for the King based on its current position and the state of the board.
     * This includes normal moves and castling if allowed.
     * @param board The current chess board.
     * @return A list of valid moves for the King, each represented as an array of row and column.
     */
    @Override
    public ArrayList<int[]> getValidMove(ChessBoard board) {
        ArrayList<int[]> validMoves = new ArrayList<>();

        // Check all surrounding squares
        for (int curRow = row - 1; curRow <= row + 1; curRow++) {
            for (int curCol = col - 1; curCol <= col + 1; curCol++) {
                if ((curRow == row && curCol == col) || !super.isLocationOnBoard(curRow, curCol)) {
                    continue;
                }

                Piece piece = board.getGrid()[curRow][curCol];
                if (piece != null && piece.color.equals(this.color)) {
                    continue;
                }
                if (this.willBeChecked(board, curRow, curCol)) {
                    continue;
                }
                validMoves.add(new int[]{curRow, curCol});
            }
        }

        // Check for castling options
        if (this.canKingSideCastle(board)) {
            validMoves.add(new int[]{this.row, 6});
        }
        if (this.canQueenSideCastle(board)) {
            validMoves.add(new int[]{this.row, 2});
        }

        return validMoves;
    }

    /**
     * Gets all surrounding locations of the King.
     * @return A list of surrounding locations, each represented as an array of row and column.
     */
    public ArrayList<int[]> getSurroundingLocation() {
        ArrayList<int[]> surrounding = new ArrayList<>();
        for (int row = this.row - 1; row <= this.row + 1; row++) {
            for (int col = this.col - 1; col <= this.col + 1; col++) {
                if (super.isLocationOnBoard(row, col)) {
                    surrounding.add(new int[]{row, col});
                }
            }
        }
        return surrounding;
    }

    public String getOpponentsColor() {
        return this.color.equals("white") ? "black": "white";
    }

    /**
     * Simulates a move and checks if the King would be in check after the move.
     * @param board The current chess board.
     * @param targetRow The row to move to.
     * @param targetCol The column to move to.
     * @return True if the King would be in check, false otherwise.
     */
    public boolean willBeChecked(ChessBoard board, int targetRow, int targetCol) {
        Piece originalPiece = board.getGrid()[targetRow][targetCol];
        int originalRow = this.row;
        int originalCol = this.col;
        board.move(this, targetRow, targetCol);
        boolean willBeChecked = this.isInCheck(board);
        board.move(this, originalRow, originalCol);

        if (originalPiece != null) {
            board.move(originalPiece, targetRow, targetCol);
        }
        return willBeChecked;
    }

    /**
     * Checks if the King is currently in check.
     * @param board The current chess board.
     * @return True if the King is in check, false otherwise.
     */
    public boolean isInCheck(ChessBoard board) {
        ArrayList<Piece> enemyPieces = board.getAllPieces(this.getOpponentsColor());
        for (Piece piece : enemyPieces) {
            if (piece instanceof King king) {
                ArrayList<int[]> surrounding = king.getSurroundingLocation();
                for (int[] location : surrounding) {
                    if (location[0] == this.row && location[1] == this.col) {
                        return true;
                    }
                }
                continue;
            }
            ArrayList<int[]> validMoves = piece.getValidMove(board);
            for (int[] validMove : validMoves) {
                if (validMove[0] == this.row && validMove[1] == this.col) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the King is in checkmate.
     * @param board The current chess board.
     * @return True if the King is in checkmate, false otherwise.
     */
    public boolean isInCheckMate(ChessBoard board) {
        if (!this.isInCheck(board)) {
            return false;
        }
        if (!this.getValidMove(board).isEmpty()) {
            return false;
        }

        boolean isCheckMate = true;
        ArrayList<Piece> teamPieces= board.getAllPieces(this.color);
        for (Piece teamPiece : teamPieces) {
            if (teamPiece instanceof King) {
                continue;
            }
            int originalRow = teamPiece.getRow();
            int originalCol = teamPiece.getCol();
            ArrayList<int[]> possibleMoves = teamPiece.getValidMove(board);

            for (int[] targetMove : possibleMoves) {
                int targetRow = targetMove[0];
                int targetCol = targetMove[1];
                Piece targetMovePiece = board.getGrid()[targetRow][targetCol];
                board.move(teamPiece, targetRow, targetCol);
                isCheckMate = this.isInCheck(board);
                board.move(teamPiece, originalRow, originalCol);

                if (targetMovePiece != null) {
                    board.move(targetMovePiece, targetRow, targetCol);
                }

                if (!isCheckMate) {
                    return isCheckMate;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the King can perform a king-side castle.
     * @param board The current chess board.
     * @return True if king-side castling is possible, false otherwise.
     */
    public boolean canKingSideCastle(ChessBoard board) {
        if (!this.isFirstMove) {
            return false;
        }
        boolean[][] attackedLocations = board.getAttackingLocationsByColor(this.getOpponentsColor());

        if (this.color.equals("white")) {
            return board.getGrid()[7][7] instanceof Rook rook &&
                    rook.isFirstMove() &&
                    board.getGrid()[7][6] == null &&
                    board.getGrid()[7][5] == null &&
                    !attackedLocations[7][6] &&
                    !attackedLocations[7][5];
        } else {
            return board.getGrid()[0][7] instanceof Rook rook &&
                    rook.isFirstMove() &&
                    board.getGrid()[0][6] == null &&
                    board.getGrid()[0][5] == null &&
                    !attackedLocations[0][6] &&
                    !attackedLocations[0][5];
        }
    }

    /**
     * Checks if the King can perform a queen-side castle.
     * @param board The current chess board.
     * @return True if queen-side castling is possible, false otherwise.
     */
    public boolean canQueenSideCastle(ChessBoard board) {
        if (!this.isFirstMove) {
            return false;
        }

        boolean[][] attackedLocations = board.getAttackingLocationsByColor(this.getOpponentsColor());

        if (this.color.equals("white")) {
            return board.getGrid()[7][0] instanceof Rook rook &&
                    rook.isFirstMove() &&
                    board.getGrid()[7][1] == null &&
                    board.getGrid()[7][2] == null &&
                    board.getGrid()[7][3] == null &&
                    !attackedLocations[7][2] &&
                    !attackedLocations[7][3];
        } else {
            return board.getGrid()[0][0] instanceof Rook rook &&
                    rook.isFirstMove() &&
                    board.getGrid()[0][1] == null &&
                    board.getGrid()[0][2] == null &&
                    board.getGrid()[0][3] == null &&
                    !attackedLocations[0][2] &&
                    !attackedLocations[0][3];
        }
    }

    @Override
    public String getImagePath() {
        return super.imageDirectory + this.color + "_king.png";
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
        if (!(o instanceof King)) return false;
        if (!super.equals(o)) return false;
        King king = (King) o;
        return isFirstMove == king.isFirstMove;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isFirstMove);
    }

    @Override
    public String toString() {
        return "King{" +
                "isFirstMove=" + isFirstMove +
                ", location=(" + this.row + ", " + this.col + ")" +
                ", color='" + color + '\'' +
                '}';
    }
}

