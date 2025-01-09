# Chess App

A feature-rich Chess application that allows players to engage in games locally or over a network using sockets. The app offers a smooth and interactive experience for chess enthusiasts of all levels.

## Features

- **Local Play**: Play against a friend on the same device.
- **Online Play**: Connect with another player via sockets for a remote chess game.
- **Modern UI**: Clean and intuitive interface designed for a seamless user experience.
- **Valid Move Highlighting**: Visual indicators for possible moves for each piece.
- **Game Status**: Real-time updates on the game state (check, checkmate, stalemate).
- **Customizable Themes**: Choose from different board and piece styles to personalize your game.

## Getting Started

### Prerequisites

- **Java 8+**: Ensure you have Java Development Kit (JDK) version 8 or higher installed.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/KYu-2468/chess-app
2. Navigate to the project directory:
   ```bash
   cd chess-app
3. Build the project using Maven:
   ```bash
   mvn clean install
4. To run the app locally:
   ```bash
   mvn javafx:run
   
### Building and distribution

1. For MacOS
   ```bash
   jpackage \
    --input target \
    --name ChessApp \
    --main-jar ChessApp-1.0-shaded.jar \
    --main-class com.example.chessfinalproject.Main \
    --type dmg \
    --mac-package-name "Chess" \
    --icon src/main/resources/com/example/chessfinalproject/icons/chess_icon.icns \
    --java-options "-Xmx1024m"
2. For Linux (Debian)
   ```bash
   jpackage \
    --input target \
    --name ChessApp \
    --main-jar ChessApp-1.0-shaded.jar \
    --main-class com.example.chessfinalproject.Main \
    --type deb \
    --icon src/main/resources/com/example/chessfinalproject/icons/chess_icon.icns \
    --java-options "-Xmx1024m"
3. For Windows (msi)
   ```bash
   jpackage \
    --input target \
    --name ChessApp \
    --main-jar ChessApp-1.0-shaded.jar \
    --main-class com.example.chessfinalproject.Main \
    --type msi \
    --icon src/main/resources/com/example/chessfinalproject/icons/chess_icon.icns \
    --java-options "-Xmx1024m"