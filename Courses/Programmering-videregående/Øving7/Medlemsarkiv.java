import java.util.*;
import java.time.LocalDate;
import java.util.ArrayList;



 
class Medlemsarkiv{
    private ArrayList<BonusMedlem> medlemmer = new ArrayList<BonusMedlem>();
    private LocalDate dagensDato = LocalDate.of(2008, 2, 10);
    public Medlemsarkiv(){

    }

    public int finnPoeng(int medlemsNr, String passord){
        //sjekk om medlemsnr og passord stemmer, returner antall poeng
        for(int i=0; i<medlemmer.size(); i++) {
            if((medlemsNr == medlemmer.get(i).getMedlnr()) && medlemmer.get(i).okPassord(passord)){
                return medlemmer.get(i).getPoeng();
            }
        }
        //ellers -1
        return -1;
    }

    public boolean registrerPoeng(int medlemsNr, int poeng){
        for(int i=0; i<medlemmer.size(); i++){
            if(medlemsNr == medlemmer.get(i).getMedlnr()){
               return medlemmer.get(i).registrerPoeng(poeng);
            }
        }
        return false;
    }

    private int finnLedigNr(){
        Random rndTall = new Random();
        int tall = rndTall.nextInt(100);

        for(int i=0; i<medlemmer.size(); i++){
            while(medlemmer.get(i).getMedlnr() == tall){
                tall = rndTall.nextInt(100);
            }
            return tall;
        }
        return 0;
    }

    public int nyMedlem(Personalia pers, LocalDate innmeldt){
       int medlNr =  finnLedigNr();
        BasicMedlem a = new BasicMedlem(medlNr, pers, innmeldt);
        medlemmer.add(a);
        return medlNr; 
    }

    public String sjekkMedlemmer(){
        String nr= "Medlemmer som ble oppgradert:\n";
        for(int i=0; i<medlemmer.size(); i++){
            if (medlemmer.get(i) instanceof BasicMedlem) {
               if(medlemmer.get(i).finnKvalPoeng(dagensDato)>=25000 && medlemmer.get(i).finnKvalPoeng(dagensDato)<75000){
                   //OPPGRADER TIL SØLV.
                   SoelvMedlem soelv = new SoelvMedlem(medlemmer.get(i).getMedlnr(), medlemmer.get(i).getPers(), medlemmer.get(i).getInnmeldtDato(), medlemmer.get(i).getPoeng());
                    medlemmer.set(i, soelv);
                    nr += medlemmer.get(i).getMedlnr() +"\n";
                   // MÅ ENDRE PÅ FINNKVALPOENG SIDEN DET IKKE ER DATE1 I PARAMETER. .GETINNMELDINGDAT()???
               } else if(medlemmer.get(i).finnKvalPoeng(dagensDato)>= 75000){
                   GullMedlem gull = new GullMedlem(medlemmer.get(i).getMedlnr(), medlemmer.get(i).getPers(), medlemmer.get(i).getInnmeldtDato(), medlemmer.get(i).getPoeng());
                    medlemmer.set(i, gull);
                    nr += medlemmer.get(i).getMedlnr() + "\n";
                   //OPPGRADER TIL GULL.
               }
                System.out.println("BasicMedlem");
            } else if (medlemmer.get(i) instanceof SoelvMedlem) {
                if(medlemmer.get(i).finnKvalPoeng(dagensDato)>= 75000){
                    GullMedlem gull = new GullMedlem(medlemmer.get(i).getMedlnr(), medlemmer.get(i).getPers(), medlemmer.get(i).getInnmeldtDato(), medlemmer.get(i).getPoeng());
                    medlemmer.set(i, gull);
                    nr += medlemmer.get(i).getMedlnr() + "\n";
                    //OPPGRADER TIL GULL.
                }
                System.out.println("SoelvMedlem");
            } else if (medlemmer.get(i) instanceof GullMedlem) {
                System.out.println("GullMedlem");
                //ER ALLEREDE GULLMEDLEM, TRENGER IKKE GJØRE NOE HER.
            }

        }
        return nr;
        
    }

    public String toString(){
        String res = "Medlnr registrert\n";
        for(int i=0; i<medlemmer.size(); i++){
            res += "Medlemnr: " +medlemmer.get(i).getMedlnr() + " " + medlemmer.get(i).getClass().toString() +  "\n";
        }
        return res;
    }

    public static void main(String[]args){
        
    }

}