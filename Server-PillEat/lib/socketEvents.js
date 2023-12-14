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
                    // 사용자의 userId를 clientId로 부여
                      const clientId = message.clientId;
                      clients.set(clientId, ws);
                      console.log(`클라이언트 로그인, clientId: ${clientId}`);
                      const clientSocket = clients.get(clientId);

                      // 로그인한 클라이언트에게 clientId 전송
                      clientSocket.send(clientId);
                      break;

                  case 'rasberryLogin':
                    // 사용자의 userId를 clientId로 부여
                    const rasberryId = message.rasberryId;
                    clients.set(rasberryId, ws);
                    console.log(`라즈베리파이 로그인, clientId: ${rasberryId}`);
                    const rasberrySocket = clients.get(rasberryId);

                    // 로그인한 클라이언트에게 clientId 전송
                    rasberrySocket.send("rasberrypi Id : rasberryId");
                    break;    
      
                  // 복용자가 약을 먹었을 경우 보호자에게 메세지 전송
                  case 'finish':
                      console.log(`복용자 약 복용 완료, clientId: ${message.clientId}, alert_id: ${message.drugId}`);
                      // pill_history 테이블에 약 복용 여부 속성 업데이트
                      db.query('UPDATE pill_history SET is_taken = 1 WHERE pill_alert_id = ?', [message.drugId], (err, result) => {
                          if (err) {
                              console.error('query 에러:', err);
                          } else {
                            // 복용자의 보호자를 select
                              db.query('SELECT protector_id FROM user WHERE user_id = ?', [message.clientId], (err, result2) => {
                                  if (err) {
                                      console.error('query 에러:', err);
                                  } else {
                                      // 보호자가 있다면
                                      if (result2.length > 0) {
                                          const userId = result2[0].protector_id;
                                          const clientSocket = clients.get(userId);
                                          if (clientSocket) {
                                            // 보호자에게 약 복용 완료 메시지 전송
                                            db.query('SELECT * from pill_alert where pill_alert_id = ?',[message.drugId],
                                            (err,result3)=>{
                                                const responseMessage = {
                                                    type: 'finish',
                                                    message: `복용자가 ${result3[0].alert_time} ${result3[0].pill_name}을 복용했습니다!`
                                                };
                                                clientSocket.send(JSON.stringify(responseMessage));
                                            })
                                          } else {
                                              console.log(`userId = ${userId} 를 찾을 수 없습니다.`);
                                          }
                                      } else {
                                        // 보호자가 없다면 메시지 반환
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
