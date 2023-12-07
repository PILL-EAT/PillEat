var db = require('./db');

module.exports = {
    search: (req, res) => {
        const drug = req.params.drug;

        db.query('SELECT * FROM pill_info WHERE pill_name = ?;', [drug], (err, result) => {
            if (err) {
                console.error(err);
                const responseData = {
                    isSuccess: false,
                    code: 600,
                    message: "요청에 실패하였습니다.",
                    result: null
                };
                return res.json(responseData);
            }else{
                const drugs = result.map(result => ({
                    name: result.pill_name,
                    use: result.pill_use,
                }));
    
                const responseData = {
                    isSuccess: true,
                    code: 200,
                    message: "요청에 성공하였습니다.",
                    result: {
                        drugs: drugs
                    }
                };
                res.json(responseData);
            }
        });
    },
};
