let mysql = require("mysql");

/** The actual connection to the database used for the live website */
let pool = mysql.createPool({
    connectionLimit: 4,
    host: "mysql-ait.stud.idi.ntnu.no",
    user: "ericy",
    password: "4z6kcF1F",
    database: "ericy",
    debug: false,
    multipleStatements: true
});

module.exports = pool;