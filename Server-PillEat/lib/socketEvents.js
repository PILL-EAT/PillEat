const socketIO = require('socket.io');

function socketEvents(server) {
  const io = socketIO(server);

  io.on('connection', (socket) => {
    console.log('WebSocket connection established');

    socket.on('message', (message) => {
      console.log(`Received message: ${message}`);

      // 처리 로직 추가
      socket.emit('serverMessage', 'Server received your message');
    });

    socket.on('disconnect', () => {
      console.log('WebSocket connection closed');
    });
  });
}

module.exports = socketEvents;
