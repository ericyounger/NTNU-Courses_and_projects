package Oppgave2;

public class HashTable {
    private Integer[]hashTable = new Integer[6000011];
    private int collisions=0;


    public int addToTable(int  nr){
        int m = hashTable.length;
        int h1 = divHash(nr,m);
        int h2 = divHash2(nr, m);
        for(int i=0; i<m; i++){
            int j = probe(h1,h2,i,m);
            if(hashTable[j] == null){
                hashTable[j] = nr;
                return j;
            }
            collisions++;
        }
        return -1;
    }

    public double getLastFaktor(){
        return ((double)countUsed()/(double)hashTable.length);

    }

    public int divHash(int nr, int length){
        return nr % length;
    }

    public int divHash2(int nr, int length){
        return nr% (length-1) +1;
    }

    private int probe(int h1, int h2, int i, int m) {
        return (h1 + i * h2) % m;
    }

    public int countUsed() {
        int sum = 0;
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                sum++;
            }
        }
        return sum;
    }

    public int getCollisions(){
        return collisions;
    }

}
