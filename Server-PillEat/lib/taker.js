var db = require('./db');

module.exports = {
    //복용 시간 알림 기능
    alert: (req,res)=>{
        //현재 시간과 알림 시간을 비교
        const responseData = {
            isSuccess: true,
            code: 200,
            messege: "요청에 성공하였습니다.",
            result: {
                contents: "약 드실 시간입니다!"
            }
        }
        res.json(responseData);
    },

    //복용할 약 등록
    enroll: (req, res) => {
        const takerId = req.params.takerId;
        var enrollData = req.body;
    
        db.query('INSERT INTO Pill_Alert (taker_id, pill_name, pill_kind, alert_time, alert_day, alert_memo) VALUES(?,?,?,?,?,?)', 
            [takerId, enrollData.name, enrollData.category, enrollData.time, enrollData.day, enrollData.memo], (err, result) => {
            if (err) {
                throw err;
            }
    
            const responseData = {
                isSuccess: true,
                code: 200,
                message: "요청에 성공하였습니다.",
            };
    
            res.json(responseData);
        });
    },

    //등록한 약 목록 불러오기
    enrollList: (req, res) =>{
        const takerId = req.params.takerId;

        db.query('SELECT * FROM pill_alert WHERE pill_user_id = ?', [takerId], (err, result) => {
            if (err) {
                throw err;
            }
    
            const responseData = {
                isSuccess: true,
                code: 200,
                message: "요청에 성공하였습니다.",
                result: result
            };
    
            res.json(responseData);
        });

    }

    
}