var db = require('./db');

module.exports = {
    // 테스트용
    test2: (req, res) => {
        var loginData = req.body;
        console.log('test Received data:', loginData);
    },
    
    // 테스트용
    test: (req, res) => {
        console.log("hi")
        db.query('SELECT * FROM taker', (err, result) => {
            if (err) {
                throw err;
            }
            const responseData = {
                buttonText: result[0].taker_email,
                name: result[0].taker_password
            }
            res.json(responseData);
        });
    },
    
    // 로그인 기능
    login: (req, res) => {
        var loginData = req.body;
        console.log('Received data:', loginData);

        // taker일 경우
        db.query('SELECT * FROM taker WHERE taker_email = ? AND taker_password = ?',
            [loginData.email, loginData.password], (err, result) => {
                if (err) {
                    throw err;
                }
                if (result.length > 0) {
                    const responseData = {
                        isSuccess: true,
                        code: 1000,
                        message: "요청에 성공하였습니다.",
                        userId: result[0].taker_id,
                        type: "taker"
                    }
                    res.json(responseData);
                }
                // protector일 경우
                else if (result.length === 0) {
                    db.query('SELECT * FROM protector WHERE protector_email = ? AND protector_password = ?',
                        [loginData.email, loginData.password], (err, result) => {
                            if (err) {
                                throw err;
                            }
                            if (result.length > 0) {
                                const responseData = {
                                    isSuccess: true,
                                    code: 1000,
                                    message: "요청에 성공하였습니다.",
                                    userId: result[0].protector_id,
                                    type: "protector"
                                }
                                res.json(responseData);
                            }
                            // 둘 다 아닐 경우    
                            else {
                                const responseData = {
                                    isSuccess: false,
                                    code: 0,
                                    message: "요청에 실패하였습니다.",
                                    result: {
                                        userId: 0,
                                        jwt: 0
                                    }
                                }
                                res.json(responseData);
                            }
                        });
                }
            });
    },

    // 로그아웃 기능
    logout: (req, res) => {
        // 추가 예정
    },

    // 회원가입 기능
    join: (req, res) => {
        var joinData = req.body;
    
        // 이메일 중복 확인 - taker 테이블
        db.query('SELECT * FROM taker WHERE taker_email = ?', [joinData.email], (errTaker, resultTaker) => {
            if (errTaker) {
                throw errTaker;
            }
    
            // 이메일 중복 확인 - protector 테이블
            db.query('SELECT * FROM protector WHERE protector_email = ?', [joinData.email], (errProtector, resultProtector) => {
                if (errProtector) {
                    throw errProtector;
                }
    
                if (resultTaker.length > 0 || resultProtector.length > 0) {
                    // 이미 존재하는 이메일이라면 클라이언트에게 에러 메시지 전송
                    const responseData = {
                        isSuccess: false,
                        code: 2017,
                        message: "중복된 이메일입니다.",
                    };
                    res.json(responseData);
                } else {
                    // 복약자의 경우
                    if (joinData.mode === 'taker') {
                        db.query('INSERT INTO taker (taker_email, taker_password, taker_name, taker_birth, taker_number, taker_date) VALUES (?, ?, ?, ?, ?, NOW())',
                            [joinData.email, joinData.password, joinData.name, joinData.birth, joinData.phone], (err, result) => {
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
                    // protector의 경우
                    else if (joinData.mode === 'protector') {
                        db.query('INSERT INTO protector (protector_email, protector_password, protector_name, protector_birth, protector_number, protector_date) VALUES (?, ?, ?, ?, ?, NOW())',
                            [joinData.email, joinData.password, joinData.name, joinData.birth, joinData.phone], (err, result) => {
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
                }
            });
        });
    },

    // 회원 탈퇴
    delete:(req,res)=>{
        const userId = req.params.userId;
        const userType = req.params.userType; // 클라이언트로부터 전달되는 사용자의 userType

        // userType에 따라 삭제할 테이블을 결정
        const deleteTable = (userType === 'T') ? 'taker' : (userType === 'P') ? 'protector' : null;

        if (!deleteTable) {
            const responseData = {
                isSuccess: false,
                code: 600,
                message: "올바르지 않은 userType입니다.",
            };
            res.json(responseData);
            return 
        }

        // 해당 테이블에서 삭제
        db.query(`DELETE FROM ${deleteTable} WHERE ${deleteTable}_id = ?`, [userId], (err, result) => {
            if (err) {
                throw err;
            }

            // 삭제가 성공했을 경우
            if (result.affectedRows > 0) {
                const responseData = {
                    isSuccess: true,
                    code: 200,
                    message: `사용자(${userId})의 회원 탈퇴에 성공하였습니다.`,
                };
                res.json(responseData);
            } else {
                const responseData = {
                    isSuccess: false,
                    code: 600,
                    message: `해당 사용자(${userId})를 찾을 수 없습니다.`,
                };
                res.json(responseData);
            }
        });
    },
}
