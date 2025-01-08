package com.example.chessfinalproject.model;

import com.example.chessfinalproject.ChessController;
import com.example.chessfinalproject.WebSocketClient;
import com.example.chessfinalproject.model.pieces.King;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Date;
import java.util.Objects;

public class ChessGame {
    private ChessBoard board;
    private Player player1;
    private Player player2;
    private Player playerToMove;
    private Player winner;
    private Date date;
    private WebSocketClient webSocketClient;
    private boolean isOnlineGame;
    private final ObservableList<Move> moves;
    IntegerProperty movesSize;

    public ChessGame() {
        this.board = new ChessBoard();
        this.player1 = new Player("player 1","white");
        this.player2 = new Player("player 2", "black");
        this.playerToMove = player1;
        this.winner = null;
        this.date = new Date();
        this.moves = FXCollections.observableArrayList();
        this.movesSize = new SimpleIntegerProperty(0);
        moves.addListener((ListChangeListener<Move>) change -> {
            movesSize.set(moves.size());
        });
        this.isOnlineGame = false;
    }

    public WebSocketClient getWebSocketClient() {
        return this.webSocketClient;
    }

    public void setWebSocketClient(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    public ChessGame(boolean isOnlineGame, ChessController controller) {
        this();
        if (isOnlineGame) {
            this.isOnlineGame = true;
            movesSize.addListener((observable, oldValue, newValue) -> {
                System.out.println("List size changed: " + newValue);
                Platform.runLater(controller::updateView);
            });
            this.initializeOnlineGame();
        }
    }

    public void initializeOnlineGame() {
        this.webSocketClient = new WebSocketClient(this);
    }

    public boolean isOnlineGame() {
        return isOnlineGame;
    }

    public void setOnlineGame(boolean onlineGame) {
        isOnlineGame = onlineGame;
    }

    public ChessBoard getBoard() {
        return board;
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayerToMove() {
        return playerToMove;
    }

    public void setPlayerToMove(Player playerToMove) {
        this.playerToMove = playerToMove;
    }

    public King getPlayerToMovesKing() {
        if (this.getPlayerToMove().getColor().equals("white")) {
            return this.board.whiteKing;
        } else {
            return this.board.blackKing;
        }
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public ObservableList<Move> getMoves() {
        return moves;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Move getLastMove() {
        return new Move(this.moves.getLast());
    }

    public void addMove(Move move) {
        this.moves.add(move);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessGame)) return false;
        ChessGame that = (ChessGame) o;
        return Objects.equals(board, that.board) &&
                Objects.equals(player1, that.player1) &&
                Objects.equals(player2, that.player2) &&
                Objects.equals(playerToMove, that.playerToMove) &&
                Objects.equals(winner, that.winner) &&
                Objects.equals(date, that.date);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "board=" + board +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", playerToMove=" + playerToMove +
                ", winner=" + winner +
                ", date=" + date +
                '}';
    }
}
