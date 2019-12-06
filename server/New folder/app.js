// const http = require('http');
//
// const hostname = '127.0.0.1';
// const port = 3000;
//
// const server = http.createServer((req, res) => {
//   res.statusCode = 200;
//   res.setHeader('Content-Type', 'text/plain');
//   res.end('Hello World\n');
// });
//
// server.listen(port, hostname, () => {
//   console.log(`Server running at http://${hostname}:${port}/`);
// });

var express = require('express');
var app = express();
var server = require('http').Server(app);

var io = require('socket.io')(server, { pingTimeout: 3000, pingInterval: 1000 });
var port = process.env.PORT || 8080;

var sub = 34;
var sub2 = 1;

var id = 'id';
var count = 1;

id = id+count;
//serve server in port 8080
server.listen(port, function(){
  console.log('Server is now running');
});

io.on('connection', function(socket){

  socket.on('onReceived',onReceived);
  // socket.on('my_mes',onMyMess);
  socket.on('onTypingAnimation', onTypingAnimation);

  function onReceived(data){
    console.log('====================New Message in Inbox=======================');
    console.log(data);
  }

    function onTypingAnimation(data){
      console.log('===================Received=====================');
      console.log(data);
      socket.broadcast.emit('typingAnimation', data);
    }

  });
