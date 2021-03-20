package dyrehage;

import java.time.LocalDate;

public abstract class Individ extends Dyr implements SkandinaviskeRovdyr {
    private final String navn;
    private final int fDato;
    private final boolean hanndyr;
    private final boolean farlig;

    public Individ(String norskNavn, String latNavn, String latFamilie, int ankommetDato, String adresse, String navn, int fDato, boolean hanndyr, boolean farlig) {
        super(norskNavn, latNavn, latFamilie, ankommetDato, adresse);
        this.navn = navn;
        this.fDato = fDato;
        this.hanndyr = hanndyr;
        this.farlig = farlig;
    }

    public String getNavn() {
        return navn;
    }

    public int getFDato() {
        return fDato;
    }

    public boolean getHanndyr() {
        return hanndyr;
    }

    public boolean getFarlig() {
        return farlig;
    }

    public String skrivUtInfo(){
        return toString();
    }

    public String getAdresse(){
        return super.getAdresse();
    }

    public void flytt(String adresse){
        super.setAdresse(adresse);
    }

    public int getAlder(){
        return 0;
    }

    public String toString(){
        String hanndyrMelding = "";
        if(hanndyr){
            hanndyrMelding = "Hanndyr";
        } else {
            hanndyrMelding = "Hunndyr";
        }

        String farligMelding = "";
        if(farlig){
            farligMelding = "Farlig";
        } else{
            farligMelding = "Ufarlig";
        }

        return getNavn()+ ", " + getFDato() + ", " + hanndyrMelding + ", " + farligMelding;
    }
}
