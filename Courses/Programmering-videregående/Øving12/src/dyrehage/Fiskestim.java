package dyrehage;
public class Fiskestim extends Dyregruppe {
    private final double gjennomsnittligLengde;
    private final boolean kanDeleAkvarium;

    public Fiskestim(String norskNavn, String latNavn, String latFamilie, int ankommetDato, String adresse, String gruppenavn, int antIndivider, int gjennomsnittligLengde,boolean kanDeleAkvarium) {
        super(norskNavn, latNavn, latFamilie, ankommetDato, adresse, gruppenavn,antIndivider);
        this.gjennomsnittligLengde = gjennomsnittligLengde;
        this.kanDeleAkvarium = kanDeleAkvarium;
    }

    public double getGjennomsnittligLengde() {
        return gjennomsnittligLengde;
    }

    public boolean getKanDeleAkvarium() {
        return kanDeleAkvarium;
    }

    public String toString(){
        String kandeleMeld = "";
        if(kanDeleAkvarium){
            kandeleMeld = "Kan dele akvarium";
        } else{
            kandeleMeld = "Kan ikke dele akvarium";
        }
        return super.getNorskNavn()+ ", Gjennomsnittlig lengde: " + getGjennomsnittligLengde() + ", " + kandeleMeld;
    }
}
