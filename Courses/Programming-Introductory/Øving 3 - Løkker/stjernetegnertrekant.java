/** stjernetegnertrekant.java
* Program som tegner stjerner */

import static javax.swing.JOptionPane.*;
class stjernetegnertrekant {
  public static void main (String[]args) {

    char stjerne = '*';

    String linjeLest = showInputDialog (null, "Skriv inn antall linjer:");
    int linjeteller = Integer.parseInt (linjeLest);

    for (int linjer=0; linjer<=linjeteller; linjer++) {

        for (int mellomrom=40; mellomrom>linjer; mellomrom--){
            System.out.print(" ");


          }
            for (int antall= 1; antall<=linjer; antall++) { 
            System.out.print(" " + stjerne);
          } // for antall slutt



        System.out.println();
    } //linjer slutt





} // main slutt


} //class slutt
