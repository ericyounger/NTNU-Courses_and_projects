// @ flow
let app = require('express')();
let http = require('http').createServer(app);
let io = require('socket.io')(http);
let axios = require('axios');

let no_of_articles = 0; // 0 from default, gets updated when new articles are posted
let newArticles = false; // Used for checks internally whether or not to emit messages to clients.
let users_connected = 0; // The number of clients connected to site.
let connections = []; // Holds all the connections in array.

/** Set socketserver to check for new articles every 5 seconds. */
setInterval(checkForNewArticles,5000);



io.on('connection', function(socket){
    connections.push(socket);
    users_connected++;
    console.log(`user #${users_connected} connected to server`);

    socket.on('disconnect', function () {
        console.log("user disconnected");
        users_connected--;
        connections.splice(socket, 1);
    });

    /** Intervall to check if server has found more articles, if found emit message to clients and set newArticles =false */
    setInterval(()=>{
        if(newArticles){
            connections.map(e => {
               e.emit('newArticle');
            });
            newArticles = false;
        }

    },1000);

});

http.listen(4000, function(){
    console.log('listening on *:4000');
});


/** Checks for new articles, and if new articles, sets newArticles = true and updates the number of articles */
function checkForNewArticles(){
    axios.get("http://localhost:8080/articles").then(res =>{
        if(res.data.length>no_of_articles){
            no_of_articles = res.data.length;
            newArticles = true;
        }
    });
}