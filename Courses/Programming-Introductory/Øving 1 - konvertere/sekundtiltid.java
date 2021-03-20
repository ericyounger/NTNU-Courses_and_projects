
/** sekundtiltid.java
* program som regner om fra inntastet sekund til timer, minutt og sekund.
 */

import static javax.swing.JOptionPane.*;

class sekundtiltid {

	public static void main(String[] args) {

		showMessageDialog(null, "Tast inn antall sekunder for å se hvor mange timer, minutt og sekund det er");

		String sekundlest = showInputDialog("Tast inn sekund "); // sekund inntasting
		int sekund = Integer.parseInt(sekundlest);

		int time = sekund / 3600;
		int timerest = sekund % 3600;

		int minutt = timerest / 60;
		int minuttrest = timerest % 60;

		int sekundsum = minuttrest;

		showMessageDialog(null,
				sekund + " sekund blir til " + time + " time, " + minutt + " minutt og " + sekundsum + " sekunder");

	}
}