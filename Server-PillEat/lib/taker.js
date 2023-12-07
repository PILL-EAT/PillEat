var db = require('./db');

module.exports = {
    //복용할 약 등록
    enroll: (req, res) => {
        const takerId = req.params.takerId;
        var enrollData = req.body;
    
        db.query('INSERT INTO Pill_Alert (taker_id, pill_name, pill_kind, pill_volumn, alert_time, alert_day, alert_memo) VALUES(?,?,?,?,?,?,?)', 
            [takerId, enrollData.name, enrollData.category,enrollData.volumn, enrollData.time, enrollData.day, enrollData.memo], (err, result) => {
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
        const takerId = req.params.takerId;

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
                    name: result.pill_name,
                    category: result.pill_category,
                    volumn: result.pill_volumn,
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
    finish: (req,res) => {
        // 논의 예정
    },

    //등록한 약 삭제 요청
    enrollDelete: (req,res) => {
        const drugId = req.params.userId;
    
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
 
}