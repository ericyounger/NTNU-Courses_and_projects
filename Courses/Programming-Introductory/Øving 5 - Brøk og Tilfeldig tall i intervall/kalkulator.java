
import static javax.swing.JOptionPane.*;

class kalkulator{
  public static void main(String[]args){



    while(true){
    Brok a1 = new Brok (5, 10);


      /* Får matet inn brøk */
      String tellerLest = showInputDialog("Skriv inn verdi for teller: ");
      int tellerInput = Integer.parseInt(tellerLest);;
      String nevnerLest = showInputDialog("Skriv inn verdi for nevner: ");
      int nevnerInput = Integer.parseInt(nevnerLest);
      Brok brok2 = new Brok (tellerInput, nevnerInput);

      /* Får valgmeny opp */
      int valg;
      String valgLest = showInputDialog(null, "Tast for operasjon:\n 1: Multiplikasjon\n 2: Divisjon\n 3: Addisjon\n 4: Subtraksjon:");
      valg = Integer.parseInt(valgLest);

      /* Kjører handling avhengig av valg */
      switch(valg) {
        case 1:
        a1.getBrokMulti(brok2); //bruker brøk 2 til å utføre operasjonen med
        break;
        case 2:
        a1.getBrokDivisjon(brok2); //bruker brøk 2 til å utføre operasjonen med
        break;
        case 3:
        a1.getBrokAddisjon(brok2); //bruker brøk 2 til å utføre operasjonen med
        break;
        case 4:
        a1.getBrokSubtraksjon(brok2); //bruker brøk 2 til å utføre operasjonen med
        break;
        default :
        showMessageDialog(null, "Ugyldig verdi. Velg 1-4");
        continue;
      }

      showMessageDialog(null, a1);

  } // end while



  } // end main
} // end class
