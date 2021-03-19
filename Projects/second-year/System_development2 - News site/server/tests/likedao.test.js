let mysql = require("mysql");
const LikeDao = require("../dao/likedao.js"); // import comments dao to use it's methods.
let pool = require("./connectionDB"); // Gitlab CI Pool


let likeDao = new LikeDao(pool);

afterAll(() => {
    pool.end();  // release resources after test.
});

/** Test to updates likes on article, expects status-code=200(OK) */
test("update like on article db", done =>{
    function callback(status, data) {
        console.log("Test callback: status=" + status + ", data.length=" + data.length);
        expect(status).toBe(200);
        done();
    }
    let list = [20,1];
    likeDao.setLikes(callback, list);
});

/** Test to get all likes based on article_id, expects 0 or more */
test("get likes on article db", done => {
    function callback(status, data) {
        console.log("Test callback: status=" + status + ", data.length=" + data.length);
        expect(data.length).toBeGreaterThanOrEqual(0);
        done();
    }
    likeDao.getLikes(callback,1)
});

