var express = require("express");
var mysql = require("mysql");
var bodyParser = require("body-parser");
var app = express();
app.use(bodyParser.json()); // for å tolke JSON
var cors = require("cors");
app.use(cors());
var pool = mysql.createPool({
  connectionLimit: 4,
  host: "mysql-ait.stud.idi.ntnu.no",
  user: "ericy",
  password: "4z6kcF1F",
  database: "ericy",
  debug: false
});

app.get("/articles/:priority", (req, res) => {
  console.log("Fikk request om artikler basert på prioritet");
  pool.getConnection((err, connection) => {
    console.log("Connected to database");
    if (err) {
      console.log("Feil ved kobling til databasen");
      res.json({
        error: "Feil ved oppkobling"
      });
    } else {
      connection.query(
        "select article_id, headline, content, priority, picture, post_date, category, author from article where priority=? and visible=1 limit 20",
        [req.params.priority],
        (err, rows) => {
          connection.release();
          if (err) {
            console.log(err);
            res.json({
              error: "error queying"
            });
          } else {
            console.log(rows);
            res.json(rows);
          }
        }
      );
    }
  });
});

app.put("/article", (req, res) => {
  console.log("Fikk update request på en artikkel");
  pool.getConnection((err, connection) => {
    console.log("Connected to database");
    if (err) {
      console.log("Feil ved kobling til databasen");
      res.json({
        error: "Feil ved oppkobling"
      });
    } else {
      var val = [
        req.body.headline,
        req.body.category,
        req.body.picture,
        req.body.priority,
        req.body.content,
        req.body.article_id
      ];
      connection.query(
        "update article set headline=?, category=?, picture=?, priority=?, content=? where article_id=?;",
        val,
        (err, rows) => {
          connection.release();
          if (err) {
            console.log(err);
            res.json({
              error: "error queying"
            });
          } else {
            console.log(rows);
            res.json(rows);
          }
        }
      );
    }
  });
});


app.get("/articles", (req, res) => {
  console.log("Fikk request om alle artikler");
  pool.getConnection((err, connection) => {
    console.log("Connected to database");
    if (err) {
      console.log("Feil ved kobling til databasen");
      res.json({
        error: "Feil ved oppkobling"
      });
    } else {
      connection.query(
        "select article_id, headline, content, priority, picture, post_date, category, author from article where visible=1 limit 20",
        (err, rows) => {
          connection.release();
          if (err) {
            console.log(err);
            res.json({
              error: "error queying"
            });
          } else {
            console.log(rows);
            res.json(rows);
          }
        }
      );
    }
  });
});

app.get("/article/:article_id", (req, res) => {
  console.log("Fikk request om artikler basert på artikkel id");
  pool.getConnection((err, connection) => {
    console.log("Connected to database");
    if (err) {
      console.log("Feil ved kobling til databasen");
      res.json({
        error: "Feil ved oppkobling"
      });
    } else {
      connection.query(
        "select article_id, headline, content, priority, picture, post_date, category, author from article where article_id=? and visible=1 limit 20",
        [req.params.article_id],
        (err, rows) => {
          connection.release();
          if (err) {
            console.log(err);
            res.json({
              error: "error queying"
            });
          } else {
            console.log(rows);
            res.json(rows);
          }
        }
      );
    }
  });
});

app.post("/article", (req, res) => {
  console.log("Fikk POST-Request fra klienten på artikkel");
  pool.getConnection((err, connection) => {
    if (err) {
      console.log("Feil ved oppkobling");
      res.json("Feil ved oppkobling");
    } else {
      console.log("Fikk databasekobling");
      var val = [
        req.body.headline,
        req.body.category,
        req.body.picture,
        req.body.priority,
        req.body.content,
        req.body.author
      ];
      connection.query(
        "insert into article(article_id, headline, post_date, category, picture, priority, visible, content, author) values (default,?,default,?,?,?,1,?,?)",
        val,
        err => {
          if (err) {
            console.log(err);
            res.status(500);
            res.json({
              error: "Feil ved insert"
            });
          } else {
            console.log("insert ok");
            res.send("");
          }
        }
      );
    }
  });
});

app.get("/category/:category", (req, res) => {
  console.log("Fikk request fra klient på category/:category");
  pool.getConnection((err, connection) => {
    console.log("connected to database");
    if (err) {
      console.log("Feil ved tilkobling til database");
      res.json({
        error: "feil ved oppkobling"
      });
    } else {
      connection.query(
        "select article_id, headline, content, priority, picture, post_date, category, author from article where category=? and visible=1 limit 20 ",
        [req.params.category],
        (err, rows) => {
          connection.release();
          if (err) {
            console.log(err);
            res.json({
              error: "error queying"
            });
          } else {
            console.log(rows);
            res.json(rows);
          }
        }
      );
    }
  });
});

app.delete("/article", (req, res) => {
  console.log("Fikk Delete-request fra klienten");
  pool.getConnection((err, connection) => {
    if (err) {
      console.log(err);
      res.json({
        error: "Feil ved oppkobling"
      });
    } else {
      console.log("Fikk databasetilkobling");
      var val = [req.body.article_id];
      connection.query(
        "UPDATE article SET visible = 0 WHERE article_id=?",
        val,
        err => {
          if (err) {
            console.log(err);
            res.status(500);
            res.json({
              error: "Feil ved delete"
            });
          } else {
            res.send("");
            console.log("delete ok");
          }
        }
      );
    }
  });
});

app.post("/comment", (req, res) => {
  console.log("Fikk post request fra klienten");
  pool.getConnection((err, connection) => {
    if (err) {
      console.log(err);
      res.json({
        error: "feil ved oppkobling"
      });
    } else {
      console.log("Fikk databasekobling");
      var val = [req.body.nickname, req.body.text_comment, req.body.article_id];
      connection.query(
        "insert into comments(c_id, nickname, text_comment, article_id) values(default, ?, ?, ?)",
        val,
        err => {
          if (err) {
            console.log(err);
            res.status(500);
            res.json({
              error: "Feil ved delete"
            });
          } else {
            console.log("insert ok");
            res.send("");
          }
        }
      );
    }
  });
});


//Denne er ikke ferdigskrevet, må sjekkes om den fungerer
app.get("/comments/:id", (req, res) => {
  console.log("Fikk request om kommentarer basert på en id");
  pool.getConnection((err, connection) => {
    console.log("Connected to database");
    if (err) {
      console.log("Feil ved kobling til databasen");
      res.json({
        error: "Feil ved oppkobling"
      });
    } else {
      connection.query(
        "select * from comments where article_id=?",
        [req.params.id],
        (err, rows) => {
          connection.release();
          if (err) {
            console.log(err);
            res.json({
              error: "error queying"
            });
          } else {
            console.log(rows);
            res.json(rows);
          }
        }
      );
    }
  });
});

var server = app.listen(8080);