package Oppgave2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Oppgave2 {
    public static void main(String[] args) throws IOException {
        HashTable hash = new HashTable();
        Random random = new Random();
        HashMap<Integer, Integer> java = new HashMap<Integer, Integer>(6000011);



        int[] numbers = new int[5000000];

        for(int i=0; i<5000000; i++){
            numbers[i] = random.nextInt(60000110);
        }

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            for(int j=0; j<numbers.length; j++){
                //hash.addToTable(numbers[j]);
                java.put(numbers[j],numbers[j]);
            }

            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)
                (slutt.getTime()-start.getTime()) / runder;
        System.out.println("Millisekund pr. runde:" + tid);
        System.out.println("Custom HashTable info:");
        System.out.println(hash.countUsed() + " inserted to hashTable");
        System.out.println(hash.getLastFaktor() + " = Lastfaktor");
        System.out.println(hash.getCollisions() + " antall kollisjoner");

    }
}
