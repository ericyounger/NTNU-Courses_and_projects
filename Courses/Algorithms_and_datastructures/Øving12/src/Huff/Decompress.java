package Huff;

import java.io.*;
import java.util.BitSet;

public class Decompress {

    public static void main(String[]args) throws IOException {
        String filepath = "src/txt/opg12_comp";

        String input = filepath + ".txt";
        String output = filepath +"_decomp.txt";
        DataInputStream innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(input)));
        DataOutputStream utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
        Huffman crusher = new Huffman();
        byte[] array;

        BitSet bs = new BitSet();
        int unusedBits;
        int[]frequency = new int[256];


        //read info to int[], int and byte[]
        for(int i=0; i<frequency.length; i++){
          frequency[i] = innfil.readInt();
        }

        unusedBits = innfil.readInt();
        array = new byte[innfil.available()];

        innfil.read(array);
        bs.valueOf(array);

        innfil.close();

        //Finished reading


        Node[] nodes = new Node[256];

        //add to node frequency array
        for(int i=0; i<frequency.length; i++){
            if(frequency[i]>0){
                byte a = (byte) i;
                nodes[i] = new Node(a);
                nodes[i].setFrequency(frequency[i]);
            } else{
                nodes[i] = new Node((byte)-1);
                nodes[i].setFrequency(0);
            }
        }

        Node root = crusher.createTree(nodes);


        int bitIndex =7;
        Node current = root;
        int unSignificantBit = 0;

        BitSet a = BitSet.valueOf(array);
        int writeFrom = 0;
        for(int i=0; i<a.length(); i++){
            if(a.get(i) == true){
                current = current.getRight();
            } else{
                current = current.getLeft();
            }

            if(current.getLeft() == null && current.getRight() == null){
                utfil.writeByte(current.getLetter());
                current = root;
            }
        }


        utfil.close();

    }



    private static Boolean isBitSet(byte b, int bit) {
        return (b & (1 << bit)) != 0;
    }


}
