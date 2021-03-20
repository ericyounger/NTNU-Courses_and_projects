// @flow

/** Runs once before all tests to setup database and tables */
const regeneratorRuntime = require('regenerator-runtime/runtime');
const mysql = require('mysql');
const runsqlfile = require('./runsqlfile.js');

module.exports = async () => {
    const pool: mysql.Pool = mysql.createPool({
        connectionLimit: 1,
        host: 'mysql',
        user: 'root',
        password: 'secret',
        database: 'supertestdb',
        debug: false,
        multipleStatements: true
    });



    runsqlfile("tests/sql/createTable.sql", pool, () => {
        runsqlfile("tests/sql/create_testdata.sql", pool, () => {
            console.log('done reading sql file');
        });
    });

    // Set reference to mongod in order to close the server during teardown.
    global.__MONGOD__ = pool;
};
