let mysql = require("mysql");
const CategoryDao = require("../dao/categorydao.js"); // import category dao to use it's methods.
let pool = require("./connectionDB"); // Gitlab CI Pool



let categoryDao= new CategoryDao(pool); //Tests uses local mysql connection pool

afterAll(() => {
    pool.end(); // release resources after test.
});

/** Test to get alle categories from database, expect one or more. */
test("get categories from db", done => {
    function callback(status, data) {
        console.log("Test callback: status=" + status + ", data.length=" + data.length);
        expect(data.length).toBeGreaterThanOrEqual(1);
        done();
    }
    categoryDao.getAll(callback)
});


