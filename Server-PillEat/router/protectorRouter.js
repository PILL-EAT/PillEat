const express = require('express');
var router = express.Router()

var protector = require('../lib/protector');

// 복용자 입력 요청 (작성 예정)
router.post('/input-taker/:protectorId', (req, res) => {
    protector.inputTaker(req, res);
});

module.exports = router;