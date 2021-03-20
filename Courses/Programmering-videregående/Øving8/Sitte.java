class Sitte extends Tribune{
    private int[] antOpptatt; //tabellstørrelse: antall rader

    public Sitte(String tribunenavn, int kapasitet, int pris){
        super(tribunenavn, kapasitet, pris);
        antOpptatt = new int[5];
    }
    public int getRadKapasitet(){ 
        return super.getKapasitet() / antOpptatt.length;
    }

    public int sjekkEtterLedigRad(int antBilletter){
        for(int i=0; i<antOpptatt.length; i++){
            if((getRadKapasitet()-antOpptatt[i])>=antBilletter){
                return i;
            }
        }
        return -1000;
    }
    
    
    public int finnAntallSolgteBilletter(){
        int sum=0;
        for(int i=0; i<antOpptatt.length; i++){
            sum += antOpptatt[i];
        }
        return sum;
    }

    public int getAntLedigBilletter(){
        return super.getKapasitet()-finnAntallSolgteBilletter();
    }

    public Billett[] kjøpBilletter(int antBilletter) {
        Billett[] tabell = new Billett[antBilletter];
        int rad = sjekkEtterLedigRad(antBilletter);
            if(rad != -1000 && rad>=0){
                for(int i=0; i<tabell.length; i++){
                    SitteplassBillett a = new SitteplassBillett(super.getTribuneNavn(), super.getPris(), rad, antOpptatt[rad]);
                    tabell[i] = a;
                    antOpptatt[rad]++;
                }
                return tabell;

            } else{
                return null;
            }
        
    }

    public Billett[] kjøpBilletter(String[] navn) {
        Billett[] tabell = new Billett[navn.length];
        return tabell;
    }

    public double finnInntekt(){
        return finnAntallSolgteBilletter()*super.getPris();
    }

    public String toString() {
        return getClass().getSimpleName() + " Tribunenr: " + getTribuneNavn() + ", Kapasitet: " + getKapasitet()
                + ", Antall Solgte billetter: " + finnAntallSolgteBilletter() + ", Inntekt: " + finnInntekt();
    }

}