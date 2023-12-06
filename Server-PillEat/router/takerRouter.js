const express = require('express');
var router = express.Router()

var taker = require('../lib/user');


//복용시간 알림
router.get('/drug/alert/:takerId', (req, res) => {
    user.alert(req, res);
});





module.exports = router;