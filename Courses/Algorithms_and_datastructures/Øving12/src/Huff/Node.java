package Huff;

public class Node implements Comparable<Node> {
    private byte letter;
    private int frequency = 0;
    private Node root;
    private Node left;
    private Node right;
    private int vertex;


    public Node(byte letter){
        this.letter = letter;
        increaseFrequency();
    }

    public byte getLetter() {
        return letter;
    }

    public int getFrequency(){
        return frequency;
    }

    public void increaseFrequency() {
        this.frequency += 1;
    }

    public int compareTo(Node node) {
        return this.frequency-node.getFrequency();
    }

    public void setRoot(Node root){
        this.root = root;
    }

    public void setLeft(Node left){
        this.left = left;
    }

    public void setRight(Node right){
        this.right = right;
    }

    public void setFrequency(int frequency){
        this.frequency = frequency;
    }

    public void setVertex(int vertex){
        this.vertex = vertex;
    }

    public Node getLeft(){
        return left;
    }

    public Node getRight(){
        return right;
    }

    public Node getRoot(){
        return root;
    }

    public int getVertex(){
        return vertex;
    }
}
