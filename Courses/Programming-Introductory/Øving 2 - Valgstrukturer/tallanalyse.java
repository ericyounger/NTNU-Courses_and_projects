/** tallanalyse.java 
* Program som analyserer tall og ser om det er positivt, delelig med et gitt tall og om tallet er i et gitt lukket interval
*/

import static javax.swing.JOptionPane. *;

class tallanalyse {

	public static void main (String[]args){

		/* Intro beskjed */
		showMessageDialog (null, "Du blir spurt om først et tall så et annet i intervallet 1-100, så sjekker programmet om det er positivt, delelig og om det er innenfor intervallet");

		/* Leser inn første og andre tall */
		String heltallLest = showInputDialog (null, "Skriv inn første heltall: ");
		int heltall;
		heltall = Integer.parseInt (heltallLest);
		
		String heltall2Lest = showInputDialog (null, "Skriv inn andre heltall: ");
		int heltall2;
		heltall2 = Integer.parseInt (heltall2Lest);
		
		/* Sjekker om det er rest på divisjon */
		int divisjon;
		divisjon = heltall%heltall2;
		
		
		/*Sjekker om begge tallene er positive eller negative */
		if (heltall<0 ){
			
			showMessageDialog (null, "Tallet " +heltall + "er negativt");	
			
		} else  {
			
		showMessageDialog (null, "Tallet " + heltall + "er positivt");	
			
		}
		
		if (heltall2<0){
		showMessageDialog (null, " Tallet " + heltall2 + "er negativt ");
		
		}
		
		else {
		
		showMessageDialog (null, "Tallet " + heltall2 + "er positivt");	
		
		}
		
		/* Sjekker om første heltall er delelig på andre heltall */
		
		if (divisjon == 0) {
			showMessageDialog (null, "Tallet er delelig og gir ingen rest ");
			
		}
		
		else {
			
			showMessageDialog (null, "Tallet er ikke delelig og gir en rest på: " + divisjon); 
		}
		
	
	
		/* Sjekker om tallene er i det gitte intervallet */
		if (heltall>=1 && heltall<=100) {
			showMessageDialog (null, "Det første tallet er innenfor intervallet");
			
		}
		
		else  {
			showMessageDialog (null, "Det første tallet du oppga er ikke i intervallet");
			
		}
		
		if (heltall2>=1 && heltall2<=100) {
			showMessageDialog (null, "Det andre tallet er innenfor intervallet");
		}
	
		
		else {
			showMessageDialog (null, "Det andre tallet du oppga er ikke i intervallet");
		}


	}
}