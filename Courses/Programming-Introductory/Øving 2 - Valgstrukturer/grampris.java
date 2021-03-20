
/** grampris.java
* Regner kiloprisen og forteller hvilken kjøttdeig som er billigst */

import static javax.swing.JOptionPane.*;

class grampris {
	public static void main(String[] args) {

		/* Dialog for kjøttdeig A */
		String prisALest = showInputDialog(null, "Skriv inn pris for kjøttdeig A: ");
		double prisA = Double.parseDouble(prisALest);

		String kiloALest = showInputDialog(null, "Skriv inn antall gram for kjøttdeig A");
		double kiloA = Double.parseDouble(kiloALest);

		/* Dialog for kjøttdeig B */
		String prisBLest = showInputDialog(null, "Skriv inn pris for kjøttdeig B: ");
		double prisB = Double.parseDouble(prisBLest);

		String kiloBLest = showInputDialog(null, "Skriv inn antall gram for kjøttdeig B");
		double kiloB = Double.parseDouble(kiloBLest);

		/* Regnestykke for kiloprisene */

		double kilopris = prisA / kiloA * 1000;
		double kilopris2 = prisB / kiloB * 1000;

		if (kilopris < kilopris2) {
			showMessageDialog(null, "Kjøttdeig A er billigst med en kilopris på " + kilopris + "kr");
		} else {
			showMessageDialog(null, "Kjøttdeig B er billigst med en kilopris på " + kilopris2 + "kr");

		}

	}

}