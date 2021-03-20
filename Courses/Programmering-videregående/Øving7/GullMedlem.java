import java.time.*;
public class GullMedlem extends BonusMedlem{

    public GullMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato, int poeng){
        super(medlNr, pers, innmeldtDato, poeng);
    }

    public int finnKvalPoeng(LocalDate date1) {
        return super.finnKvalPoeng(date1);
    }

    public boolean registrerPoeng(int poeng){
        double hjelp = poeng * 1.5;
        int sum = (int) hjelp;
        return super.registrerPoeng(sum);
    }

    public Personalia getPers() {
        return super.getPersonalia();
    }
    
    public int getPoeng() {
        return super.getPoeng();
    }

    public LocalDate getInnmeldtDato() {
        return super.getInnmeldtDato();
    }

    public int getMedlnr() {
        return super.getMedlnr();
    }
}