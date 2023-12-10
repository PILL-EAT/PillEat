const express = require('express');
var router = express.Router()

var taker = require('../lib/taker');


// 등록한 약 목록 불러오기 (완성)
router.get('/drug/enroll-list', (req, res) => {
    taker.enrollList(req, res);
});

// 복용할 약 등록 (완성)
router.post('/drug/enroll', (req, res) => {
    taker.enroll(req, res);
});

// 복용 완료 요청(논의 예정)
router.post('/drug/enroll', (req, res) => {
    taker.finish(req,res);
});

// 등록한 약 삭제 요청 (완성)
router.delete('/drug/enroll-delete/:drugId', (req, res) => {
    taker.enrollDelete(req,res);
});

// 복용 완료 여부 리스트 불러오기 (완성)
router.get('/record/:userId/:date', (req, res) => {
    taker.record(req,res);
});

module.exports = router;