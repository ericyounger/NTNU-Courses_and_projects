package Oppgave1;

public class HashTable {
    private Node[]hashTable = new Node[193];


    public boolean addToTable(String data){

        int index = divHash(data);
        Node entry = new Node(data);

        if(hashTable[index] != null){
            System.out.println("Kollisjon på innsetting index: " + index + "  navn: " + data  + " og " + hashTable[index].data);

            if(hashTable[index].getNext() != null){

                Node n = hashTable[index].next;

                while(n.getNext() != null){
                    n = n.next;
                    System.out.println(n);
                }

                n.setNext(entry);
                return false;


            } else{
                hashTable[index].setNext(entry);
                return false;
            }
        } else {
            hashTable[index] = entry;
            return true;
        }

    }

    public double getLastFaktor(int n){
        return ((double)n/(double)hashTable.length);

    }

    public int divHash(String data){
        int unicode = 0;
        for(int i=0; i<data.length(); i++){
            unicode += ((int) data.charAt(i)) * i;
        }
        return unicode % hashTable.length;
    }

    public String getElement(String søk){
        int unicode = 0;
        for(int i=0; i<søk.length(); i++){
            unicode += (int) søk.charAt(i);
        }
        int index = divHash(søk);

        if(hashTable[index].getData().equals(søk)){
            return hashTable[index].getData() + " found at index: " + index;
        } else{
            //traverserer gjennom linked list


            Node n = hashTable[index].next;
            if(n.getData().equals(søk)){
                System.out.println("Kollisjon på søk index: " + index + "  navn: " + søk  + " og " + hashTable[index].data);
                return n.getData();
            } else{
                while(n.getNext() != null){
                    n = n.next;
                    if(n.getData().equals(søk)){
                        System.out.println("Kollisjon på søk index: " + index + "  navn: " + søk  + " og " + hashTable[index].data);
                        return n.getData() + " found at index: " + index;
                    } else{
                        return null;
                    }
                }
            }








            return null;
        }

    }



    class Node{
        private Node next;
        private String data;

        public Node(String data){
            this.data = data;
        }

        public Node getNext(){
            return next;
        }

        public String getData(){
            return data;
        }

        public void setNext(Node next){
            this.next = next;
        }
    }

}
