public class Vkant extends Kant {
    int vekt;
    int flyt = 0;


    public Vkant(Node n, Vkant nst, int vkt){
        super(n,nst);
        vekt = vkt;
    }

    public int getRestKapasitet(){
        return vekt-flyt;
    }
}
