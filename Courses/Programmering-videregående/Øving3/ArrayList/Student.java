import java.util.ArrayList;
class Student{
    private final String navn;
    private int antOppg;
    
    public Student(String navn){
        this.navn = navn;
    }

    public String getNavn(){
        return navn;
    }

    public int getAntOppg(){
        return antOppg;
    }

    public void setAntOppg(int antOppg){
        if(antOppg<0){
           throw new IllegalArgumentException("Negativt tall");
        }
        this.antOppg += antOppg;
        
        // Try catch setning i kombinasjon med throw IllegalArgumentException?
    }

    public String toString(){
        return "Navn: " + navn + ", antall oppgaver løst: " + antOppg +"\n";
    }



}