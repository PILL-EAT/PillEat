// schedulerSetup.js
const schedule = require('node-schedule');
const db = require('./db');

function setupScheduler() {
    const Schedule = schedule.scheduleJob('0 0 * * *', () => {
        var currentDate = new Date();
        var dayOfWeek = currentDate.getDay();
        dayOfWeek = (dayOfWeek === 0) ? 7 : dayOfWeek;

        var year = currentDate.getFullYear();
        var month = ('0' + (currentDate.getMonth() + 1)).slice(-2);
        var day = ('0' + currentDate.getDate()).slice(-2);

        var date = `${year}-${month}-${day}`;

        dayOfWeek = (dayOfWeek === 0) ? 7 : dayOfWeek;

        var date = currentDate.toISOString().split('T')[0];
        db.query(`INSERT INTO pill_history (date, pill_alert_id, is_taken)
            SELECT '${date}' as date, pill_alert_id, false as is_taken
            FROM pill_alert
            WHERE SUBSTRING(alert_day, '${dayOfWeek}', 1) = '1';`,
            (err, result) => {
                if (err) {
                    throw (err);
                }
                console.log("pill_history UPDATE")
                db.query('select * from pill_history', (err, result) => {
                    console.log(result)
                })
            })
    });
}

module.exports = setupScheduler;
