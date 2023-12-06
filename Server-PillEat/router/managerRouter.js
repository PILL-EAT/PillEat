const express = require('express');
var router = express.Router()

var user = require('../lib/user');

//test용
router.get('/userList', (req, res) => {
    user.userList(req, res);
});