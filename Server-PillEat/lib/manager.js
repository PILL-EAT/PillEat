var db = require('./db');

module.exports = {
    //사용자 정보 목록 요청
    userList: (req,res)=>{
        //taker와 protector table 데이터 결합해서 반환
        db.query(`SELECT taker_id AS user_id,
                taker_email AS user_email,
                taker_password AS user_password,
                taker_name AS user_name,
                taker_birth AS user_birth,
                taker_number AS user_number,
                taker_date AS user_date,
                null AS taker_id,
                'taker' AS user_type
                FROM taker
                UNION
                SELECT 
                protector_id AS user_id,
                protector_email AS user_email,
                protector_password AS user_password,
                protector_name AS user_name,
                protector_birth AS user_birth,
                protector_number AS user_number,
                protector_date AS user_date,
                taker_id AS taker_id, 
                'protector' AS user_type
                FROM protector;`, (err, result) => {
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