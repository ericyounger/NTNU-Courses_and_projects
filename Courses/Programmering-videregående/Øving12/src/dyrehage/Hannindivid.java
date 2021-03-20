package dyrehage;

public class Hannindivid extends Individ implements SkandinaviskeRovdyr {

    public Hannindivid(String norskNavn, String latNavn, String latFamilie, int ankommetDato, String adresse, String navn, int fDato, boolean hanndyr, boolean farlig){
        super(norskNavn, latNavn, latFamilie, ankommetDato, adresse, navn, fDato, hanndyr, farlig);
    }

    public int getFDato(){
        return super.getFDato();
    }

    public int getAntKull(){
        return 0;
    }

    public void leggTilKull(int antall){

    }

    public void leggTilNyttKull(){

    }

}
