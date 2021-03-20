
class VIP extends Sitte{
    private String[][] tilskuer; // tabellstørrelse: antall rader * antall plasser pr rad.

    public VIP(String tribunenavn, int kapasitet, int pris, int antRader){
        super(tribunenavn, kapasitet, pris);
        this.tilskuer = new String[antRader][getKapasitet()/antRader]; // må settte inn noen verdier her.
    }
    public int finnAntallSolgteBilletter(){
        return super.finnAntallSolgteBilletter();
    }

  
    public double finnInntekt(){
        return super.finnInntekt();
    }

    public int finnPlassIRad(int rad){
        int teller =0;
        for(int i=0; i<tilskuer[rad].length; i++){
            if(tilskuer[rad][i] != null){
                teller++;
            }
        }
        return teller;
    }

    


    public Billett[] kjøpBilletter(int antBilletter) {
        return null;
    }

    public Billett[] kjøpBilletter(String[] navn) {
        Billett[] y;
        int rad = super.sjekkEtterLedigRad(navn.length); // SJEKKER ETTER LEDIG RAD
        int plass = finnPlassIRad(rad);

        // SJEKKER OM ALLE NAVN ER FYLLT INN ///
        for (int j = 0; j < navn.length; j++) {
            if ((navn[j] == null) || (navn[j].trim().equals(""))) {
                return null;
            }
        }

        if(rad != -1000){ //HVIS LEDIG PLASS KJØP BILLETT
            int sete = finnPlassIRad(rad);
           y=  super.kjøpBilletter(navn.length);
           int a = 0;
           System.out.println(navn.length);
            for (int i = sete; i <sete + navn.length; i++) {
                // SitteplassBillett x = new SitteplassBillett(super.getTribuneNavn(), super.getPris(), rad, sete); // I må byttes ut.
                // y[a] = x; // LEGGER BILLETT TIL I RETURN ARRAY
                tilskuer[rad][sete] = navn[a];  //OPPDATERER TILSKUER ARRAY MED NAVN TIL BESTILLER
                a++;
            }
            return y;
        } else{
            return null;
        }
       
    }

    public String toString() {
        return getClass().getSimpleName() + " Tribunenr: " + getTribuneNavn() + ", Kapasitet: " + getKapasitet()
                + ", Antall Solgte billetter: " + finnAntallSolgteBilletter() + ", Inntekt: " + finnInntekt();
    }
}