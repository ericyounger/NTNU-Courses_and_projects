
/** ligningkontor.java
* Program som bestemmer hvilket kontor som kan hjelpe deg avhengig av hvilken dag i måneden du er født på
*/

import static javax.swing.JOptionPane.*;

class ligningkontor {
	public static void main(String[] args) {

		String datoLest = showInputDialog("Tast inn Fødselsdag");
		int dato = Integer.parseInt(datoLest);

		/* Mellom 1-8 */
		if (dato >= 1 && dato <= 8) {
			showMessageDialog(null, "Du kan henvende deg til kontor 113");
		}
		/* Mellom 9-14 */
		else if (dato >= 9 && dato <= 14) {
			showMessageDialog(null, "Du kan henvende deg til kontor 120");
		}

		/* Mellom 15-25 */
		else if (dato >= 15 && dato <= 25) {
			showMessageDialog(null, "Du kan henvende deg til kontor 125");
		}

		/* Mellom 26 og 31 */
		else if (dato >= 26 && dato <= 31) {
			showMessageDialog(null, "Du kan henvende deg til kontor 134");

		}

		else {
			showMessageDialog(null, "Du har tastet inn et ugyldig valg, benytt et tall fra 1-31");

		}

	}

}
