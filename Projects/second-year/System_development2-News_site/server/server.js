// @flow

let express = require("express");
let bodyParser = require("body-parser");
let app = express();
app.use(bodyParser.json()); // for å tolke JSON
let cors = require("cors");
app.use(cors());
let pool = require("./connectionDB");

const ArticleDao = require('./dao/articledao.js');
const CommentsDao = require('./dao/commentsdao.js');
const LikeDao = require('./dao/likedao.js');
const CategoryDao = require('./dao/categorydao.js');

let articledao = new ArticleDao(pool);
let commentdao = new CommentsDao(pool);
let likedao = new LikeDao(pool);
let categorydao = new CategoryDao(pool);


/** Get all categories from database and return to client */
app.get("/categories", (req :  express$Request, res : express$Response) => {
  console.log("Fikk request om kategorier");
  categorydao.getAll((status, data) =>{
    res.status(status);
    res.json(data);
  });
});

/** Update article's priority */
app.put("/articles/priority", (req :  express$Request, res : express$Response) => {
  console.log("Fikk request om endring av prioritet");
  let val = [
    req.body.priority,
    req.body.article_id
  ];

  articledao.setPriority((status, data) =>{
    res.status(status);
    res.json(data);
  }, val);
});


/** Get articles based on priority and return to client */
app.get("/articles/priority/:priority", (req :  express$Request, res : express$Response) => {
  console.log("Fikk request om artikler basert på prioritet");
  articledao.getByPriority((status, data) => {
    res.status(status);
    res.json(data);
  }, req.params.priority);
});

/** Get likes on articles based on 'article_id' and return to client */
app.get("/articles/likes/:id", (req :  express$Request, res : express$Response) => {
  console.log("Fikk request om likes på artikkel");
  likedao.getLikes((status, data) =>{
    res.status(status);
    res.json(data);
  }, req.params.id);
});

/** Update likes on a article based on json sent with body with tags: 'likes' and 'article_id' */
app.put("/articles/likes", (req :  express$Request, res : express$Response) => {
  console.log("Fikk update request på likes på en artikkel");
  let val = [req.body.likes, req.body.article_id];

  likedao.setLikes((status, data) =>{
    res.status(status);
    res.json(data);
  }, val);
});

/** Update article based on json sent with body with tags:
 *  'headline', 'category', 'picture', 'priority', 'content' and 'article_id' */
app.put("/articles", (req :  express$Request, res : express$Response) => {
  console.log("Fikk update request på en artikkel");
  let val = [
    req.body.headline,
    req.body.category,
    req.body.picture,
    req.body.priority,
    req.body.content,
    req.body.article_id
  ];
  articledao.updateOne((status, data) => {
    res.status(status);
    res.json(data);
  }, val);
});

/** Get all visisble(not deleted) articles in database */
app.get("/articles", (req :  express$Request, res : express$Response) => {
  console.log("Fikk request om alle artikler");
  articledao.getAll((status, data) => {
    res.status(status);
    res.json(data);
  });
});

/** Get article based on 'article_id' and return to client */
app.get("/articles/:article_id", (req :  express$Request, res : express$Response) => {
  console.log("Fikk request om artikler basert på artikkel id");
  articledao.getOne((status, data) => {
    res.status(status);
    res.json(data);
  }, req.params.article_id);
});

/** Post a new article to database. Takes a JSON in body*/
app.post("/articles", (req :  express$Request, res : express$Response) => {
  console.log("Fikk POST-Request fra klienten på artikkel");
  let val = [
    req.body.headline,
    req.body.category,
    req.body.picture,
    req.body.priority,
    req.body.content,
    req.body.author
  ];

  articledao.createOne((status, data) => {
    res.status(status);
    res.json(data);
  }, val);

});

/** Get all articles based on 'category' and return to client */
app.get("/articles/category/:category", (req :  express$Request, res : express$Response) => {
  console.log("Fikk request fra klient på category/:category");
  articledao.getByCategory((status, data) => {
    res.status(status);
    res.json(data);
  }, req.params.category);
});


/** Delete article, actually sets visible = false. Takes a json in body with tag 'article_id' as a
 * security measure to avoid deleting by accident */
app.delete("/articles", (req :  express$Request, res : express$Response) => {
  console.log("Fikk Delete-request fra klienten");
  articledao.deleteOne((status, data) => {
    res.status(status);
    res.json(data);
  }, req.body.article_id)
});

/** Post a new comment to a article, takes a json in body with tags: 'nickname', 'text_comment' and 'article_id'*/
app.post("/comments/add", (req :  express$Request, res : express$Response) => {
  console.log("Fikk post request fra klienten");
  let val = [req.body.nickname, req.body.text_comment, req.body.article_id];
  commentdao.postComment((status, data) => {
    res.status(status);
    res.json(data);
  }, val);
});

/** Get all comments based on 'article_id' in parameter */
app.get("/comments/:id", (req :  express$Request, res : express$Response) => {
  console.log("Fikk request om kommentarer basert på en artikkel_id");
  commentdao.getComments((status, data) =>{
    res.status(status);
    res.json(data);
  }, req.params.id);
});

let server = app.listen(8080);