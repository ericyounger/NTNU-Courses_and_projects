package com.example.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;

public class Server extends Thread{
	private final static String TAG = "ServerListener";
	private final static int PORT = 12345;
	
	public void run() {
		ServerSocket ss 	= null;
		Socket s 			= null;

		try{
    		Log.i(TAG,"start server....");
            ss = new ServerSocket(PORT);
            Log.i(TAG,"serversocket created, wait for client....");
            while(true){
            	//Listens for new sockets, and hands off threads to a seperate sockethandler (ClientHandler)
				s = ss.accept();
				ClientHandler a = new ClientHandler(s);
				a.start();
				Log.i(TAG, "New connection");
			}

        } catch (IOException e) {
            e.printStackTrace();
        }finally{//close sockets!!
        	try{
        		s.close();
        		ss.close();
        	}catch(Exception e){}	            	
        }   
	}
}
