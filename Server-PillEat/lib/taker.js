const { socket } = require('server/router');
var db = require('./db');

// 날짜 생성 함수
function getCurrentDate() {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = ('0' + (currentDate.getMonth() + 1)).slice(-2);
    const day = ('0' + currentDate.getDate()).slice(-2);

    return `${year}-${month}-${day}`;
}


module.exports = {
    enroll: (req, res) => {
        try {
            const takerId = req.params.userId;
            const enrollData = req.body;
    
            console.log(`takerId: ${takerId}, enroll: ${JSON.stringify(enrollData)}`);
            console.log(enrollData.date);
    
            const times = enrollData.time[0];
            const queryPromises = [];
    
            var date = getCurrentDate();
    
            for (let i = 0; i < enrollData.date; i++) {
                console.log(times['time' + (i + 1)]);
    
                // 각 쿼리를 Promise로 감싸고 배열에 추가
                const queryPromise = new Promise((resolve, reject) => {
                    db.query(
                        'INSERT INTO pill_alert (taker_id, pill_name, pill_kind, alert_time, alert_day, iotYN) VALUES(?,?,?,?,?,?)',
                        [takerId, enrollData.name, enrollData.category, times['time' + (i + 1)], enrollData.day, enrollData.iot],
                        (err, result) => {
                            if (err) {
                                const errorResponse = {
                                    isSuccess: false,
                                    code: 600,
                                    message: "요청에 실패하였습니다.",
                                };
                                reject(errorResponse);
                            } else {
                                const pillAlertId = result.insertId;
                                db.query('INSERT INTO pill_history (date, pill_alert_id, is_taken) VALUES(?,?,?)',
                                    [date, pillAlertId, 0], (err, result2) => {
                                        if (err) {
                                            const errorResponse = {
                                                isSuccess: false,
                                                code: 600,
                                                message: "요청에 실패하였습니다.",
                                            };
                                            reject(errorResponse);
                                        } else {
                                            resolve(result);
                                        }
                                    });
                            }
                        }
                    );
                });
    
                queryPromises.push(queryPromise);
            }
    
            // 모든 Promise가 완료될 때까지 대기
            Promise.all(queryPromises)
                .then(results => {
                    const responseData = {
                        isSuccess: true,
                        code: 200,
                        message: "요청에 성공하였습니다.",
                        results: results,
                    };
                    res.json(responseData);
                })
                .catch(error => {
                    console.error(error);
                    const errorResponse = {
                        isSuccess: false,
                        code: 600,
                        message: "요청에 실패하였습니다.",
                    };
                    res.json(errorResponse);
                });
    
        } catch (error) {
            console.error(error);
            const errorResponse = {
                isSuccess: false,
                code: 600,
                message: "요청에 실패하였습니다.",
            };
            res.json(errorResponse);
        }
    },
    
    

    //등록한 약 목록 불러오기
    enrollList: (req, res) => {
        var takerId = req.params.userId;
    
        console.log(`enrollList, takerId: ${takerId}`)
    
        db.query('SELECT * FROM pill_alert WHERE taker_id = ? ORDER BY alert_time ASC;', [takerId], (err, result) => {
            if (err) {
                console.log(err)
                const responseData = {
                    isSuccess: false,
                    code: 600,
                    message: "요청에 실패하였습니다.",
                    result: null
                };
                return res.json(responseData);
            } else {
                if (result.length === 0) {
                    // 데이터가 없는 경우
                    const responseData = {
                        isSuccess: true,
                        code: 404,
                        message: "데이터가 없습니다.",
                        result: {
                            drugs: []
                        }
                    };
                    return res.json(responseData);
                }
                const drugs = result.map(resultItem => ({
                    drugId: resultItem.pill_alert_id,
                    name: resultItem.pill_name,
                    category: resultItem.pill_kind,
                    time: resultItem.alert_time,
                    day: resultItem.alert_day,
                    iot: resultItem.iotYN
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

    //등록한 약 삭제 요청
    enrollDelete: (req,res) => {
        var drugId = req.params.drugId;
        var date = getCurrentDate();

        console.log(`enrollDelete, drugId: ${drugId}`)
        
        query1 = 'DELETE FROM pill_alert WHERE pill_alert_id = ?;';
        query2 = 'DELETE FROM pill_history WHERE pill_alert_id = ? AND date = ?;';
        // user 테이블에서 삭제
        db.query(query1+query2, [drugId, drugId, date], (err, result) => {
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
        var date = req.params.date;

        console.log(`drug-list takerId = ${takerId}, date = ${date}`)

        db.query(`SELECT *
                FROM pill_history
                LEFT JOIN pill_alert ON pill_history.pill_alert_id = pill_alert.pill_alert_id
                WHERE pill_alert.taker_id = ? AND pill_history.date = ? ORDER BY alert_time ASC;`, 
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
                    userId: result.taker_id,
                    drugId: result.pill_alert_id,
                    name: result.pill_name,
                    time: result.alert_time,
                    finishYN : result.is_taken,
                    iot: result.iotYN
                  }));
                  console.log(list)
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

    // 보호자 등록
    inputProtector: (req, res) => {
        console.log("hi")
        var takerId = req.params.takerId;
        console.log(takerId)
        var inputData = req.body;
        console.log(`inputProtector ${inputData}`);

        // 전화번호 중복 확인 - user 테이블
        db.query('SELECT * FROM user WHERE user_number = ?', [inputData.phone], (err, result) => {
            if (err) {
                throw err;
            }
            if (result.length === 0) {
                // 전화번호가 일치하는 유저가 없다면
                const responseData = {
                    isSuccess: false,
                    code: 2017,
                    message: "등록된 전화번호가 없습니다.",
                };
                return res.json(responseData);
            } else {
                if (result[0].user_type === "protector") {
                    // 회원 가입
                    db.query('UPDATE user SET protector_id = ? WHERE user_id = ?', [result[0].user_id, takerId], (err, updateResult) => {
                        if (err) {
                            throw err;
                        }
                        const responseData = {
                            isSuccess: true,
                            code: 1000,
                            message: "요청에 성공하였습니다.",
                        };
                        return res.json(responseData);
                    });
                } else {
                    // 유저의 type이 "protector"가 아니라면
                    const responseData = {
                        isSuccess: false,
                        code: 2017,
                        message: "해당 유저는 보호자가 아닙니다.",
                    };
                    return res.json(responseData);
                }
            }
        });
    }


    
}
