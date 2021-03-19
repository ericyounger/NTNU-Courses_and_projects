package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filadresse = "/Users/ericyounger/Dropbox/NTNU/Algoritmer og datastruktur/Øvinger/Øving7/src/com/company/txt/L7g5.txt" ;
        FileReader fr = new FileReader(filadresse);
        BufferedReader reader = new BufferedReader(fr);
        Graf graf = new Graf();
        graf.ny_ugraf(reader);


        int startNode= 5;

        //Oppgave1//
        breddeFørstSøk(graf,startNode);

        //Oppgave2//
        topoSort(graf,startNode);





    }

    public static void breddeFørstSøk(Graf graf, int startnode){
        System.out.println("Bredde først søk");
        graf.bfs(graf.node[startnode]);
        graf.getInfo(startnode);
    }

    public static void topoSort(Graf graf, int startnode){
        System.out.println("\nTopological sort");
        graf.dfs(graf.node[startnode]);
        Node start = graf.topologisort();
        graf.topologiPrint(start);

    }

}
