module com.example.chessfinalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires socket.io.client;
    requires engine.io.client;
    requires org.json;

    opens com.example.chessfinalproject to javafx.fxml;
    exports com.example.chessfinalproject;
}