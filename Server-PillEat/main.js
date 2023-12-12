// 가천대학교 p-실무프로젝트 IoT 3팀 "약쟁이"
// 개인화된 약 복용 서비스 pillEat-Server

const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);
const port = 60013;
const schedule = require('node-schedule');
const bodyParser = require('body-parser');

var db = require('./lib/db');
const socketEvents = require('./lib/socketEvents');
socketEvents(server);


app.use(bodyParser.json())
app.use(express.urlencoded({ extended: true }));

// 사용자 정의 모듈
var userRouter = require('./router/userRouter');
var takerRouter = require('./router/takerRouter');
var protectorRouter = require('./router/protectorRouter');
var managerRouter = require('./router/managerRouter');

// 스케줄러 설정 (밤 12시마다 pill_history 테이블 update )
const Schedule = schedule.scheduleJob('0 0 * * *', () => {
    var currentDate = new Date();
    var dayOfWeek = currentDate.getDay(); // 일요일(0) ~ 토요일(6)까지의 숫자 반환

    // 기존의 getDay() 반환값을 월요일부터 1로 시작해서 일요일을 7로 조정
    dayOfWeek = (dayOfWeek === 0) ? 7 : dayOfWeek;

    var year = currentDate.getFullYear();
    var month = ('0' + (currentDate.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 1을 더하고 두 자리로 표시
    var day = ('0' + currentDate.getDate()).slice(-2); // 일도 두 자리로 표시

    var date = `${year}-${month}-${day}`;

    // 기존의 getDay() 반환값을 월요일부터 1로 시작해서 일요일을 7로 조정
    dayOfWeek = (dayOfWeek === 0) ? 7 : dayOfWeek;
    
        var date = currentDate.toISOString().split('T')[0];
            db.query(`INSERT INTO pill_history (date, pill_alert_id, is_taken)
            SELECT '${date}' as date, pill_alert_id, false as is_taken
            FROM pill_alert
            WHERE SUBSTRING(alert_day, '${dayOfWeek}', 1) = '1';`,
            (err,result)=>{
                if (err){
                    throw(err)
                }
                console.log("pill_history UPDATE")
                db.query('select * from pill_history',(err,result)=>{
                    console.log(result)
                })
            })
        })

// 서버 작동 확인용
app.get('/', (req, res) => {
    console.log()
    res.send('your server is on');
});


app.use('/user', userRouter);
app.use('/taker', takerRouter);
app.use('/protector', protectorRouter);
app.use('/manager', managerRouter);




server.listen(port,'0.0.0.0', () => {
    // 서버가 실행될 때 pill_history 테이블에 현재 날짜의 알림 데이터 등록
    var currentDate = new Date();
    var dayOfWeek = currentDate.getDay(); // 일요일(0) ~ 토요일(6)까지의 숫자 반환

    // 기존의 getDay() 반환값을 월요일부터 1로 시작해서 일요일을 7로 조정
    dayOfWeek = (dayOfWeek === 0) ? 7 : dayOfWeek;
    
    var year = currentDate.getFullYear();
    var month = ('0' + (currentDate.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 1을 더하고 두 자리로 표시
    var day = ('0' + currentDate.getDate()).slice(-2); // 일도 두 자리로 표시

    var date = `${year}-${month}-${day}`;
        
        db.query(`DELETE FROM pill_history WHERE date = ?`,[date], (err, result)=>{
            if (err){
                throw(err)
            }
            db.query(`INSERT INTO pill_history (date, pill_alert_id, is_taken)
            SELECT '${date}' as date, pill_alert_id, false as is_taken
            FROM pill_alert
            WHERE SUBSTRING(alert_day, '${dayOfWeek}', 1) = '1';`,
            (err,result)=>{
                if (err){
                    throw(err)
                }
            })
        })
    console.log(`Server is running at http://ceprj.gachon.ac.kr:${port}`);
});


