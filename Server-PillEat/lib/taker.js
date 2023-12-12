const { socket } = require('server/router');
var db = require('./db');

// 삽입 오류 처리를 위한 함수


module.exports = {
    //복용할 약 등록
    enroll: async (req, res) => {
        try {
            const takerId = req.params.userId;
            const enrollData = req.body;
    
            console.log(`takerId: ${takerId}, enroll: ${JSON.stringify(enrollData)}`);
            console.log(enrollData.date);
    
            const times = enrollData.time[0];
            const queryPromises = [];
    
            for (let i = 0; i < enrollData.date; i++) {
                console.log(times['time' + (i + 1)]);
    
                // 각 쿼리를 Promise로 감싸고 배열에 추가
                const queryPromise = new Promise((resolve, reject) => {
                    db.query(
                        'INSERT INTO pill_alert (taker_id, pill_name, pill_kind, alert_time, alert_day) VALUES(?,?,?,?,?)',
                        [takerId, enrollData.name, enrollData.category, times['time' + (i + 1)], enrollData.day],
                        (err, result) => {
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
                        }
                    );
                });
    
                queryPromises.push(queryPromise);
            }
    
            // 모든 Promise가 완료될 때까지 대기
            const results = await Promise.all(queryPromises);
    
            const responseData = {
                isSuccess: true,
                code: 200,
                message: "요청에 성공하였습니다.",
            };
            res.json(responseData);
           
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
    
        db.query('SELECT * FROM pill_alert WHERE taker_id = ?', [takerId], (err, result) => {
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
                    day: resultItem.alert_day
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
        var date = req.params.date;

        console.log(`drug-list takerId = ${takerId}, date = ${date}`)

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
}