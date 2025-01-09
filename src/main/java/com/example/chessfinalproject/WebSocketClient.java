package com.example.chessfinalproject;

import com.example.chessfinalproject.model.ChessGame;
import com.example.chessfinalproject.model.Move;
import com.example.chessfinalproject.model.Player;
import com.example.chessfinalproject.model.pieces.Piece;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.application.Platform;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Random;

public class WebSocketClient {
    private Socket socket;
    public final String PIECE_MOVE = "piece:move";
    public final String MATCH_PENDING = "match:pending";
    public final String MATCH_SETUP= "match:setup";
    private int playerId;

    public WebSocketClient(ChessGame game, ChessController controller) {
        try {
            // Connect to the WebSocket server
            socket = IO.socket("https://chess-app-17sc.onrender.com"); // Online
            // socket = IO.socket("http://localhost:8080"); // Local

            Random rand = new Random();
            this.playerId = rand.nextInt(1000);

            // Event: On connection
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Connected to the server");
                    socket.emit(MATCH_PENDING, playerId);
                }
            });

            // Event: On receiving game setup
            socket.on(MATCH_SETUP, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if ((int) args[0] != playerId && (int) args[1] != playerId) {
                        return;
                    }
                    Platform.runLater(controller::removeLoadingOverlay);
                    Platform.runLater(controller::startGameOverlay);
                    game.setOnlineGame(true);
                    if ((int) args[0] == playerId) {
                        Player player1 = new Player(args[0].toString(), "white");
                        Player player2 = new Player(args[1].toString(), "black");
                        game.setPlayer1(player1);
                        game.setPlayer2(player2);
                        game.setPlayerToMove(player1);
                    } else {
                        Player player1 = new Player(args[1].toString(), "black");
                        Player player2 = new Player(args[0].toString(), "white");
                        game.setPlayer1(player1);
                        game.setPlayer2(player2);
                        game.setPlayerToMove(player2);
                    }
                }
            });

            // Event: On receiving an opponent move
            socket.on(PIECE_MOVE, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if ((int) args[0] == playerId) {
                        return;
                    }
                    int originalRow = (int) args[1];
                    int originalCol = (int) args[2];
                    int newRow = (int) args[3];
                    int newCol = (int) args[4];
                    Piece piece = game.getBoard().getGrid()[originalRow][originalCol];
                    game.getBoard().move(piece, newRow, newCol);
                    if (game.getPlayer2().equals("white")) {
                        if (game.getBoard().blackKing.isInCheckMate(game.getBoard())) {
                            game.setWinner(game.getPlayer2());
                        }
                    } else {
                        if (game.getBoard().whiteKing.isInCheckMate(game.getBoard())) {
                            game.setWinner(game.getPlayer2());
                        }
                    }

                    game.setPlayerToMove(game.getPlayer1());
                    game.getMoves().add(new Move(game.getPlayer2(),
                            piece, originalRow, originalCol, newRow, newCol));
                }
            });

            // Event: On disconnection
            socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Disconnected from server");
                }
            });

            // Connect the client
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void emitMove(int prevRow, int prevCol, int newRow, int newCol) {
        if (socket != null && socket.connected()) {
            socket.emit(PIECE_MOVE, this.playerId, prevRow, prevCol, newRow, newCol);
        } else {
            System.out.println("Socket is not connected.");
        }
    }

    public void endConnection() {
        if (socket != null && socket.connected()) {
            socket.disconnect(); // Disconnect the socket
            socket.close();      // Close the socket connection
        } else {
            System.out.println("Socket is not connected.");
        }
    }
}
