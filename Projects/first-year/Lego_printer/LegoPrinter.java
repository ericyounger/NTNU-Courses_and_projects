/* LegoPrinter.java
 *  LegoPrinter er klientprogram til BokstavPrinter.java.
 *  Program som printer ut både tall og bokstaver.
 *  Printeren skriver ut rundt 7 bokstaver per linje, men tar automatisk linjeSkift
 *  hvis noen bokstaver er større eller mindre enn andre på linjen, slik at det i noen tilfeller
 *  er plass til mer eller mindre bokstaver per linje.
 *
 *  Programmet benytter seg av klientprogrammet LegoPrinter.java som tar og oppretter et objekt
 *  og sender en string som blir omgjort til LowerCase bokstaver som argument gjennom metoden finnBokstaver.
 *
 *  Utviklet gjennom gruppearbeidet til Team 09 ved NTNU Trondheim, av Hans Kristian, Tobias, Torje, Andreas, Eric.
 *
 *  2018.
 *
 */


public class LegoPrinter {
	public static void main(String[] args) throws Exception{
    	BokstavPrinter printer = new BokstavPrinter(); //lager objekt
		String hardkodetTekst = "grethe og bjørn er kjempekule"; // hardkodet tekst

      	printer.finnBokstaver(hardkodetTekst); //sender en String til printeren

	}
}
