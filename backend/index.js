const express = require("express");
const app = express();
const server = require("http").createServer(app);
const io = require("socket.io")(server);

let player1;
let player2;

// Handle WebSocket connections
io.on("connection", function (socket) {
	console.log("Client connected");

	// Handle pending players waiting to match
	socket.on("match:pending", function (message) {
		if (!player1) {
			player1 = message;
		} else if (!player2) {
			player2 = message;
			// Broadcast the message to all connected clients
			io.emit("match:setup", player1, player2);
		} else {
			console.log("Player 1 and player 2 is taken.");
		}
	});

	// Handle moving pieces
	socket.on("piece:move", function (...message) {
		// Broadcast the message to all connected clients
		io.emit("piece:move", ...message);
	});

	// Handle disconnections
	socket.on("disconnect", function () {
		console.log("Client disconnected");
		player1 = null;
		player2 = null;
	});
});

app.use("/", (req, res, next) => {
	res.send("Congrats!!");
});

// Start the server
const port = 8080;
server.listen(port, function () {
	console.log("Server listening on port", port);
});
