import React, {useState} from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
const axios = require('axios').default;
const crypto = require('crypto');


function App() {
    const [loggedIn, setLoggedIn] = useState(false);
    const [displayText, setDisplayText] = useState("Hello World");

    function nyttKallMedBearerToken() {
        const header = {
            headers : {
                "Authorization": localStorage.getItem("bearer"),
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
            }
        };
        console.log(header);
        axios.get("http://localhost:4000/api/test", header).then(res => {
            if(res.status === 200) setDisplayText(res.data);
        }).catch(rej => console.log(rej));
    }
    if(!loggedIn){
        return (
            <div className="App">
                <div className="authContainer">
                    <h1>Authentication</h1>
                    <div className="row">
                        <div className="col"><Login loginCallback={setLoggedIn}/></div>
                    </div>
                </div>
            </div>
        );
    } else {
        return(
            <div>
                {displayText}
                <p><button onClick={nyttKallMedBearerToken}>Kjør et nytt kall med bruk av bearer token</button></p>
            </div>
        )
    }

}

function Login({loginCallback}){
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    function loginUser() {
        const salt = "salt";
        let hash = crypto.pbkdf2Sync(password, salt,1024,15, 'sha512').toString('utf-8');
        console.log(hash);
        const data = {username: username, client_hash: hash};


        axios.post("http://localhost:4000/auth", data).then(res => {
            localStorage.setItem("bearer", res.headers.authorization);

            const header = {
                headers : {
                    "Authorization": localStorage.getItem("bearer"),
                    'Content-Type': 'application/json;charset=UTF-8',
                    "Access-Control-Allow-Origin": "*",
                }
            };
            console.log(header);
            axios.get("http://localhost:4000/api/hello", header).then(res => {
                if(res.status === 200) loginCallback(true);
            }).catch(rej => console.log(rej));



        }).catch(rej => setErrorMessage("Feil under autentisering"));



    }

    function register(){
        const salt = "salt";
        let hash = crypto.pbkdf2Sync(password, salt,1024,15, 'sha512').toString('utf-8');
        console.log(hash);
        const data = {username: username, client_hash: hash};
        axios.post("http://localhost:4000/register", data).then(res => {
            if(res.status == 200){
                alert("Registrering ok");
            } else {
                alert("Noe gikk galt");
            }
        }).catch(rej => console.log(rej));

    }
    return(
        <div>
            <br/><br/>
            username:
            <div className="row">
                <div className="col"><input type="text" onChange={(event) => setUsername(event.target.value)}/></div>
            </div>
            password:
            <div className="row">
                <div className="col"><input type="password" onChange={(event) => setPassword(event.target.value)}/></div>
            </div>

            <div className="row">
                <div className="col">
                    <br/>
                    <button onClick={register}>Sign-up</button>
                    <button onClick={loginUser}>Login</button>
                </div>
            </div>
            {errorMessage?<div className="row"><div className="col">{errorMessage}</div></div>:null}
        </div>
    )
}

export default App;
