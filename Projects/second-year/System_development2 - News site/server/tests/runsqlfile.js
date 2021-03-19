let mysql = require("mysql");
let fs = require("fs");

//Runs mysql scripts for creating tables and insert statements in local MySql database on Gitlab.
module.exports = function run(filename, pool, done) {
  console.log("runsqlfile: reading file " + filename);
  let sql = fs.readFileSync(filename, "utf8");
  pool.getConnection((err, connection) => {
    if (err) {
      console.log("runsqlfile: error connecting");
      done();
    } else {
      console.log("runsqlfile: connected");
      connection.query(sql, (err, rows) => {
        connection.release();
        if (err) {
          console.log(err);
          done();
        } else {
          console.log("runsqlfile: run ok");
          done();
        }
      });
    }
  });
};
