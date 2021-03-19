'use strict';
const sha1 = require("crypto-js/sha1");
const Base64 = require("crypto-js/enc-base64");
const net = require('net');

module.exports = class SimpleWebSocketLib{
    constructor(port){
        this.connections = [];
        this.wsServer = net.createServer(connection => {
            this.onData(connection);
            this.closeConnection(connection);

        });

        this.wsServer.on('error', error => {
              console.error('Error: ', error);
        });
    }

    listen = (port) => {
        this.wsServer.listen(port, () => {
            console.log(`WebSocket server listening on port ${port}\n`);
        });
    };

    emit = (str) => {
        let output = this.frameData(str);
        for(let i=0; i<this.connections.length; i++){
            this.connections[i].write(output);
        }
    };

    onData = (connection) => {
        connection.on("data", data => {
            if (data.toString().includes("HTTP/1.1")) {
                this.handShake(data, connection);
            } else {
                this.payLoadPackets(data, connection);
            }
        });
    };

    payLoadPackets = (data, connection) => {

        for (let i = 0; i < this.connections.length; i++) {

            if (Buffer.from(data)[0] === 136) {
                continue;
            }
            //Write the data to all other clients than "yourself"
            if (this.connections[i] !== connection) {
                let msg = this.decode(data);
                let frame = this.frameData(msg);
                this.connections[i].write(frame);
            }
            //write to self
            else if (this.connections[i] === connection) {
                connection.write(this.frameData("data sent to clients"));
            }
        }
    };

    closeConnection = (connection) => {
        connection.on('end', () => {
        console.log('Client disconnected');

        //Remove connection from array
        this.connections = this.connections.filter(con => con != connection);

        //Sends a FIN packet
        connection.end();
    });
    };

    handShake =(data, connection) => {
        console.log('Client connected\n');
        console.log('Data received from client: ', data.toString());

        //Add new connections to list of connections
        if (this.connections.indexOf(connection) === -1) {
            this.connections.push(connection);
        }

        //Put headers into array
        let headers = data.toString().split("\n");

        //Search for key in headers
        let key = "";
        headers.map(header => {
            if (header.indexOf("Sec-WebSocket-Key:") > -1) {
                let indexStart = header.indexOf("Key:");
                key = header.substring(indexStart + 4).trim();
            }
        });

        //Hash with SHA1 and Encode entire string with Base64
        let hash = sha1(key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
        let encoded = Base64.stringify(hash);

        //Return header message to client
        connection.write("HTTP/1.1 101 Switching Protocols\r\n");
        connection.write("Upgrade: websocket\r\n");
        connection.write("Connection: Upgrade\r\n");
        connection.write("Sec-WebSocket-Accept: " + encoded.trim() + " \r\n");
        connection.write("\r\n");
    };


    decode = (hex) => {
        let msg = "";

        //Hex in form of byte array
        let bytes = Buffer.from(hex);

        //byte[1] specifies length
        let length = bytes[1] & 127;
        let maskStart = 2;
        let dataStart = maskStart + 4;
        for (let i = dataStart; i < dataStart + length; i++) {
            //Unmask bytes
            let byte = bytes[i] ^ bytes[maskStart + ((i - dataStart) % 4)];
            //add character to string
            msg += String.fromCharCode(byte);
        }

        //return completed message
        return msg;
    };

    frameData = (str) => {
    //Stacks the data in correct standard for data frame.
    let frame = [];

    //First byte is Type, Type =0x81 is text
    let type = 0x81;
    frame.push(type);
    let length = str.length;

    //We can only send 127 bytes at a time.
    if (length > 127) {
        return frameData("max of 127 bytes")
    }

    //Second byte is maskedAndLength
    //We do not mask our data from the server. Leftmost bit would be 1 if we did.
    let maskedAndLength = 0b00000000 | length;
    frame.push(maskedAndLength);

    //Bytes of string to send
    let payload = Buffer.from(str);

    //Add the data to frame
    for (let i = 0; i < payload.length; i++) {
        frame.push(payload[i]);
    }

    //Return a byte arrays of frame
    return Buffer.from(frame);
}
};
