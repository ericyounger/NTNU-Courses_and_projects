import java.time.*;
public class SoelvMedlem extends BonusMedlem{

    public SoelvMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato, int poeng){
        super(medlNr, pers, innmeldtDato, poeng);
    }

    public boolean registrerPoeng(int poeng){
        double hjelp = poeng *1.2;
        int sum = (int) hjelp;
        //FEIL I OMGJØRING 
       return super.registrerPoeng(sum);
    }

    public int finnKvalPoeng(LocalDate date1) {
        return super.finnKvalPoeng(date1);
    }

    public int getPoeng(){
        return super.getPoeng();
    }

    public LocalDate getInnmeldtDato(){
        return super.getInnmeldtDato();
    }

    public Personalia getPers(){
        return super.getPersonalia();
    }

    public int getMedlnr(){
        return super.getMedlnr();
    }
    
}