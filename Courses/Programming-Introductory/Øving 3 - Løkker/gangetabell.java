/** gangetabell.java
* Program som skriver ut gangetabell fra 13 til 15 */

import static javax.swing.JOptionPane. *;
class gangetabell {
  public static void main (String[]args){

  String startLest = showInputDialog (null, "Fra: ");
  int start = Integer.parseInt (startLest);

  String sluttLest = showInputDialog (null, "Til: ");
  int slutt = Integer.parseInt (sluttLest);

  int nummer;

    while (start <= slutt) {

        System.out.println ("\n" + start + "-gangen:");
        for (nummer =1; nummer<=10; nummer++) {
        int sum = start*nummer;
        System.out.println (start + " x " + nummer + " = " + sum);

        } // for slutt
    start++;
    } // while slutt



  } // main slutt
} // Class slutt
