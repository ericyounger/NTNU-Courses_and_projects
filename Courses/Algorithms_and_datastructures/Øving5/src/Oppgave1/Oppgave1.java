package Oppgave1;

import Oppgave1.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Oppgave1 {

    public static void main(String[] args) throws IOException {
        HashTable hash = new HashTable();

        FileReader fr = new FileReader("src/Oppgave1/navn.txt");
        BufferedReader reader = new BufferedReader(fr);

        String read = "";
        int collision =0;
        int adds = 0;
        while((read = reader.readLine()) != null){
            adds++;
            if(!hash.addToTable(read)){
                collision++;
            }

        }

        System.out.println(hash.getElement("Gultvedt,Even"));
        System.out.println("Antall kollisjoner: " + collision);
        System.out.println(hash.getLastFaktor(adds) + " er lastfaktor");
        System.out.println((double)collision/adds + "Gjennomsnittlig kollisjoner");
    }
}
