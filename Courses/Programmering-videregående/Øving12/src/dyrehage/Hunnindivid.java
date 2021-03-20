package dyrehage;

public class Hunnindivid extends Individ {
    private  int antKull=0;

    public Hunnindivid(String norskNavn, String latNavn, String latFamilie, int ankommetDato, String adresse, String navn, int fDato, boolean hanndyr, boolean farlig, int antKull){
        super(norskNavn, latNavn, latFamilie, ankommetDato, adresse, navn, fDato, hanndyr, farlig);
        this.antKull = antKull;
    }

    public int getFDato(){
        return super.getFDato();
    }

    public int getAntKull(){
        return antKull;
    }

    public void leggTilKull(int antall){
        this.antKull += antall;
    }

    public void leggTilNyttKull(){
        antKull++;
    }

}
