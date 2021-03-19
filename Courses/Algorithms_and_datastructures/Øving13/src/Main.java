import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String nodes = "src/kart/norden/noder.txt";
        String vertices = "src/kart/norden/kanter.txt";

        //String nodes = "src/kart/island/noder.txt";
        //String vertices = "src/kart/island/kanter.txt";

        FileReader fr = new FileReader(nodes);
        FileReader fr2 = new FileReader(vertices);
        BufferedReader nodeReader = new BufferedReader(fr);
        BufferedReader kantReader = new BufferedReader(fr2);
        //int oslo = 2419175;
        //int trondheim = 2460904;

        int fra = 2419175;
        int til = 2460904;

        Graph graph = new Graph();


        System.out.println("Loading edges and vertices and creating graph");
        graph.weightedGraph(nodeReader, kantReader);
        System.out.println("Finished loading");


        System.out.println("Finding shortest path");
        graph.shortestPath(fra,til, false);
        System.out.println("Finished search");

        graph.getPathInformation(til, fra);

    }
}
