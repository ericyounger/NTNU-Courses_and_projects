import eu.jacquet80.minigeo.MapWindow;
import eu.jacquet80.minigeo.POI;
import eu.jacquet80.minigeo.Point;
import eu.jacquet80.minigeo.Segment;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


public class Graph {
    int N, E; // N - no of nodes, E - no of edges
    Node[] nodes;
    Edge[] edges;


    public void weightedGraph(BufferedReader nodeReader, BufferedReader edgeReader)throws IOException{

        // Load nodes
        StringTokenizer st=new StringTokenizer(nodeReader.readLine());
        N=Integer.parseInt(st.nextToken());
        nodes =new Node[N];

        for (int i = 0; i <N ; i++) {
            nodes[i]=new Node();
        }

        for (int i = 0; i <N ; i++) {
            st=new StringTokenizer(nodeReader.readLine());
            int nodeNR=Integer.parseInt(st.nextToken());
            double latitude=Double.parseDouble(st.nextToken());
            double longitude=Double.parseDouble(st.nextToken());

            nodes[i].setVariables(longitude,latitude,nodeNR);
        }

        // Load edges
        StringTokenizer vt = new StringTokenizer(edgeReader.readLine());

        E = Integer.parseInt(vt.nextToken());
        edges = new Edge[E];


        for(int i=0; i<E; i++){
            vt = new StringTokenizer(edgeReader.readLine());
            int fromNode = Integer.parseInt(vt.nextToken());
            int toNode = Integer.parseInt(vt.nextToken());
            int driveTime = Integer.parseInt(vt.nextToken());
            int length = Integer.parseInt(vt.nextToken());
            int speedlimit = Integer.parseInt(vt.nextToken());

            //System.out.println(fromNode + " "  + toNode + " " + driveTime + " " + length + " " + speedlimit);

            Edge e = new Edge(nodes[fromNode], nodes[toNode],driveTime,length,speedlimit);
            edges[i] = e;

            nodes[fromNode].addEdge(e);
        }

        nodeReader.close();
        edgeReader.close();
    }

    public void shortestPath(int start, int slutt, boolean enableAStar){
        Date startTime = new Date();
        Date stopTime;
        PriorityQueue<Node> q = new PriorityQueue<>();
        Node startNode = nodes[start];
        Node stopNode = nodes[slutt];
        boolean aStar = enableAStar;


        startNode.setCost(0);
        q.add(startNode);
        Node current = null;

        while(!q.isEmpty()){
            current = q.poll();
            current.setExpanded(true);

            if(current.equals(stopNode)){
                //System.out.println("Found stopNode");
                break;
            }

            //Iterate over edges from current node
            for(int i=0; i<current.getEdge().size(); i++){
                Edge edge = current.getEdge().get(i);
                Node toNode = edge.getNodeTo();

                //Set cost from startnode if distance is infinity in beginning. And also changes based on other routes.
                if(toNode.getCost()> current.getCost() + edge.getDriveTime()){
                    toNode.setCost(current.getCost() + edge.getDriveTime());
                    toNode.setPreviousNode(current);
                } else{
                    continue;
                }

                //if Djikstra
                if(!aStar){
                    toNode.setPriority(toNode.getCost());
                }

                //if A*
                else{
                    if(toNode.isDistanceCalculated()){
                        toNode.setPriority((int) toNode.getDirectDistance()/130*3600 + toNode.getCost());
                    } else{
                        toNode.setDirectDistance(stopNode);
                        toNode.setPriority((int) toNode.getDirectDistance() /130 * 3600 + toNode.getCost());
                    }
                }

                //Adds and removes from priority queue based on if they are expanded already or discovered
                if(!toNode.getExpanded()){ //Node has not been expanded
                    if(toNode.getDiscovered()){
                        q.remove(toNode); // Has another priority in other iteration
                    }

                        toNode.setDiscovered(true);
                        q.add(toNode);

                }

            }
        }
        stopTime = new Date();
        System.out.println("Algorithm took " + (double) (stopTime.getTime()-startTime.getTime()) + "ms");
    }

    public void getPathInformation(int stop, int start){
        ArrayList<Node> path = new ArrayList<>();
        MapWindow window = new MapWindow();
        ArrayList<Point> points = new ArrayList<>();

        Node stopNode = nodes[stop];
        Node startNode = nodes[start];
        int travelDistance = 0; // In meters

        Node current = stopNode;
        path.add(stopNode);
        points.add(new Point(current.getLatitude(), current.getLongitude()));


        Point n;
        Node prevNode;

        while(!current.equals(startNode)){
            prevNode = current.getPreviousNode();

            for(int i=0; i<prevNode.getEdge().size(); i++){
                if(prevNode.getEdge().get(i).getNodeTo() == current){
                    travelDistance += prevNode.getEdge().get(i).length;
                }
            }


            current = current.getPreviousNode();
            path.add(current);
            n = new Point(current.getLatitude(), current.getLongitude());
            points.add(n);


        }

        int totalTime = stopNode.getCost()/100; // 1/100 seconds
        int hours = totalTime/3600;
        int minutes = (totalTime%3600) / 60;
        int seconds = totalTime%60;



        window.addPOI(new POI(new Point(startNode.getLatitude(), startNode.getLongitude()), startNode.nodeNrAsString()));

        for(int i=0; i<points.size()-1; i++){
            window.addSegment(new Segment(points.get(i), points.get(i+1) ,Color.RED));
        }

        window.addPOI(new POI(new Point(stopNode.getLatitude(), stopNode.getLongitude()), stopNode.nodeNrAsString()));

        System.out.println("\nPath Information:\n");
        System.out.println("Time: " + hours +":" + minutes+":"+seconds);
        System.out.println("Nodes in path " + path.size());
        System.out.println("Distance between nodes: " + travelDistance /1000 + " km");


        window.setVisible(true);


    }
}

