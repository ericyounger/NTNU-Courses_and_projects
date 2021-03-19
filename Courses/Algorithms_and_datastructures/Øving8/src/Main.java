import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException {
        String filadresse = "/Users/ericyounger/Dropbox/NTNU/Algoritmer og datastruktur/Øvinger/Øving8/src/txt/flytgraf1.txt" ;
        FileReader fr = new FileReader(filadresse);
        BufferedReader reader = new BufferedReader(fr);
        Graf graf = new Graf();


        graf.nyVektetGraf(reader);

        int startnode = 0;
        int sluknode = 7;
        int maxFlyt = 0;


        System.out.println("Maksimal flyt fra 0 til " + (graf.node.length-1) + " Med Edmonds-Karp");
        System.out.println("Økning:  Flytøkende vei");

        boolean morePaths = true;

        while(true){

            graf.bfs(graf.node[startnode]);
            ArrayList<Integer> vei = graf.finnVeiTilbake(startnode,sluknode);

            if(vei.contains(startnode) && vei.contains(sluknode)){
                int flyt = graf.oppdaterKant(vei);
                maxFlyt +=flyt;

                Collections.reverse(vei);
                System.out.println("       " + vei);
            } else{
                break;
            }

        }

        System.out.println("Maksimal flyt ble " + maxFlyt);












    }





}