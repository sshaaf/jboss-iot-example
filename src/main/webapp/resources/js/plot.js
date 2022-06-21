
var socket = new WebSocket('ws://localhost:8080/mqtt-example/devicesocket');

socket.onmessage
socket.onopen = function(event) {
    console.log('Connection established');
    // Display user friendly messages for the successful establishment of connection
}


socket.onmessage = function(e){
    var server_message = e.data;
    console.log(server_message);
}
