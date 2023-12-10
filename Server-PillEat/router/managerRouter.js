const express = require('express');
var router = express.Router()

var manager = require('../lib/manager');

// 사용자 정보 목록 요청 (완성)
router.get('/user-list', (req, res) => {
    manager.userList(req, res);
});

module.exports = router;