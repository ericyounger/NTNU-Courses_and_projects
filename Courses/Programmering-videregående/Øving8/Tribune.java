   import java.util.Comparator.*;

   abstract class Tribune implements java.io.Serializable{
    private final String tribunenavn;
    private final int kapasitet;
    private final int pris;    

    public Tribune(String tribunenavn, int kapasitet, int pris){
        this.tribunenavn = tribunenavn;
        this.kapasitet = kapasitet;
        this.pris = pris;
    }

    public String getTribuneNavn(){
        return tribunenavn;
    }

    
    public int getKapasitet(){
        return kapasitet;
    }

    public int getPris(){
        return pris;
    }

    public abstract Billett[] kjøpBilletter(int antall);

    public abstract Billett[] kjøpBilletter(String[] navn);

    

    public int finnAntallSolgteBilletter(){
        return 0;
    }

    public double finnInntekt(){
        return 0.0;
    }

    public String toString() {
        String tmp = "";
        return tmp;
    }

}