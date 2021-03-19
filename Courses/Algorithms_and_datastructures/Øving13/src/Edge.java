public class Edge {
    private Node from;
    private Node to;
    int driveTime;
    int length;
    int speedLimit;

    public Edge(Node from, Node to, int driveTime, int length, int speedLimit){
        this.from = from;
        this.to = to;
        this.driveTime = driveTime;
        this.length = length;
        this.speedLimit = speedLimit;

    }


    public Node getNodeTo(){
        return to;
    }

    public Node getNodeFrom(){
        return from;
    }

    public void setNodeTo(Node n){
        this.to = n;
    }

    public void setNodeFrom(Node n){
        this.from = n;
    }

    public int getLength(){
        return length;
    }

    public int getDriveTime(){
        return driveTime;
    }

    public int getSpeedLimit(){
        return speedLimit;
    }

    @Override
    public String toString(){
        return "Drive time:" + this.driveTime + " Length: " + this.length + " Speedlimit: " + this.speedLimit;
    }
}


