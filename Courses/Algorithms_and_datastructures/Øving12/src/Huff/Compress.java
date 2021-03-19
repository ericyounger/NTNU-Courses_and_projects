package Huff;

import java.io.*;

public class Compress{
    static String input = "src/txt/opg12.txt" ;
    static String output = "src/txt/opg12_comp.txt";


    public static void main(String[] args) throws IOException{
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(input)));
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
        Huffman c = new Huffman();

        //Read to byte arrray
        byte[] array = new byte[innfil.available()];
        innfil.readFully(array);
        innfil.close();


        //Set frequency and return array.
        Node[] frequency = c.setFrequency(array);
        Node root = c.createTree(frequency);


        c.writeToFile(utfil, array, frequency);
    }

}

