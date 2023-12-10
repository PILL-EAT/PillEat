var db = require('./db');

module.exports = {  
    // 로그인
    login: (req, res) => {
        var loginData = req.body;
        console.log('user login:', loginData);
    
        // user 테이블에서 로그인 확인
        db.query('SELECT * FROM user WHERE user_email = ? AND user_password = ?',
            [loginData.email, loginData.password], (err, result) => {
                if (err) {
                    // 로그인 실패
                    console.error(err);
                    const responseData = {
                        isSuccess: false,
                        code: 0,
                        message: "요청에 실패하였습니다.",
                        result: {
                            userId: null,
                            type: null
                        }
                    };
                    res.json(responseData);
                }else{
                    // 로그인 성공
                    const responseData = {
                        isSuccess: true,
                        code: 1000,
                        message: "요청에 성공하였습니다.",
                        result: {
                            userId: result[0].user_id,
                            type: result[0].user_type  // user_type에 따라 "taker" or "protector" or "manager"
                        }
                    };

                    var userId = result[0].user_id;

                    res.json(responseData);
                }
            });
    },

    logout: (req,res) => {
        var userId = req.params.userId;
        clients.delete(userId);

        const responseData = {
            isSuccess: true,
            code: 1000,
            message: "요청에 성공하였습니다.",
        }

        res.json(responseData);
    },

    // 회원가입 기능
    join: (req, res) => {
        var joinData = req.body;
    
        // 이메일 중복 확인 - user 테이블
        db.query('SELECT * FROM user WHERE user_email = ?', [joinData.email], (errUser, resultUser) => {
            if (errUser) {
                throw errUser;
            }
    
            if (resultUser.length > 0) {
                // 이미 존재하는 이메일이라면 클라이언트에게 에러 메시지 전송
                const responseData = {
                    isSuccess: false,
                    code: 2017,
                    message: "중복된 이메일입니다.",
                };
                res.json(responseData);
            } else {
                // 회원 가입
                db.query('INSERT INTO user (user_email, user_password, user_name, user_birth, user_number, user_date, user_type) VALUES (?, ?, ?, ?, ?, NOW(), ?)',
                    [joinData.email, joinData.password, joinData.name, joinData.birth, joinData.phone, (joinData.mode === 'taker' ? 'taker' : 'protector')], (err, result) => {
                        if (err) {
                            throw err;
                        }
                        const responseData = {
                            isSuccess: true,
                            code: 1000,
                            message: "요청에 성공하였습니다.",
                        }
                        res.json(responseData);
                    });
            }
        });
    },
    

    // 회원 탈퇴
    delete: (req, res) => {
        const userId = req.params.userId;
    
        // user 테이블에서 삭제
        db.query(`DELETE FROM user WHERE user_id = ?`, [userId], (err, result) => {
            if (err) {
                const responseData = {
                    isSuccess: false,
                    code: 600,
                    message: `요청에 실패하였습니다.`,
                };
                res.json(responseData);
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

    // 내 정보 보기
    userInfo:(req,res)=>{
        const userId = req.params.userId;
        db.query('select * from user where = ?',[userId], (err,result)=>{
            if(err){
                const responseData = {
                    isSuccess: false,
                    code: 600,
                    message: "요청에 실패하였습니다.",
                    result: null
                };
                res.json(responseData);
            }else{ 
                const responseData = {
                    isSuccess: false,
                    code: 200,
                    message: "요청에 성공하였습니다.",
                    result: {
                        email: result[0].user_email,
                        password: result[0].user_password,
                        name: result[0].user_name,
                        birth: result[0].user_birth,
                        phone: result[0].user_phone,
                        mode: result[0].user_type
                    }
                };
                res.json(responseData);
            }
        })
    },

    // 내 정보 수정
    userUpdate: (req, res) => {
        const userId = req.params.userId;
        var updateData = req.body;
    
        db.query(
            'UPDATE user SET user_name = ?, user_birth = ?, user_phone = ? WHERE user_Id = ?',
            [updateData.name, updateData.birth, updateData.phone, userId],(err, result) => {
                if (err) {
                    const responseData = {
                        isSuccess: false,
                        code: 600,
                        message: "요청에 실패하였습니다.",
                    };
                    res.json(responseData);
                } else {
                    const responseData = {
                        isSuccess: true,
                        code: 200,
                        message: "요청에 성공하였습니다.",
                    };
                    res.json(responseData);
                }
            }
        );
    },
    
    

}
