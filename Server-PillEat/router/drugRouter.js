const express = require('express');
var router = express.Router()

var drug = require('../lib/drug');

//약 검색
router.get('/search/:drug', (req, res) => {
    drug.search(req, res);
});

module.exports = router;