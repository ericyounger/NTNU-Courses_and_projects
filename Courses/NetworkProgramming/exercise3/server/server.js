

const express = require("express");
const bodyParser  = require('body-parser');
const app = express();
app.use(bodyParser.json());
app.use ( bodyParser.json( { type: '*/*' } ));
const { exec } = require("child_process");

let cors = require("cors");
app.use(cors());



const fs = require('fs');

const port =  4000;



app.post('/run/node', (req, res) => {
	console.log('Running javascript code');
	let code = req.body.input;
	writeAndRunNodeFile(code, res);
});





function writeAndRunNodeFile(code, res){
	let consoleLog = [];
	fs.writeFileSync('./nodeRunner.js', code);

		let script = exec("node nodeRunner.js", (error, stdout, stderr) => {
			if (error) {
				//console.log(`error: ${error.message}`);
				consoleLog.push(error.message.toString());
				return;
			}
			if (stderr) {
				//console.log(`stderr: ${stderr}`);
				consoleLog.push(stderr.toString());
				return;
			}

			//console.log(`stdout: ${stdout}`);
			consoleLog.push(stdout);
			console.log(stdout);
		});

		script.on('close', () => {
			console.log("Finished");
			res.send(consoleLog);

		});
}


app.get('/run/python', (req, res) => {
	console.log('Running python code');
});

app.get('/run/java', (req, res) => {
	console.log("Running java code");
});

app.get('/run/cpp', (req, res) => {
	console.log('Running c++ code');
});

app.get('/run/csharp', (req, res) => {
	console.log('Running C# code');
});



app.listen(port, () => {
	console.log('Server running on port ' + port);
});