const { socket } = require('server/router');
var db = require('./db');

module.exports = {
    //복용할 약 등록
    enroll: (req, res) => {
        var takerId = req.params.takerId;
        var enrollData = req.body;

        console.log(`takerId: ${takerId}, enroll: ${enrollData}`);
    
        db.query('INSERT INTO Pill_Alert (taker_id, pill_name, pill_kind, alert_time, alert_day) VALUES(?,?,?,?,?)', 
            [takerId, enrollData.name, enrollData.category, enrollData.time, enrollData.day], (err, result) => {
            if (err) {
                console.log(err)
                const responseData = {
                    isSuccess: False,
                    code: 600,
                    message: "요청에 실패하였습니다.",
                };
                return res.json(responseData);
            }else{
                const responseData = {
                    isSuccess: true,
                    code: 200,
                    message: "요청에 성공하였습니다.",
                };
                res.json(responseData);
            } 
        });
    },

    //등록한 약 목록 불러오기
    enrollList: (req, res) =>{
        var takerId = req.params.takerId;

        console.log(`enrollList, takerId: ${takerId}`)

        db.query('SELECT * FROM pill_alert WHERE pill_user_id = ?', [takerId], (err, result) => {
            if (err) {
                console.log(err)
                const responseData = {
                    isSuccess: false,
                    code: 600,
                    message: "요청에 실패하였습니다.",
                    result: null
                };
                return res.json(responseData);
            }else{
                const drugs= result.map(result => ({
                    drugId: result.pill_alert_id,
                    name: result.pill_name,
                    category: result.pill_category,
                    time: result.pill_time,
                    day: result.pill_day
                  }));
                const responseData = {
                    isSuccess: true,
                    code: 200,
                    message: "요청에 성공하였습니다.",
                    result: {
                        drugs: drugs
                      }
                };
                res.json(responseData);
            } 
        });
    },

    //복용 완료 요청
    finish: (req,res,io) => {
        io.on('connection', (socket) => {
            console.log(`클라이언트가 연결되었습니다.`);
            
            // 클라이언트로부터 메시지 수신 예제
            socket.on('clientMessage', (message) => {
              console.log(`클라이언트로부터 수신한 메시지: ${message}`);
            });
          
            // 클라이언트에게 메시지 전송 예제
            socket.emit('serverMessage', '서버에서 보낸 메시지');
          });
    },

    //등록한 약 삭제 요청
    enrollDelete: (req,res) => {
        var drugId = req.params.drugId;

        console.log(`enrollDelete, drugId: ${drugId}`)
    
        // user 테이블에서 삭제
        db.query(`DELETE FROM pill_alert WHERE pill_alert_id = ?`, [drugId], (err, result) => {
            if (err) {
                const responseData = {
                    isSuccess: false,
                    code: 600,
                    message: `요청에 실패하였습니다.`,
                };
                return res.json(responseData);
            }else{
                const responseData = {
                    isSuccess: true,
                    code: 200,
                    message: `요청에 성공하였습니다.`,
                };
                res.json(responseData);
            }
        });
    },

    // 복용 완료 여부 리스트 불러오기
    record:(req,res)=>{
        var takerId = req.params.takerId;
        var date = req.parmas.date;

        console.log("drug-list")

        db.query(`SELECT *
                FROM pill_history
                LEFT JOIN pill_alert ON pill_history.pill_alert_id = pill_alert.pill_alert_id
                WHERE pill_alert.taker_id = ? AND pill_history.date = ?;`, 
                [takerId, date], (err, result) => {
            if (err) {
                console.log(err)
                const responseData = {
                    isSuccess: false,
                    code: 600,
                    message: "요청에 실패하였습니다.",
                    result: null
                };
                return res.json(responseData);
            }else{
                const list= result.map(result => ({
                    name: result.pill_name,
                    time: result.pill_time,
                    finishYN : result.is_taken
                  }));
                const responseData = {
                    isSuccess: true,
                    code: 200,
                    message: "요청에 성공하였습니다.",
                    result: {
                        list: list
                      }
                };
                res.json(responseData);
            } 
        });
    },

    dd:(req,res,io)=>{
        
        
    }
 
}