let mysql = require("mysql");
const ArticleDao = require("../dao/articledao.js"); // import article dao to use it's methods.
let pool = require("./connectionDB"); // Gitlab CI Pool


let articleDao = new ArticleDao(pool); //Tests uses local mysql connection pool


afterAll(() => {
    pool.end(); // release resources after test.
});


/** Test to get all articles from database, and expect to receive more than one article */
test("get all articles from db", done => {
    function callback(status, data) {
        console.log("Test callback: status=" + status + ", data.length=" + data.length);
        expect(data.length).toBeGreaterThanOrEqual(2);
        done();
    }
    articleDao.getAll(callback);
});

/** Test to get one article from database, expect to only receive one item */
test("get one article from db", done => {
    function callback(status, data) {
        console.log("Test callback: status=" + status + ", data.length=" + data.length);
        expect(data.length).toBe(1);
        done();
    }
    articleDao.getOne(callback,1);
});

/** Test to get articles by priority, and expect more items than one article */
test("get articles by priority from db", done =>{
   function callback(status, data){
       console.log("Test callback: status=" + status + ", data.length="+data.length);
       expect(data.length).toBeGreaterThan(2);
       done()
   }
   articleDao.getByPriority(callback, 1);
});

/** Test to get articles by category, expect one or more articles */
test("get articles by category from db", done =>{
   function callback(status, data){
       console.log("Test callback: status=" + status + ", data.length="+data.length);
       expect(data.length).toBeGreaterThanOrEqual(1);
       done()
   }
   articleDao.getByCategory(callback, "Lifestyle");
});

/** Test to post a new article to database, expects status code=200(OK) */
test("post article to db", done =>{
    function callback(status, data){
        console.log("Test callback: status=" + status + ", data.length="+data.length);
        expect(status).toBe(200);
        done()
    }

    let list = ["Overskrift","Lifestyle","bildelink",1,"lololol","Eric"];

    articleDao.createOne(callback, list);
});

/** Test to update article on database, expect status code=200(OK) */
test("update article db", done =>{
   function callback(status, data){
       console.log("Test callback: status=" + status + ", data.length="+data.length);
       expect(status).toBe(200);
       done()
   }

   let list = ["Oppdatert overskrift", "Politics", "nybildelink", 2,"innhold",1];
   articleDao.updateOne(callback, list);
});

/** Test to delete(set visible=0) one article from database, expect status code=200(OK) */
test("delete article from db", done =>{
   function callback(status, data){
       console.log("Test callback: status=" + status + ", data.length="+data.length);
       expect(status).toBe(200);
       done()
   }

   articleDao.deleteOne(callback,1);
});



