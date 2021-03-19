package distributedProgramming;

import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.util.ArrayList;


class Server{



    public static void main(String[]args) throws IOException {

        final int PORT = 7000;
        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("Server is running on port " + PORT);
        ArrayList<ConnectionHandler> connections = new ArrayList<>();
        Socket connection = null;

        while (true) {
            connection = ss.accept();
            ConnectionHandler t = new ConnectionHandler(connection);
            t.start();
            connections.add(t); // Don't really need this, but have connections in reference if needed later on.
        }


    }

}