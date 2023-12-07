var db = require('./db');

module.exports = {
    // 사용자 정보 목록 요청
    userList: (req, res) => {
      console.log("manager userList");
  
      db.query(`
        SELECT
            taker_name AS name,
            taker_date AS time,
            taker_birth AS birth,
            'taker' AS mode
        FROM taker
        UNION
        SELECT
            protector_name AS name,
            protector_date AS time,
            protector_birth AS birth,
            'protector' AS mode
        FROM protector;
      `, (err, result) => {
        if (err) {
          console.error(err);
          const responseData = {
            isSuccess: false,
            code: 600,
            message: "요청에 실패하였습니다.",
            result: null
          };
          console.log(result);
          res.json(responseData);
        } else {
          const users = result.map(user => ({
            name: user.name,
            time: user.time,
            birth: user.birth,
            mode: user.mode,
          }));
  
          const responseData = {
            isSuccess: true,
            code: 200,
            message: "요청에 성공하였습니다.",
            result: {
              users: users
            }
          };
          console.log(responseData)
          console.log(JSON.stringify(result, null, 2));
          res.json(responseData);
        }
      });
    }
  };
  