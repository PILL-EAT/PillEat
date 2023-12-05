const express = require('express');
var router = express.Router()

var user = require('../lib/user');


//로그인
router.post('/login', (req, res) => {
    user.login(req, res);
});

//로그아웃
router.get('/logout', (req, res) => {
    user.logout(req, res);
});

//회원가입
router.post('/join', (req, res) => {
    user.join(req, res);
});
//회원탈퇴
router.delete('/userDelete/:userId', (req, res) => {
    user.join(req, res);
});




module.exports = router;