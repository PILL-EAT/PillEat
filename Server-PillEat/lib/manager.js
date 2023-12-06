var db = require('./db');

module.exports = {
    //사용자 정보 목록 요청
    userList: (req,res)=>{
        //taker와 protector 결합해서 쿼리
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

    },
}