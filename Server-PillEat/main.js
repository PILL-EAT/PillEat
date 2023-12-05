// 가천대학교 p-실무프로젝트 IoT 3팀 "약쟁이"
// 개인화된 약 복용 서비스 pillEat-Server

const express = require('express');
const app = express();
const port = 3000;
const bodyParser = require('body-parser');

app.use(bodyParser.json())

// 사용자 정의 모듈
var userRouter = require('./router/userRouter');
var takerRouter = require('./router/takerRouter');
var protectorRouter = require('./router/protectorRouter');


app.use('/user', userRouter);

app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});


