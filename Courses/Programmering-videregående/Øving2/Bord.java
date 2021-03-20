import static javax.swing.JOptionPane.*;
class Bord{
  private String[] bord;

  public Bord(int bord){
  this.bord = new String[bord];
  }

  public int getAntallLedigBord(){
    int ledigBord = 0;
    for(int i=0; i<bord.length; i++){
      if(bord[i] == null){
        ledigBord++;
      }
    }
    return ledigBord;
  }

  public int getAntallOpptattBord(){
    int bordReservert = 0;
    for(int i=0; i<bord.length; i++){
      if(bord[i] != null){
        bordReservert++;
      }
    }
    return bordReservert;
  }

  public void reservasjon(String navn, int antBord){
    if(getAntallLedigBord()>= antBord){
      for(int j=0; j<antBord; j++){
        for (int i = 0; i <bord.length; i++) {
          if (bord[i] == null) {
            bord[i] = navn;
            break;
            }

         } 
             
        }
      
          //logikkfeil, må skrive reservasjon metode på nytt.
      
        showMessageDialog(null, "Bord reservert");
    } else{
        showMessageDialog(null, "Ikke nok ledig bord");

    }
  }

 
  public void frigiBord(int[] indeks){
  for(int i=0; i<indeks.length; i++){
    bord[indeks[i]] = null;
  }
  }

  public int[] equals(String navn){

    //sjekker hvor mange bord er reservert på personen
    int teller=0;
      for (int i = 0; i < bord.length; i++) {
        if (navn.equalsIgnoreCase(bord[i])) {
          teller++;
        }
      }
      //oppretter ny tabell med plass til bord som er reservert
      int[] bordNr = new int[teller];
     
      //legger til bord i tabell
      teller = 0;
      for(int j=0; j<bord.length; j++){
        if (navn.equalsIgnoreCase(bord[j])) {
          bordNr[teller] = j;
        teller++;
      }
      }
      
    return bordNr;
  }

  public String toString(){
    String res ="";
    for(int i=0; i<bord.length; i++){
      res += "bordnr: " + i + ": " + bord[i] +"\n";
    }
    return res;
  }

}
    
  
  

