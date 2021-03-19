let mysql = require("mysql");
const CommentsDao = require("../dao/commentsdao.js"); // import comments dao to use it's methods.
let pool = require("./connectionDB"); // Gitlab CI Pool


let commentDao = new CommentsDao(pool); //Tests uses local mysql connection pool


afterAll(() => {
    pool.end(); // release resources after test.
});

/** Test to post a new comment on article, expect status-code=200(OK) */
test("post comment on article db", done =>{
    function callback(status, data){
        console.log("Test callback: status=" + status + ", data.length=" + data.length);
        expect(status).toBe(200);
        done();
    }

    let list = ["Ericmeister","Ikke enig!!",1];
    commentDao.postComment(callback, list);
});


/** Test to get all comments based on article_id, expecs to have 1 or more */
test("get comments on article db", done => {
    function callback(status, data) {
        console.log("Test callback: status=" + status + ", data.length=" + data.length);
        expect(data.length).toBeGreaterThanOrEqual(1);
        done();
    }
    commentDao.getComments(callback, 1);
});



