import static javax.swing.JOptionPane.*;

class Brok{
  private int teller;
  private int nevner;


  public Brok (int teller, int nevner){
    if (nevner == 0){
      throw new IllegalArgumentException ("Nevner kan ikke være 0");
    }
    else {
      this.teller = teller;
      this.nevner = nevner;
    }
  }

  public Brok (int teller){
    this.teller = teller;
    this.nevner = 1;
  }
 public int getTeller(){
   return teller;
 }

 public int getNevner(){
   return nevner;
 }
  public void getBrokMulti(Brok brok2){
    this.teller = teller * brok2.getTeller();
    this.nevner = nevner * brok2.getNevner();
  }

  public void getBrokDivisjon(Brok brok2){
    this.teller = teller * brok2.getNevner();
    this.nevner = nevner * brok2.getTeller();
  }

  public void getBrokAddisjon(Brok brok2){ //samme om det heter brok2 eller eple, henter ikke verdi, gir bare nytt navn
    this.teller = teller * brok2.getNevner() + brok2.getTeller() * nevner;
    this.nevner = nevner * brok2.getNevner();
  }
  public void getBrokSubtraksjon(Brok brok2){
    this.teller = teller * brok2.getNevner() - brok2.getTeller() * nevner;
    this.nevner = nevner * brok2.getNevner();
  }

public String toString(){
  if(teller == nevner){
  return "Svaret er lik 1";
} else if (teller == 0){
  return "Svaret er lik 0";
} else {
  return teller + "/" + nevner;
}
}



}
