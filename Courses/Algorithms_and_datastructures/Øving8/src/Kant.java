public class Kant {
    Kant neste;
    Node til;
    Kant revers;

    public Kant(Node n, Kant nst){
        til = n;
        neste = nst;
    }

    public void setRevers(Kant kant){
        revers = kant;
    }
}


