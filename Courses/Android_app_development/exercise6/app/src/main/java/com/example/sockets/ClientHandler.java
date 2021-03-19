package com.example.sockets;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
* Handles the communication for socket accepted from Server.java
*/
public class ClientHandler extends Thread {
    private final static String TAG = "ServerThread";
    private Socket s;

    public ClientHandler(Socket s){
        this.s = s;
    }

    public void run(){
        Socket s 			= this.s;
        PrintWriter out		= null;
        BufferedReader in 	= null;
        try {
            Log.v(TAG, "Client connected");
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            //Acknowledgement on connection sent to client.
            out.println("Welcome client");//send text to client

            //receive calculation operation from client.
            String res = in.readLine();
            Log.i(TAG,"Message from client: " + res);

            // Parse text to doubles
            int first, second;
            int index = res.indexOf("+");
            first = Integer.parseInt(res.substring(0,index));
            second = Integer.parseInt(res.substring(index+1, res.length()));

            Log.i("parse", Integer.toString(first));

            //Calculate and return result to client socket.
            int result = first+second;
            out.println(result);

        } catch (IOException e){

        }

    }
}
