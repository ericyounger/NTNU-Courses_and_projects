/** skuddaar.java 
* Program som ser om året er ett skuddår */

import static javax.swing.JOptionPane. *;

class skuddaar {
	public static void main (String[]args) {
		
		while(true) {
		String tallLest = showInputDialog ("Tast inn år for å se om det er skuddår");
		int tall;
		tall = Integer.parseInt (tallLest);
		
		
		int divisjon = tall%4;
		int divisjon2 = tall%400;
		int divisjon100 = tall%100;
		String tekst = " er et skuddår";
		String tekst2 = " er ikke et skuddår";
		
		
		/* Tall er delelig med 4 og ikke 100 */
		if ((divisjon == 0) && (divisjon100 !=0)) { 
			showMessageDialog (null, tall + tekst);
		
		
			/* Tall er delelig med 400 */
			} else if (divisjon2 == 0) {
				showMessageDialog (null, tall + tekst);
				
		
		/* Tall er ikke delelig med 4 eller 400 */	
		} else {
			showMessageDialog (null, tall + tekst2);
		}
	
		
		}
		
		
		
		
	}
	
	
	
	
}