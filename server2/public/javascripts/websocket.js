var myWebSocket;
function connectToWS() {
    var endpoint = document.getElementById("endpoint").value;
    if (myWebSocket !== undefined) {
        myWebSocket.close()
    }
    myWebSocket = new WebSocket(endpoint);
    myWebSocket.onmessage = function(event) {
        var leng;
        if (event.data.size === undefined) {
            leng = event.data.length
        } else {
            leng = event.data.size
        }
        console.log("onmessage. size: " + leng + ", content: " + event.data);
    }
    myWebSocket.onopen = function(evt) {
        console.log("onopen.");
    };
    myWebSocket.onclose = function(evt) {
        console.log("onclose.");
    };
    myWebSocket.onerror = function(evt) {
        console.log("Error!");
    };
}
function sendMsg() {
    var message = document.getElementById("myMessage").value;
    myWebSocket.send(message);
}
function closeConn() {
    myWebSocket.close();
}