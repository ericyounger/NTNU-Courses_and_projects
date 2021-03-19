package Huff;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;

public class Huffman {
    boolean found = false;
    int foundIndex = -1;
    static int bsIndex = 0;


    public Node[] setFrequency(byte[] array){
        Node[] frequency = new Node[256];
        for(int i=0; i<array.length; i++){
            int indexToPutIn = Byte.toUnsignedInt(array[i]);
            if(frequency[indexToPutIn] == null){
                frequency[indexToPutIn] = new Node(array[i]);
            } else{
                frequency[indexToPutIn].increaseFrequency();
            }
        }

        for(int i=0; i<frequency.length; i++){
            if(frequency[i] == null){
                frequency[i] = new Node((byte)-1);
                frequency[i].setFrequency(0);
            }
        }

        return frequency;
    }

    public Node createTree(Node[] frequency){
        ArrayList<Node> queue = new ArrayList<>();
        Node rootReturn = new Node((byte)-1);

        //Copy to arraylist to work on own elements. For queue.
        for(int i=0; i<frequency.length; i++){
            if((frequency[i].getFrequency()) != 0){
                queue.add(frequency[i]);
            }
        }


        while(queue.size() != 1){
            Collections.sort(queue);

            Node left = queue.get(0);
            Node right = queue.get(1);
            Node root = new Node((byte)-1);

            int sumFrequency = left.getFrequency() + right.getFrequency();
            root.setFrequency(sumFrequency);

            root.setLeft(left);
            root.setRight(right);

            left.setRoot(root);
            left.setVertex(0);
            right.setRoot(root);
            right.setVertex(1);

            queue.remove(0);
            queue.remove(0);
            queue.add(root);

            if(queue.size() == 1){
                rootReturn =root;
            }
        }
        return rootReturn;
    }

    public String buildTraversalBitset(Node leafnode) {
        String bitset = "";
        Node current = leafnode;

        while (current.getRoot() != null) {
            bitset += current.getVertex();
            current = current.getRoot();
        }

        String returnV = new StringBuilder(bitset).reverse().toString();

        return returnV;
    }

    public void writeToFile(DataOutputStream utfil, byte[] array, Node[] frequency) throws IOException {
        BitSet bs = new BitSet();
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<array.length; i++){
            for(int j=0; j<frequency.length; j++){
                if(array[i] == frequency[j].getLetter()){
                    if(frequency[j].getFrequency() != 0){
                        String n = buildTraversalBitset(frequency[j]);
                        sb.append(n);
                    }
                }
            }

        }

        String completeBitSet = sb.toString();

        for(int i=0; i<completeBitSet.length(); i++){
            if(completeBitSet.charAt(i) == '0'){
                bs.set(i, false);
            } else{
                bs.set(i, true);
            }
        }


        for(int i=0; i<frequency.length; i++){
            utfil.writeInt(frequency[i].getFrequency());
        }

        utfil.writeInt(bs.length() % 8);


        byte[] bitsetToBytes = bs.toByteArray();

        System.out.println(bitsetToBytes.length);

        for(byte b: bitsetToBytes){
            utfil.write(b);
        }

        utfil.close();



    }

}
