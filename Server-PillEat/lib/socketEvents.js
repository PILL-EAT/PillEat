// webSocketSetup.js
const expressWs = require('express-ws');
const db = require('./db');

const clients = new Map();

function setupWebSocket(app) {
    expressWs(app);

    app.ws('/ws', (ws, req) => {
        console.log('클라이언트가 연결되었습니다.');
        ws.send('socket 연결 성공');

        ws.on('message', (rawMessage) => {
          try {
              // 수신된 메시지 파싱
              const message = JSON.parse(rawMessage);
              console.log(message);
      
              // 메시지 타입에 따라 처리
              switch (message.type) {
                  // 사용자가 로그인을 하면 clientId를 부여하고 클라이언트에게 전송
                  case 'login':
                      const clientId = message.clientId;
                      clients.set(clientId, ws);
                      console.log(`클라이언트 로그인, clientId: ${clientId}`);
                      const clientSocket = clients.get(clientId);
                      clientSocket.send(clientId);
                      break;
      
                  // 복용자가 약을 먹었을 경우 보호자에게 메세지 전송
                  case 'finish':
                      console.log(`복용자 약 복용 완료, clientId: ${message.clientId}, alert_id: ${message.drugId}`);
                      db.query('UPDATE pill_history SET is_taken = 1 WHERE pill_alert_id = ?', [message.drugId], (err, result) => {
                          if (err) {
                              console.error('query 에러:', err);
                          } else {
                              db.query('SELECT user_id FROM user WHERE taker_id = ?', [message.clientId], (err, result) => {
                                  if (err) {
                                      console.error('query 에러:', err);
                                  } else {
                                      if (result.length > 0) {
                                          const userId = result[0].user_id;
                                          const clientSocket = clients.get(userId);
                                          if (clientSocket) {
                                              clientSocket.send('약 복용이 완료되었습니다.');
                                          } else {
                                              console.log(`userId = ${userId} 를 찾을 수 없습니다.`);
                                          }
                                      } else {
                                          console.log(`등록된 보호자가 없습니다. ${message.clientId}`);
                                      }
                                  }
                              });
                          }
                      });
                      break;
      
                  // 복용자가 약을 먹지 않았을 경우 보호자에게 메세지 전송
                  case 'finish-no':
                      console.log(`복용자가 아직 약을 복용하지 않았습니다.: ${message.clientId}`);
                      break;
      
                  default:
                      console.log('Unknown message type:', message.type);
                      break;
              }
          } catch (error) {
              console.error('Error parsing message:', error);
          }
      });
      

        // socket 연결 해제
        ws.on('close', () => {
            const clientId = findClientIdByWebSocket(ws);
            if (clientId) {
                clients.delete(clientId);
                console.log(`Client ${clientId} disconnected`);
            }
        });
    });
}
// clientId를 찾는 함수
function findClientIdByWebSocket(webSocket) {
    for (const [clientId, clientSocket] of clients.entries()) {
        if (clientSocket === webSocket) {
            return clientId;
        }
    }
    return null;
}

module.exports = setupWebSocket;
