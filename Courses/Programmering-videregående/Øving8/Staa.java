class Staa extends Tribune{
    private int antSolgteBilletter;

    public Staa(String navn, int kapasitet, int pris){
        super(navn, kapasitet, pris);
    }

    public int finnAntallSolgteBilletter() {
        return antSolgteBilletter;
    }
    
    

    public double finnInntekt() {
        return finnAntallSolgteBilletter()*super.getPris();
    }

    public boolean getDiff(int antBilletter){
        if(antBilletter<=(super.getKapasitet()-finnAntallSolgteBilletter())){
            return true;
    }
    return false;
}

    public Billett[] kjøpBilletter(int antBilletter) {
        Billett[] tabell = new Billett[antBilletter];
        if(antBilletter<=(super.getKapasitet()-finnAntallSolgteBilletter())){
                for(int i=0; i<antBilletter; i++){
                    StaaplassBillett a = new StaaplassBillett(getClass().getSimpleName(), getPris());
                    tabell[i] = a;
                }
                setAntSolgteBilleter(antBilletter);
        return tabell;
        } else{
            return null;
        }
    }

    public Billett[] kjøpBilletter(String[] navn) {
        Billett[] tabell = new Billett[10];
        for(int i=0; i<navn.length; i++){
            StaaplassBillett a = new StaaplassBillett(getClass().getSimpleName(), getPris());
            tabell[i] = a;
        }
        return tabell;
    }

    public void setAntSolgteBilleter(int billetter){
        this.antSolgteBilletter += billetter;
    }

    public String toString(){
        return getClass().getSimpleName() + " Tribunenr: "+ getTribuneNavn() + ", Kapasitet: " + getKapasitet() +", Antall Solgte billetter: " + finnAntallSolgteBilletter() + ", Inntekt: " + finnInntekt();
    }
    
}