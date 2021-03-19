/* Simple Authentication server
* Should use proper framework for serious usage
* Also should JWT tokens instead of random tokens.
*/

const express = require('express');
const crypto = require('crypto');
const bodyParser = require('body-parser');
const app = express();
const port = 4000;
let cors = require("cors");
const corsOptions = {
	exposedHeaders: 'Authorization',
  };
  app.use(cors(corsOptions));
app.use(bodyParser.json())

const iterations = 1024;
const keylen = 512;
const digest = 'sha512';
let tokens = [];
users = {
	"eric" : {
	salt : "salt",
	hash : "hash"
},
};

//All paths with api uses authenticate
app.use('/api', authenticate); 


app.get('/api/hello', (req, res) => {
	console.log("Fetching hello world");
	res.send('Hello World!');
})

app.get('/api/test', (req, res) => {
	console.log("Test endepunkt med bearer token");
	res.send("Brukt bearer token");
})

app.post('/register', (req, res) => {
	//Create hash and send to client
	console.log("Request to register");
	let client_hash = req.body.client_hash;
	let user = users[req.body.username];

	let generatedSalt = crypto.randomBytes(5).toString('hex');
	let generatedHASH = crypto.pbkdf2Sync(client_hash,generatedSalt,iterations,keylen,digest);
	users[req.body.username] = {salt: generatedSalt, hash: generatedHASH.toString('hex')};
	res.end();
});

app.post('/auth',(req, res) => {
	console.log("/auth route called")
	let client_hash = req.body.client_hash;
	let user = users[req.body.username];

	if(user && user.hash === crypto.pbkdf2Sync(client_hash, user.salt,iterations,keylen,digest).toString('hex')) {
		// User is authenticated
		// Send an access token so that we do not
		// need to do all this again too soon,
		// it is computationally expensive after all
		console.log("User authenticated");
		const token = crypto.randomBytes(10).toString('hex');
		tokens.push("Bearer " + token);
		res.setHeader('Authorization', 'Bearer ' + token);
		res.end();
	} else{ 
		res.sendStatus(401);
	}
})


function authenticate(req, res, next){
	console.log("Authenticating");
	let token = req.header('Authorization');

	if(token){
		if(tokens.includes(token)){
			console.log("Token is OK");
			next();
		} else {
			console.log("Unauthorized");
			res.sendStatus(401);
		}
	} else {
		console.log("No token header given");
		res.sendStatus(401);
	}
	

}

app.listen(port, () => {
	console.log(`Server listening at http://localhost:${port}`);
})