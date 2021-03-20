import static javax.swing.JOptionPane.*;
class Klient{
  public static void main(String[] args){
    String[] muligheter = {"Reserver bord", "Finn bordnr", "Frigi bord", "Endre restaurantnavn","Bord Info", "Avslutt"};
    String restaurantNavn = showInputDialog(null, "Hva heter restauranten?");
    String etableringArTekst = showInputDialog(null, "Når ble restauranten etablert?");
    int etableringAr = Integer.parseInt(etableringArTekst);
    String antBordTekst = showInputDialog(null, "Antall bord i restauranten?");
    int antBord = Integer.parseInt(antBordTekst);
    Restaurant a = new Restaurant(restaurantNavn, etableringAr, antBord);
    int valg;

    final int reserverBord = 0;
    final int finnBordnr = 1;
    final  int frigiBord = 2;
    final int bordInfo = 4;
    final int avslutt= 5;
    final int endreNavn = 3;

do {
  valg = showOptionDialog(null, a.getRestaurantNavn()+ " * " +a.getEtableringsAr() + " * " + a.getRestaurantAlder() + " * " + a.getAntallLedigBord() + " ledig bord * " + a.getAntallOpptattBord() + " opptatte bord" , "Valg", YES_OPTION, INFORMATION_MESSAGE, null, muligheter, muligheter[0]);

    switch (valg) {
        case reserverBord:  // reservere et antall bord på et bestemt navn
          String reservasjon = showInputDialog(null, "Hvem skal reservasjonen stå på?");
          String bordReservasjonTekst = showInputDialog(null, "Hvor mange bord skal det reserveres?");
          int bordReservert = Integer.parseInt(bordReservasjonTekst);
          a.reservasjon(reservasjon, bordReservert);
          break;
        case finnBordnr:  // finne alle bordene som er reservert på et bestemt navn
          String forespurtNavn = showInputDialog(null, "Hvem står reservasjonen på?");
          showMessageDialog(null, a.getBordReservert(forespurtNavn)); 
          break;
        case frigiBord:
        String antallTekst = showInputDialog(null, "Hvor mange bord skal du frigjør?");
        int antall = Integer.parseInt(antallTekst);
        int[] bordFrigitt = new int[antall];
        for(int i=0; i<antall; i++){
          String inputTekst = showInputDialog(null, "Hvilket bord skal frigjøres?");
          int input = Integer.parseInt(inputTekst);
          bordFrigitt[i] = input;
        }
         a.frigiBord(bordFrigitt);
            
        // frigi en rekke bord, bordnummer er gitt
            // ....les inn aktuelle bordnummer og kall metode...
            break;

        case endreNavn:
          String nyttRestaurantNavn = showInputDialog(null, "Hva skal den nye restauranten hete?");
          a.setRestaurantNavn(nyttRestaurantNavn);
          break;
      
        case bordInfo:
          showMessageDialog(null, a.toString());
          break;
        case avslutt:
            // ....
         break;
        default:
         break;
    }
  } while (valg != avslutt);

  }
}
