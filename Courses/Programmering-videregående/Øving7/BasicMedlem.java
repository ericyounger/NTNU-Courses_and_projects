import java.time.*;

public class BasicMedlem extends BonusMedlem{
    

    public BasicMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato){
        super(medlNr, pers, innmeldtDato);
    }

    public BasicMedlem(BasicMedlem a) {
        super(a.getMedlnr(), a.getPers(), a.getInnmeldtDato());
    }

    public boolean registrerPoeng(int poeng){
        return super.registrerPoeng(poeng);
    }

    public int finnKvalPoeng(LocalDate date1) {
        return super.finnKvalPoeng(date1);
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