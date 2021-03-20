
import static javax.swing.JOptionPane.*;
import java.util.Scanner;
import java.io.InputStream;
class valutaprogram{
  public static double belop;
  public static void main(String[]args){



    /* Oppretter objektene/valuta typene */
    valuta dollar = new valuta("1: Dollar \n", 0.12);
    valuta euro = new valuta("2: Euro \n", 0.10);
    valuta svenskeKr = new valuta("3: Svenske Kroner \n", 1.09);


    /* Skriver inn beløp */
    String belopLest = showInputDialog(null, "Tast inn beløpet du vil omgjør: ");
    belop = Double.parseDouble (belopLest);


    /* Får opp fra meny */
    String tilfraLest = showInputDialog (null, "Velg til/fra norske kroner: \n" + "1: Fra NOK\n" + "2: Til NOK");
    int tilfra = Integer.parseInt (tilfraLest);

    String tilValutaLest = showInputDialog (null, "Velg hvilken valuta: \n" + dollar.getType() + euro.getType() + svenskeKr.getType() + "4: Avslutt");
    int tilValuta = Integer.parseInt (tilValutaLest);


      if (tilfra == 1){

      if (tilValuta == 1){
        showMessageDialog(null, belop + " NOK blir til " + dollar.getNoktilvaluta() + " USD");
      } else if (tilValuta == 2){
        showMessageDialog(null, belop + " NOK blir til " + euro.getNoktilvaluta() + " Euro");
      } else if (tilValuta == 3){
        showMessageDialog(null, belop + " NOK blir til " + svenskeKr.getNoktilvaluta() + " Svenske kr");
      }
    } else if (tilfra == 2){
       if (tilValuta == 1){
        showMessageDialog(null, belop + " USD blir til " + dollar.getNokfravaluta() + " NOK");
      } else if (tilValuta == 2){
        showMessageDialog(null, belop + " Euro blir til " + euro.getNokfravaluta() + " NOK");
      } else if (tilValuta == 3){
        showMessageDialog(null, belop + " Svenske kr blir til " + svenskeKr.getNokfravaluta() + " NOK");
      }
    }

  }
}
