/** primtall.java
* Et program for å finne primtall
*/
import static javax.swing.JOptionPane. *;
class primtall {
	public static void main (String[]args) {

		/* Gir beskjed om tallet er primtall eller ikke */
		String melding1 = " er et primtall";
		String melding2 = " er ikke et primtall";

		/* Leser inn tall */
		while(true) {
			String tallLest = showInputDialog(null, "Tast inn tall for å se om det er primtall:");
			int tall = Integer.parseInt(tallLest);
			/* Sjekker tall fra 1 til og med tallet som ble brukt */

			boolean ikkeDelelig = true;


			for (int i=2; i<tall; i++){
				if (tall%i == 0) { // Delelig
					ikkeDelelig = false; // sier at hvis dette kan deles så se vekk ifra denne
					break; // ut av for loopen
				}
			}

			// printe ut om det er et primtall
			if (ikkeDelelig && tall!= 1 && tall!=0) {
				showMessageDialog(null, tall + melding1);
			} else {
				showMessageDialog(null, tall + melding2);
			}

		} // while true
	} // main slutt
} //class slutt
