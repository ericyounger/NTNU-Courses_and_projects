import static javax.swing.JOptionPane.*;
class Tekstklient{
  public static void main(String[]args){
    /* Skriver inn teksten */
    String inn = showInputDialog(null, "Skriv inn tekst som skal bli analysert: ");
    Tekstanalyse tekst = new Tekstanalyse(inn); // Mater inn tekst og tabell til objekt

    /* Skriver inn bokstaven man vil ha informasjon om */
    while(true){
      int bokstavDiff=0;
      String bokstavLest = showInputDialog(null, "Skriv inn bokstaven du vil få informasjon om: ");
      int bokstav = bokstavLest.toLowerCase().charAt(0); // gjør om til lowercase og gir en verdi
      bokstavDiff = bokstav-97;  // nullstiller verdi i forhold til tabell indeks.

      tekst.getAntallforespurt(bokstavDiff); // Sender bokstav inn til objekt.

      /* Skriver ut toString med informasjon */
      System.out.print(tekst);
      System.out.println("");
}

} // public main end

} //class end
