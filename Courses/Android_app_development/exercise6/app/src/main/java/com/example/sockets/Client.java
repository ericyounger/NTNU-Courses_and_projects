package com.example.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;  
import java.net.Socket;

import android.app.Activity;
import android.util.Log;
  
public class Client extends Thread {   
	private final static String TAG = "Client";
	private final static String IP = "127.0.0.1";
	private final static int PORT = 12345;
	private String result = "";
	private int firstNumber, secondNumber;
	private MainActivity activity;


	public Client(Activity activity, int number1, int number2) {
	    this.firstNumber = number1;
	    this.secondNumber = number2;
	    this.activity = (MainActivity) activity;
    }
    
	public void run() {
    	Socket s 			= null;
    	PrintWriter out		= null;
    	BufferedReader in 	= null;
    	
        try {
            s = new Socket(IP, PORT); // Socket connection
            Log.v(TAG, "C: Connected to server" + s.toString());
            out = new PrintWriter(s.getOutputStream(), true); //Output writer
            in = new BufferedReader(new InputStreamReader(s.getInputStream())); // Inputreader

			//Welcome text
            String res = in.readLine();
            Log.i(TAG,res);

            //Send numbers for calculation
            out.println(firstNumber+"+"+secondNumber);

            //Receive result of calculation
            String result = in.readLine();
            Log.i("Result is: ", result);

            //Now return to main activity
			activity.onResultFromSocket(result);


        } catch (IOException e) {
            e.printStackTrace();
        }finally{//close socket!!
        	try{
	        	out.close();
	        	in.close();
	        	s.close();
        	}catch(IOException e){}
        }
    }


}