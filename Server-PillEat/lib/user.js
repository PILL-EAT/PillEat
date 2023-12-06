var db = require('./db');

module.exports = {

    //테스트용
    test2 : (req,res)=>{
        var loginData = req.body;
        console.log('test Received data:', loginData);
    },
    //테스트용
    test : (req,res)=>{
        console.log("hi")
        db.query('select * from taker',(err,result)=>{
            if(err){
                throw err;
            }
            const responseData = {
                buttonText: result[0].taker_email,
                name: result[0].taker_password
            }
            res.json(responseData);
        })
    },
    //로그인 기능
    login : (req,res)=>{
        var loginData = req.body;
        console.log('Received data:', loginData);
        db.query('select * from taker where taker_email and taker_password = ?',
        [loginData.email, loginData.password], (err,result)=>{
            if(err){
                throw err;
            }
            if(result.length > 0){
                const responseData = {
                    isSuccess: true,
                    code: 1000,
                    messege: "요청에 성공하였습니다.",
                    result: {
                        userId: result[0].userId,
                        jwt : 0 //추후 수정
                    }
                }
            }else{
                const responseData = {
                    isSuccess: false,
                    code: 0,
                    messege: "요청에 실패하였습니다.",
                    result: {
                        userId: 0,
                        jwt : 0
                    }
            } 
        }
        res.json(responseData);
        })
        
    },

    //로그아웃 기능
    logout : (req,res)=>{
        //추가 예정
    },

    //회원가입 기능
    join : (req,res)=>{
        var joinData = req.body;
        //복약자의 경우
        db.query('INSERT INTO taker (taker_email, taker_password, taker_name, taker_birth, taker_number, taker_date) VALUES(?, ?, ?, ?, ?, ?)', 
        [joinData.emil, joinData.password, joinData.name, joinData.birth, joinData.phone], (err,result)=>{
            if(err){
                throw err;
            }
            const responseData = {
                isSuccess: true,
                code: 1000,
                messege: "요청에 성공하였습니다.",
            }
        })
    },
}