
import javax.swing.JOptionPane.*;
class Student{
  private String navn;
  private int antOppg;

  public Student(String navn, int antOppg){
  this.navn = navn;
  this.antOppg = antOppg;
  }

  public String getNavn(){
    return navn;
  }

  public int getAntOppg(){
    return antOppg;
  }

  public void okAntOppg(int økning){
    this.antOppg += økning;
  }

  public String toString(){
    return navn + "\n" + "Antall oppgaver gjort: " +getAntOppg();
  }



}
