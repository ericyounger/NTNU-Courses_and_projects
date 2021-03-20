
/** tidtilsekund.java
* program som regner om fra inntastet tid til sekund.
 */

import static javax.swing.JOptionPane.*;

class tidtilsekund {

	public static void main(String[] args) {

		showMessageDialog(null, "Tast inn time, minutt og sekund for å konvertere det til sekunder");

		String timerlest = showInputDialog("Tast inn timer "); // timeinntasting
		double timer = Double.parseDouble(timerlest);
		double timekonverter = timer * 3600;

		String minuttlest = showInputDialog("Tast inn minutt "); // minuttinntasting
		double minutt = Double.parseDouble(minuttlest);
		double minuttkonverter = minutt * 60;

		String sekundlest = showInputDialog("Tast inn sekund "); // sekundinntasting
		double sekund = Double.parseDouble(sekundlest);
		double tidkonverter = timekonverter + minuttkonverter + sekund;

		showMessageDialog(null, timer + " time, " + minutt + " minutt, " + "og " + sekund + " sekund" +

				" blir til " + tidkonverter + " sekund");

	}
}
