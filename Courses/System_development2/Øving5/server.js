var express = require("express");
var mysql = require("mysql");
var bodyParser = require("body-parser");
var app = express();
var apiRoutes = express.Router();
app.use(bodyParser.json()); // for å tolke JSON
const PersonDao = require("./dao/persondao.js");

var pool = mysql.createPool({
  connectionLimit: 2,
  host: "host",
  user: "user",
  password: "password",
  database: "databasename",
  debug: false
});

let personDao = new PersonDao(pool);

app.get("/person", (req, res) => {
  console.log("/person: fikk request fra klient");
  personDao.getAll((status, data) => {
    res.status(status);
    res.json(data);
  });
});

app.get("/person/:personId", (req, res) => {
  console.log("/person/:personId: fikk request fra klient");
  personDao.getOne(req.params.personId, (status, data) => {
    res.status(status);
    res.json(data);
  });
});

app.post("/person", (req, res) => {
  console.log("Fikk POST-request fra klienten");
  personDao.createOne(req.body, (status, data) => {
    res.status(status);
    res.json(data);
  });
});


app.delete("/person", (req, res) => {
	console.log("Fikk DELETE-request fra klienten");
	personDao.deleteOne(req.body, (status, data) =>{
		res.status(status);
		res.json(data);
	});
});

app.put("/person", (req, res) => {
	console.log("Fikk UPDATE-request fra klienten");
	personDao.updateOne(req.body, (status, data) =>{
		res.status(status);
		res.json(data);
	});
});

var server = app.listen(8080);
