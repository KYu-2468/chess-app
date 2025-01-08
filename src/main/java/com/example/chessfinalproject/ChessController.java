package com.example.chessfinalproject;

import com.example.chessfinalproject.model.ChessBoard;
import com.example.chessfinalproject.model.ChessGame;
import com.example.chessfinalproject.model.pieces.King;
import com.example.chessfinalproject.model.pieces.Piece;
import com.example.chessfinalproject.model.pieces.Rook;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ChessController {
    private ChessGame game;
    private ChessBoard board;
    @FXML
    private boolean isPieceSelected;
    @FXML
    private Piece selectedPiece;
    @FXML
    public GridPane chessBoard;
    @FXML
    public AnchorPane anchor;

    @FXML protected void playLocal() {
        game = new ChessGame();
        this.board = game.getBoard();
        this.updateView();
    }

    @FXML
    protected void playOnline(ActionEvent event) {
        game = new ChessGame(true, this);
        this.board = game.getBoard();
        this.updateView();
    }

    @FXML
    protected void onClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        Integer row = GridPane.getRowIndex(clickedButton);
        Integer col = GridPane.getColumnIndex(clickedButton);

        Piece pieceClicked = this.board.getGrid()[row][col];

        if (this.isPieceSelected) {
            if (selectedPiece.isValidMove(this.board, row, col)) {
                if (selectedPiece instanceof King king && king.isFirstMove()) {
                    // check if is castle
                    if (col == 6) {
                        if (this.game.isOnlineGame()) {
                            this.game.getWebSocketClient().emitMove(row, 7, row, col - 1);
                        } else {
                            board.move(board.getGrid()[row][7], row, col - 1);
                        }
                    } else if (col == 2) {
                        if (this.game.isOnlineGame()) {
                            this.game.getWebSocketClient().emitMove(row, 0, row, col + 1);
                        } else {
                            board.move(board.getGrid()[row][0], row, col + 1);
                        }
                    }
                }

                if (this.game.isOnlineGame()) {
                    this.game.getWebSocketClient().emitMove(selectedPiece.getRow(),
                            selectedPiece.getCol(),
                            row,
                            col);
                }
                board.move(selectedPiece, row, col);

                if (this.game.getPlayerToMove().getColor().equals("white")) {
                    if (this.board.blackKing.isInCheckMate(board)) {
                        // white win
                        this.game.setWinner(this.game.getPlayer1());
                    }
                    if (!this.game.isOnlineGame()) {
                        this.game.setPlayerToMove(this.game.getPlayer2());
                    }
                } else {
                    if (this.board.whiteKing.isInCheckMate(board)) {
                        // black win
                        this.game.setWinner(this.game.getPlayer2());
                    }
                    if (!this.game.isOnlineGame()) {
                        this.game.setPlayerToMove(this.game.getPlayer1());
                    }
                }

                if (this.selectedPiece instanceof King king) {
                    king.setFirstMove(false);
                } else if (this.selectedPiece instanceof Rook rook) {
                    rook.setFirstMove(false);
                }
            }

            this.isPieceSelected = false;
            this.selectedPiece = null;
        } else if (pieceClicked != null &&
                pieceClicked.getColor().equals(this.game.getPlayerToMove().getColor())) {
            if (this.game.isOnlineGame()) {
                if (!this.game.getPlayer1().getColor().equals(pieceClicked.getColor())) {
                    this.isPieceSelected = false;
                    this.selectedPiece = null;
                    this.updateView();
                    return;
                }
            }
            this.isPieceSelected = true;
            this.selectedPiece = this.board.getGrid()[row][col];
        } else {
            this.isPieceSelected = false;
            this.selectedPiece = null;
        }
        this.updateView();
    }

    public ChessController() {
        this.game = new ChessGame();
        this.board = game.getBoard();
    }

    private void setButtonImage(Button button, String imagePath) {
        button.setAlignment(javafx.geometry.Pos.CENTER);
        button.setPrefHeight(46.0);
        button.setPrefWidth(78.0);

        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitHeight(40.0);
        imageView.setFitWidth(44.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        button.setGraphic(imageView);
    }

    private void clearPiece(Button button) {
        button.setGraphic(null);
    }

    public Button getButtonAt(int row, int col) {
        for (Node node : chessBoard.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null &&
                    GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {

                if (node instanceof Button button) {
                    return button;
                }
            }
        }
        return null;
    }

    public void updateView() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = this.board.getGrid()[row][col];

                if (piece == null) {
                    this.clearPiece(this.getButtonAt(row, col));
                } else {
                    setButtonImage(this.getButtonAt(row, col), piece.getImagePath());
                }
            }
        }

        if (this.isPieceSelected) {
            ArrayList<int[]> validMoves = this.filterValidMovesBlockingCheck(this.game.getPlayerToMovesKing());
            for (int[] validMove : validMoves) {
                Button button = this.getButtonAt(validMove[0], validMove[1]);
                button.setStyle("-fx-background-color: lightblue;");
            }
        } else {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Button button = this.getButtonAt(row, col);
                    button.setStyle("-fx-background-color: "+ this.board.getGridOriginalColor(row, col) + ";");
                }
            }
        }

        if (this.game.getWinner() != null) {
            // Create the overlay
            StackPane overlay = new StackPane();
            overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
            overlay.setPrefSize(anchor.getPrefWidth(), anchor.getPrefHeight());

            VBox vbox = new VBox(10);
            Text text = new Text(this.game.getWinner().getColor().toUpperCase() + " Won");
            text.setFill(Color.WHITE);
            text.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            Button playAgainButton = new Button("Play Again");
            playAgainButton.setOnAction(e -> {
                this.playLocal();
                anchor.getChildren().remove(overlay);
            });

            Button exitButton = new Button("Exit");
            exitButton.setOnAction(e -> {
                Platform.exit(); // Close the application
            });

            vbox.getChildren().addAll(text, playAgainButton, exitButton);
            vbox.setStyle("-fx-alignment: center;");

            overlay.getChildren().add(vbox);

            // Add the overlay to the AnchorPane
            anchor.getChildren().add(overlay);
        }
    }

    public ArrayList<int[]> filterValidMovesBlockingCheck(King playerToMoveKing) {
        if (!playerToMoveKing.isInCheck(this.board)) {
            this.selectedPiece.getValidMove(this.board);
        }

        int originalRow = this.selectedPiece.getRow();
        int originalCol = this.selectedPiece.getCol();
        ArrayList<int[]> validMovesBlockingCheck = new ArrayList<>();
        ArrayList<int[]> validMoves = this.selectedPiece.getValidMove(this.board);
        for (int[] move : validMoves) {
            int targetRow = move[0];
            int targetCol = move[1];
            Piece targetLocationPiece = this.board.getGrid()[targetRow][targetCol];
            this.board.move(this.selectedPiece, targetRow, targetCol);
            if (!playerToMoveKing.isInCheck(this.board)) {
                validMovesBlockingCheck.add(move);
            }
            this.board.move(this.selectedPiece, originalRow, originalCol);
            this.board.getGrid()[targetRow][targetCol] = targetLocationPiece;
        }

        return validMovesBlockingCheck;
    }

}
