package udpSockets;

import java.io.*;
import java.net.*;

class UDPServer {
    enum Operations{
        ADD,
        SUB,
    }

    public static void main(String args[]) throws Exception
    {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];


        while(true)
        {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String sentence = new String( receivePacket.getData());

            System.out.println(sentence);
            String[] input = sentence.split(" ");
            Operations op = null;



            try{
                op = Operations.valueOf(input[0].toUpperCase());
                int no1 = Integer.parseInt(input[1].trim());
                int no2 = Integer.parseInt(input[2].trim());

                switch (op){
                    case ADD:
                        int resultAdd = no1 + no2;
                        String outADD = String.valueOf(resultAdd);
                        sendData = outADD.getBytes();
                        break;

                    case SUB:
                        System.out.println(input[0]);
                        System.out.println("no1 : " + no1 + " no2: " + no2);
                        int resultSub = no1 - no2;
                        String outSub = String.valueOf(resultSub);
                        sendData = outSub.getBytes();
                        break;
                }

            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }



            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
            sentence = ""; // Clears sentence for next computation;
            sendData = new byte[1024];
            receiveData = new byte[1024];
            input = null;

        }

    }


}