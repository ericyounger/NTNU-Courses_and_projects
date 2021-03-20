import java.time.*;


class BonusMedlem{
    private final int medlNr;
    private final Personalia pers;
    private final LocalDate innmeldtDato;
    private int poeng = 0;
    
    public BonusMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato){
        this.medlNr = medlNr;
        this.pers = pers;
        this.innmeldtDato = innmeldtDato;
    }

    public BonusMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato, int poeng) {
        this.medlNr = medlNr;
        this.pers = pers;
        this.innmeldtDato = innmeldtDato;
        this.poeng = poeng;
    }

    public Personalia getPers(){
        return pers;
    }

    public int getMedlnr(){
        return medlNr;
    }

    public Personalia getPersonalia(){
        return pers;
    }

    public LocalDate getInnmeldtDato(){
        return innmeldtDato;
    }

    public int getPoeng(){
        return poeng;
    }

    //NOE FEIL I DATO OG DAGER MELLOM
    public int finnKvalPoeng(LocalDate date1){
        int arMellom = Period.between(getInnmeldtDato(), date1).getYears();
    
        if(arMellom < 1){
            return getPoeng();
        }
        return 0;
    }

    public boolean okPassord(String passord){
        return pers.okPassord(passord);
    }

    public boolean registrerPoeng(int nyePoeng){
        this.poeng += nyePoeng;
        return true;
    }

}