var websocket = new WebSocket("server address");

websocket.onmessage = function(str) {
  console.log("Someone sent: ", str);
};

// Tell the server this is client 1 (swap for client 2 of course)
websocket.send(JSON.stringify({
  id: "client1"
}));

// Tell the server we want to send something to the other client
websocket.send(JSON.stringify({
  to: "client2",
  data: "foo"
}));
