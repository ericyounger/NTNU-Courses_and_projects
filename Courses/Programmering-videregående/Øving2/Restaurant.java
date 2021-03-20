import java.util.Calendar;
import static javax.swing.JOptionPane.*;

class Restaurant{
  private String restaurantNavn = "Eric's Restaurant";
  private int etablertAr = 1990;
  Bord bord;

  public Restaurant(String navn, int ar, int bord){
    this.bord = new Bord(bord);
    this.restaurantNavn = navn;
    this.etablertAr = ar;
  }

  public String getRestaurantNavn(){
    return restaurantNavn;
  }

  public void setRestaurantNavn(String navn){
    this.restaurantNavn = navn;
  }

  public int getEtableringsAr(){
    return etablertAr;
  }

  public int getRestaurantAlder(){
    //dagens dato - etableringsår.

    int year = Calendar.getInstance().get(Calendar.YEAR);
    int alder = year-etablertAr;
    return alder;
  }
  
  public int getAntallLedigBord(){
   int ledigBord = bord.getAntallLedigBord();
   return ledigBord;
  }
  
  public int getAntallOpptattBord(){
    int opptattBord = bord.getAntallOpptattBord();
    return opptattBord;
  }

  public void reservasjon(String navn, int antBord){
    String reservasjonsNavn = navn;
    int bordReservert = antBord;
    bord.reservasjon(reservasjonsNavn, bordReservert);
    showMessageDialog(null, "Antall opptatte bord: " + getAntallOpptattBord());
    
  }

  public String getBordReservert(String navn){
    int[] bordReservert = bord.equals(navn);
    String bordNr = "";
    System.out.println(bordReservert.length);
    for(int i=0; i<bordReservert.length; i++){
     
      bordNr += "Bordnr: " + bordReservert[i] + " "+ navn + "\n";
    }
    return bordNr;
  }

  public void frigiBord(int[] bordFrigitt){
   bord.frigiBord(bordFrigitt);
  }

  public String toString(){
    return bord.toString();
  }

}
