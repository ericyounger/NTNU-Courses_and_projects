/* BokstavPrinter.java
 *  Program som printer ut både tall og bokstaver.
 *  Printeren skriver ut rundt 7 bokstaver per linje, men tar automatisk linjeSkift
 *  hvis noen bokstaver er større eller mindre enn andre på linjen, slik at det i noen tilfeller
 *  er plass til mer eller mindre bokstaver per linje.
 *
 *  Programmet benytter seg av klientprogrammet LegoPrinter.java som tar og oppretter et objekt
 *  og sender en string som blir omgjort til UpperCase bokstaver som argument gjennom metoden finnBokstaver.
 *
 *  Utviklet gjennom gruppearbeidet til Team 09 ved NTNU Trondheim, av Hans Kristian, Tobias, Torje, Andreas, Eric.
 *
 *  2018.
 *
 */


import lejos.hardware.motor.*;
import lejos.hardware.motor.*;
import lejos.hardware.lcd.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.Keys;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.*;
import lejos.hardware.lcd.TextLCD;



/* KLASSE FOR Å SKRIVE BOKSTAVER
 *
 *
 * Motor.A -> X-RETNING (Horisontalt)
 * Motor.B -> Y-RETNING (Vertikalt)
 * Motor.C -> Z-RETNING (Opp og ned med pennen)
 *
 *
 * */


public class BokstavPrinter {
    private static final int X_SPEED = 150; //Motorfarten i x-retning
    private static final int Y_SPEED = 50; //Motorfarten i y-retning
    private static final int Z_SPEED = 30; //Motorfarten i z-retning
    private static final int X_TID = 1000; //Antall millisekund for full strek i x-retning
    private static final int Y_TID = 1000; //Antall millisekund for full strek i y-retning
    private static final int Z_TID = 800; //Antall millisekund løfting og senking av pennen
    private static final int BOKSTAV_SPACING_TID = 160; //Antall millisekund med X_SPEED mellom bokstaver
    private static final int KURVE_OPPDELING = 50; //Hvor mange rette streker en full sirkel er bygd opp av, f. eks. vil =5 bety at en sirkel blir en femkant
    private int linjeLengde = 0; //Hvor mye av plassen på linja vi har brukt opp

    /* Konstruktor */
    public BokstavPrinter() {
       resetSpeed();
    }

    //Resetter speeden til klassekonstantene vi har for speed
    private void resetSpeed() {
        Motor.A.setSpeed(X_SPEED);
        Motor.B.setSpeed(Y_SPEED);
        Motor.C.setSpeed(Z_SPEED);
    }

    //Setter en ny vertikal speed (hvis vi skal bytte vekk fra Y_SPEED)
    private void vertikalSpeed(int nySpeed) {
        Motor.B.setSpeed(nySpeed);
    }

    //Setter en ny horisontal speed (hvis vi skal bytte vekk fra Y_SPEED)
    private void horisontalSpeed(int nySpeed) {
        Motor.A.setSpeed(nySpeed);
    }


    //HER ER METODENE FOR Å SKRIVE BOKSTAVER----------------------------------------------------------------
    /*ALLE MÅ AVSLUTTE MED Å HA PENNEN OVER BAKKEN, OPPE I HØYRE HJØRNET
     * penOpp();
     * penNed();
     * ALLE UNDER TAR EN INT SOM PARAMETER, SOM ER DIVISOR FOR TIDEN DEN SKAL GJØRE DET (2 vil si halve tiden den vanligvis gjør det)
     * DIAGONALMETODENE KAN BRUKE TO PARAMETER, EN TIDSDIVISOR SOM DELER TIDEN VI BRUKER PÅ DEN OG EN XDIVISOR SOM DELER BREDDEN
     * hStrekHoyre();
     * hStrekVenstre();
     * vStrekNed();
     * vStrekOpp();
     * dStrekHoyreOpp();
     * dStrekHoyreNed();
     * dStrekVenstreOpp();
     * dStrekVenstreNed();
     *
     * Se forklaring ved metoden:
     * sirkelKurve();
     * */
    private void skrivSpace() {
        hStrekHoyre(4);
    }

    private void skrivA() {
        vStrekNed(1);
        penNed();
        dStrekHoyreOpp(1, 2);
        dStrekHoyreNed(1, 2);
        penOpp();
        hStrekVenstre(2);
        dStrekHoyreOpp(2, 2);
        penNed();
        hStrekHoyre(4);
        penOpp();
        dStrekHoyreOpp(2, 2);
    }

    private void skrivB() {
        penNed();
        sirkelKurve(0, Math.PI, 1, 2);
        sirkelKurve(0, Math.PI, 1, 2);
        vStrekOpp(1);
        penOpp();
        hStrekHoyre(4);
    }

    private void skrivC() {
        hStrekHoyre(2);
        vStrekNed(1);
        penNed();
        sirkelKurve(Math.PI, 2 * Math.PI, 1, 1);
        penOpp();
    }

    private void skrivD() {
        penNed();
        sirkelKurve(0, Math.PI, 1, 1);
        vStrekOpp(1);
        penOpp();
        hStrekHoyre(2);
    }

    private void skrivE() {
        penNed();
        vStrekNed(1);
        hStrekHoyre(3);
        penOpp();
        hStrekVenstre(3);
        vStrekOpp(2);
        penNed();
        hStrekHoyre(3);
        penOpp();
        hStrekVenstre(3);
        vStrekOpp(2);
        penNed();
        hStrekHoyre(3);
        penOpp();
    }

    private void skrivF() {
        penNed();
        vStrekNed(1);
        penOpp();
        vStrekOpp(2);
        penNed();
        hStrekHoyre(3);
        penOpp();
        hStrekVenstre(3);
        vStrekOpp(2);
        penNed();
        hStrekHoyre(3);
        penOpp();
    }

    private void skrivG() {
        hStrekHoyre(2);
        vStrekNed(2);
        penNed();
        hStrekHoyre(3);
        vStrekNed(2);
        hStrekVenstre(3);
        sirkelKurve(Math.PI, 2 * Math.PI, 1, 1);
        hStrekHoyre(2);
        penOpp();

    }

    private void skrivH() {
        penNed();
        vStrekNed(1);
        penOpp();
        vStrekOpp(2);
        penNed();
        hStrekHoyre(2);
        penOpp();
        vStrekNed(2);
        penNed();
        vStrekOpp(1);
        penOpp();
    }

    private void skrivI() {
        penNed();
        hStrekHoyre(2);
        penOpp();
        hStrekVenstre(4);
        penNed();
        vStrekNed(1);
        penOpp();
        hStrekVenstre(4);
        penNed();
        hStrekHoyre(2);
        penOpp();
        vStrekOpp(1);
        hStrekHoyre(6);

    }

    private void skrivJ() {
        hStrekHoyre(5);
        penNed();
        vStrekNed(2);
        vStrekNed(4);
        sirkelKurve(Math.PI / 2, (3 * Math.PI) / 2, 2, 2);
        penOpp();
        hStrekHoyre(2);
        vStrekOpp(2);
        vStrekOpp(4);
    }

    private void skrivK() {
        penNed();
        vStrekNed(1);
        penOpp();
        vStrekOpp(2);
        penNed();
        dStrekHoyreNed(2);
        penOpp();
        dStrekVenstreOpp(2);
        penNed();
        dStrekHoyreOpp(2);
        penOpp();
    }

    private void skrivL() {
        penNed();
        vStrekNed(1);
        hStrekHoyre(2);
        penOpp();
        vStrekOpp(1);
    }

    private void skrivM() {
        penNed();
        vStrekNed(1);
        penOpp();
        vStrekOpp(1);
        penNed();
        dStrekHoyreNed(2);
        dStrekHoyreOpp(2);
        vStrekNed(1);
        penOpp();
        vStrekOpp(1);
    }

    private void skrivN() {
        penNed();
        vStrekNed(1);
        penOpp();
        vStrekOpp(1);
        penNed();
        dStrekHoyreNed(1);
        vStrekOpp(1);
        penOpp();
    }

    private void skrivO() {
        hStrekHoyre(4);
        penNed();
        sirkelKurve(0, 2 * Math.PI, 1, 1);
        penOpp();
        hStrekHoyre(2);
    }

    private void skrivP() {
        penNed();
        vStrekNed(1);
        penOpp();
        vStrekOpp(1);
        penNed();
        sirkelKurve(0, Math.PI, 1, 2);
        penOpp();
        vStrekOpp(2);
        hStrekHoyre(4);
    }

    private void skrivQ() {
        //DENNE TROR JEG SER LITT RAR UT NÅ, MEN VENTER TIL TESTING
        hStrekHoyre(4);
        penNed();
        sirkelKurve(0, 2 * Math.PI, 1, 1);
        penOpp();
        vStrekNed(2);
        penNed();
        dStrekHoyreNed(2);
        penOpp();
        dStrekVenstreOpp(2);
        vStrekOpp(2);
        hStrekHoyre(2);
    }

    private void skrivR() {
        penNed();
        vStrekNed(1);
        penOpp();
        vStrekOpp(1);
        penNed();
        sirkelKurve(0, Math.PI, 1, 2);
        dStrekHoyreNed(2);
        penOpp();
        dStrekVenstreOpp(2);
        vStrekOpp(2);
        hStrekHoyre(4);
    }

    private void skrivS() {
        //siiiinuuuuus, mulig vi kan bruke den fremtidige sirkelmetoden, prøver meg på det
        hStrekHoyre(3);
        penNed();
        sirkelKurve(1 / 6 + Math.PI, -Math.PI + Math.PI, 1, 2);
        sirkelKurve(-Math.PI + Math.PI, 3 * Math.PI / 2, 1, 2);
        penOpp();
        vStrekOpp(2);
        vStrekOpp(4);
        hStrekHoyre(4);
        hStrekHoyre(3);

    }

    private void skrivT() {
        penNed();
        hStrekHoyre(2);
        hStrekHoyre(4);
        penOpp();
        hStrekVenstre(4);
        hStrekVenstre(8);
        penNed();
        vStrekNed(1);
        penOpp();
        vStrekOpp(1);
        hStrekHoyre(2);
    }

    private void skrivU() {
        hStrekHoyre(4);
        penNed();
        vStrekNed(2);
        vStrekNed(4);
        sirkelKurve(Math.PI / 2, (3 * Math.PI) / 2, 2, 2);
        vStrekOpp(2);
        vStrekOpp(4);
        penOpp();
        hStrekHoyre(2);
    }

    private void skrivV() {
        penNed();
        dStrekHoyreNed(1, 2);
        dStrekHoyreOpp(1, 2);
        penOpp();
    }

    private void skrivW() {
        penNed();
        vStrekNed(1);
        dStrekHoyreOpp(2);
        dStrekHoyreNed(2);
        vStrekOpp(1);
        penOpp();
    }

    private void skrivX() {
        penNed();
        dStrekHoyreNed(1);
        penOpp();
        hStrekVenstre(2);
        penNed();
        dStrekHoyreOpp(1);
        penOpp();
    }

    private void skrivY() {
        penNed();
        dStrekHoyreNed(2);
        vStrekNed(2);
        penOpp();
        vStrekOpp(2);
        penNed();
        dStrekHoyreOpp(2);
        penOpp();
    }

    private void skrivZ() {
        penNed();
        hStrekHoyre(2);
        dStrekVenstreNed(1);
        hStrekHoyre(2);
        penOpp();
        vStrekOpp(1);
    }

    private void skrivAE() {
        vStrekNed(3);
        hStrekHoyre(7);
        dStrekVenstreNed(2, 2);
        dStrekVenstreNed(6, 2);

        penNed();
        dStrekHoyreOpp(1, 2);
        dStrekHoyreOpp(4, 2);

        //dStrekHoyreNed(1,2);
        vStrekNed(1);
        vStrekNed(4);
        hStrekHoyre(3);
        penOpp();
        hStrekVenstre(3);
        hStrekVenstre(4);
        dStrekHoyreOpp(2, 2);

        penNed();
        hStrekHoyre(3);
        penOpp();
        hStrekVenstre(4);
        vStrekOpp(2);
        penNed();
        hStrekHoyre(3);
        penOpp();
    }

    private void skrivOE() {
        hStrekHoyre(4);
        penNed();
        sirkelKurve(0, 2 * Math.PI, 1, 1);
        penOpp();
        hStrekHoyre(3);
        penNed();
        dStrekVenstreNed(1);
        dStrekVenstreNed(5);
        penOpp();
        dStrekHoyreOpp(1);
        dStrekHoyreOpp(5);

    }

    private void skrivAA() {
        vStrekNed(1);
        penNed();
        dStrekHoyreOpp(1, 2);
        dStrekHoyreNed(1, 2);
        penOpp();
        hStrekVenstre(2);
        dStrekHoyreOpp(2, 2);
        penNed();
        hStrekHoyre(4);
        penOpp();
        dStrekVenstreOpp(2, 2);
        penNed();
        sirkelKurve(Math.PI, 3 * Math.PI, 3, 3);
        penOpp();
        hStrekHoyre(2);
    }

    private void skriv1() {
        penNed();
        dStrekVenstreNed(3);
        penOpp();
        dStrekHoyreOpp(3);
        penNed();
        vStrekNed(1);
        penOpp();
        hStrekHoyre(4);
        penNed();
        hStrekVenstre(2);
        penOpp();
        hStrekHoyre(2);
        vStrekOpp(1);
    }

    private void skriv2() {
        penNed();
        sirkelKurve(0, Math.PI, 1, 2);
        dStrekVenstreNed(2, 2);
        hStrekHoyre(2);
        penOpp();
        vStrekOpp(1);

    }

    private void skriv3() {
        penNed();
        sirkelKurve(0, Math.PI, 1, 2);
        sirkelKurve(0, Math.PI, 1, 2);
        penOpp();
        vStrekOpp(1);
        hStrekHoyre(2);
    }

    private void skriv4() {
        penNed();
        dStrekVenstreNed(2, 1);
        hStrekHoyre(2);
        penOpp();
        vStrekNed(2);
        penNed();
        vStrekOpp(1);
        penOpp();
    }

    private void skriv5() {
        penNed();
        hStrekHoyre(3);
        penOpp();
        hStrekVenstre(3);
        penNed();
        vStrekNed(2);
        sirkelKurve(0, Math.PI, 1, 2);
        penOpp();
        vStrekOpp(1);
        hStrekHoyre(2);
    }

    private void skriv6() {
        hStrekHoyre(4);
        penNed();
        dStrekVenstreNed(2);
        dStrekVenstreNed(5);
        sirkelKurve(3 * Math.PI / 2, 7 * Math.PI / 2, 2, 2);
        vStrekOpp(5);
        penOpp();
        dStrekHoyreOpp(2);
        hStrekHoyre(5);
    }

    private void skriv7() {
        penNed();
        hStrekHoyre(2);
        dStrekVenstreNed(1, 1);
        penOpp();
        dStrekHoyreOpp(2, 2);
        hStrekVenstre(4);
        penNed();
        hStrekHoyre(2);
        penOpp();
        hStrekVenstre(4);
        dStrekHoyreOpp(2, 2);
        hStrekHoyre(4);
    }

    private void skriv8() {
        penNed();
        sirkelKurve(0, 2 * Math.PI, 2, 2);
        penOpp();
        vStrekNed(2);
        penNed();
        sirkelKurve(0, 2 * Math.PI, 2, 2);
        vStrekOpp(5);
        penOpp();
        vStrekOpp(2);
        hStrekHoyre(2);
    }

    private void skriv9() {
        penNed();
        sirkelKurve(0, 2 * Math.PI, 2, 2);
        sirkelKurve(0, Math.PI / 2, 2, 2);
        vStrekNed(1);
        penOpp();
        vStrekOpp(1);
        hStrekHoyre(4);
    }

    private void skriv0() {
        hStrekHoyre(6);
        penNed();
        sirkelKurve(0, 2 * Math.PI, 2, 1);
        penOpp();
        hStrekHoyre(2);
    }

    private void skrivBindestrek() {
        vStrekNed(2);
        penNed();
        hStrekHoyre(2);
        penOpp();
        vStrekOpp(2);
        hStrekHoyre(4);
    }
//SLUTT PÅ Å SKRIVE BOKSTAVER---------------------------------------------------------------------------


//METODER FOR "PRIMITIVE" BEVEGELSER/STREKER-----------------------------------------------------------------------

    //VENTEFUNSJON
    //tid er millisekund
    private void vent(int tid) {
        try {
            Thread.sleep(tid);
        } catch (Exception e) {
        }
    }

    //LETTER SPACING
    private void nesteBokstav() {
        Motor.A.backward();
        vent(BOKSTAV_SPACING_TID);
        Motor.A.stop(true);
    }

    //LØFT PENN (Z-PLAN)
    private void penOpp() {
        Motor.C.setSpeed(Z_SPEED);
        Motor.C.backward();
        vent(Z_TID);
        Motor.C.stop(true);


    }

    //SENK PENN (Z-PLAN)
    private void penNed() {
        Motor.C.setSpeed(Z_SPEED);
        Motor.C.forward();
        vent(Z_TID);
    }

    //HORISONTAL STREK MOT HØYRE
    private void hStrekHoyre(int divisor) {
        Motor.C.stop(true);
        Motor.A.backward();
        vent(X_TID / divisor);
        Motor.A.stop(true);
    }

    //HORISONTAL STREK MOT VENSTRE
    private void hStrekVenstre(int divisor) {
        Motor.C.stop(true);
        Motor.A.forward();
        vent(X_TID / divisor);
        Motor.A.stop(true);
    }

    //VERTIKAL STREK NEDOVER
    private void vStrekNed(int divisor) {
        Motor.C.stop(true);
        Motor.B.forward();
        vent(Y_TID / divisor);
        Motor.B.stop(true);
    }

    //VERTIKAL STREK OPPOVER
    private void vStrekOpp(int divisor) {
        Motor.C.stop(true);
        Motor.B.backward();
        vent(Y_TID / divisor);
        Motor.B.stop(true);
    }

    //DIAGONAL STREK OPPOVER FRA VENSTRE MOT HØYRE
    private void dStrekHoyreOpp(int tidDivisor) {
        dStrekHoyreOpp(tidDivisor, 1);
    }

    private void dStrekHoyreOpp(int tidDivisor, int xDivisor) {
        Motor.C.stop();
        Motor.A.setSpeed((X_SPEED / 2) / xDivisor);
        Motor.A.backward();
        Motor.B.backward();
        vent(Y_TID / tidDivisor);
        Motor.A.stop(true);
        Motor.B.stop(true);
        Motor.A.setSpeed(X_SPEED);
    }

    //DIAGONAL STREK NEDOVER  MOT VENSTRE
    private void dStrekHoyreNed(int tidDivisor) {
        dStrekHoyreNed(tidDivisor, 1);
    }

    private void dStrekHoyreNed(int tidDivisor, int xDivisor) {
        Motor.C.stop();
        Motor.A.setSpeed((X_SPEED / 2) / xDivisor);
        Motor.A.backward();
        Motor.B.forward();
        vent(Y_TID / tidDivisor);
        Motor.A.stop(true);
        Motor.B.stop(true);
        Motor.A.setSpeed(X_SPEED);
    }

    //DIAGONAL STREK OPPOVER MOT VENSTRE
    private void dStrekVenstreOpp(int tidDivisor) {
        dStrekVenstreOpp(tidDivisor, 1);
    }

    private void dStrekVenstreOpp(int tidDivisor, int xDivisor) {
        Motor.C.stop();
        Motor.A.setSpeed((X_SPEED / 2) / xDivisor);
        Motor.A.forward();
        Motor.B.backward();
        vent(Y_TID / tidDivisor);
        Motor.A.stop(true);
        Motor.B.stop(true);
        Motor.A.setSpeed(X_SPEED);
    }

    //DIAGONAL STREK NEDOVER MOT VENSTRE
    private void dStrekVenstreNed(int tidDivisor) {
        dStrekVenstreNed(tidDivisor, 1);
    }

    private void dStrekVenstreNed(int tidDivisor, int xDivisor) {
        Motor.C.stop();
        Motor.A.setSpeed((X_SPEED / 2) / xDivisor);
        Motor.A.forward();
        Motor.B.forward();
        vent(Y_TID / tidDivisor);
        Motor.A.stop(true);
        Motor.B.stop(true);
        Motor.A.setSpeed(X_SPEED);
    }

    //Brukes til å skifte linje
    private void linjeSkift() {
        resetHorisontal();
        vStrekNed(1);
        vStrekNed(2);
        linjeLengde = 0;
    }

    //Resetter x-verdien til printer-pennen
    private void resetHorisontal() {
        horisontalSpeed(900);
        Motor.A.forward();
        vent(1300);
        Motor.A.stop(true);
        resetSpeed();
    }

    //SIRKELKURVE
    /*
     * "start" og "slutt" kan velges mellom 0 og 2PI. 0 som start vil si at den begynner i toppen av sirkelen og går med klokka til sluttverdien som er velges.
     * xDivisor bestemmer hvor mye mindre den skal være i x-retning
     * yDivisor bestemmer hvor mye mindre den skal være i y-retning
     *
     * Metoden kan brukes til både kurver med og mot klokka, avhengig av start og slutt
     *
     * Den brukes til å tegne en mangekant tilnærmet en sirkel, hvor nøyaktig den er øker med KURVE_OPPDELING
     * */
    private void sirkelKurve(double start, double slutt, int xDivisor, int yDivisor) {
        if (slutt <= start) {
            double oppdeling = (2 * Math.PI) / KURVE_OPPDELING;
            for (double i = start; i >= slutt; i -= oppdeling) {
                Motor.C.stop();
                int hSpeed = (int) Math.round(Math.cos(i) * Y_SPEED / xDivisor);
                int vSpeed = (int) Math.round(Math.sin(i) * Y_SPEED / yDivisor);
                horisontalSpeed(Math.abs(hSpeed * 2));
                vertikalSpeed(Math.abs(vSpeed));
                if (hSpeed >= 0) {
                    Motor.A.backward();
                } else {
                    Motor.A.forward();
                }
                if (vSpeed >= 0) {
                    Motor.B.forward();
                } else {
                    Motor.B.backward();
                }
                vent((int) (Math.ceil(Y_TID / KURVE_OPPDELING) * 3));
                Motor.A.stop(true);
                Motor.B.stop(true);
                vent(10);
            }

        } else {
            double oppdeling = (2 * Math.PI) / KURVE_OPPDELING;
            for (double i = start; i <= slutt; i += oppdeling) {
                Motor.C.stop();
                int hSpeed = (int) Math.round(Math.cos(i) * Y_SPEED / xDivisor);
                int vSpeed = (int) Math.round(Math.sin(i) * Y_SPEED / yDivisor);
                horisontalSpeed(Math.abs(hSpeed * 2));
                vertikalSpeed(Math.abs(vSpeed));
                if (hSpeed >= 0) {
                    Motor.A.backward();
                } else {
                    Motor.A.forward();
                }
                if (vSpeed >= 0) {
                    Motor.B.forward();
                } else {
                    Motor.B.backward();
                }
                //System.out.println(i+" h:"+hSpeed+" v:"+vSpeed);
                vent((int) (Math.ceil(Y_TID / KURVE_OPPDELING) * 3));
                //vent(Y_TID);
                Motor.A.stop(true);
                Motor.B.stop(true);
                vent(10);
                //System.out.println((int)Y_TID/KURVE_OPPDELING);
            }
        }
        resetSpeed(); //resetter speeden
    }


//SLUTT METODER FOR "PRIMITIVE" BEVEGELSER/STREKER-----------------------------------------------------------------


    //Sjekker om det er mer plass til bokstaver på linjen
    private void linjePlass(double divisor) {
        double plass = X_SPEED * X_TID / divisor;
        if (linjeLengde + plass > 800000) {
            linjeSkift();//Setter linjeLengde = 0
        }
        linjeLengde += plass;
    }

    //Sjekker om vi trenger en bindestrek eller om ordet kan bli skrevet ferdig uten
    private boolean trengerBindestrek() {
        double plass = X_SPEED * X_TID;
        if (linjeLengde + plass*1.5 > 800000) {
            return true;
        }
        return false;
    }


    public void finnBokstaver(String tekst) {
        tekst = tekst.toUpperCase(); //gjør alt til store bokstaver
        penOpp(); //løfter pennen før den skal begynne å skrive
        resetHorisontal(); //resetter den horisontale posisjonen til printeren

        //stopper motorene før vi printer
        Motor.A.stop(true);
        Motor.B.stop(true);
        Motor.C.stop(true);

        for (int i = 0; i < tekst.length(); i++) {//looper gjennom alle bokstavene
            char bokstav = tekst.charAt(i);

            //denne prøver å finne ut om det er plass til to hele bokstaver til på linjen
            //hvis det ikke er det, lager den bindestrek og hopper til neste linje
            if(i>0){
                  char sjekkBokstav = tekst.charAt(i-1);
                  if (sjekkBokstav >= 65 && sjekkBokstav <= 90 || sjekkBokstav == 198 || sjekkBokstav == 216 || sjekkBokstav == 197) {
                      sjekkBokstav = tekst.charAt(i);
                      if (sjekkBokstav >= 65 && sjekkBokstav <= 90 || sjekkBokstav == 198 || sjekkBokstav == 216 || sjekkBokstav == 197) {
                          if (trengerBindestrek()) {
                              skrivBindestrek();
                              linjeSkift();
                          }
                      }
                  }
              }

            switch (bokstav) {
                case ('A'):
                    linjePlass(2);
                    skrivA();
                    break;
                case ('B'):
                    linjePlass(2);
                    skrivB();
                    break;
                case ('C'):
                    linjePlass(2);
                    skrivC();
                    break;
                case ('D'):
                    linjePlass(2);
                    skrivD();
                    break;
                case ('E'):
                    linjePlass(2);
                    skrivE();
                    break;
                case ('F'):
                    linjePlass(2);
                    skrivF();
                    break;
                case ('G'):
                    linjePlass(0.5);
                    skrivG();
                    break;
                case ('H'):
                    linjePlass(1);
                    skrivH();
                    break;
                case ('I'):
                    linjePlass(2);
                    skrivI();
                    break;
                case ('J'):
                    linjePlass(2);
                    skrivJ();
                    break;
                case ('K'):
                    linjePlass(2);
                    skrivK();
                    break;
                case ('L'):
                    linjePlass(2);
                    skrivL();
                    break;
                case ('M'):
                    linjePlass(2);
                    skrivM();
                    break;
                case ('N'):
                    linjePlass(2);
                    skrivN();
                    break;
                case ('O'):
                    linjePlass(1);
                    skrivO();
                    break;
                case ('P'):
                    linjePlass(2);
                    skrivP();
                    break;
                case ('Q'):
                    linjePlass(1);
                    skrivQ();
                    break;
                case ('R'):
                    linjePlass(2);
                    skrivR();
                    break;
                case ('S'):
                    linjePlass(1);
                    skrivS();
                    break;
                case ('T'):
                    linjePlass(1);
                    skrivT();
                    break;
                case ('U'):
                    linjePlass(2);
                    skrivU();
                    break;
                case ('V'):
                    linjePlass(2);
                    skrivV();
                    break;
                case ('W'):
                    linjePlass(2);
                    skrivW();
                    break;
                case ('X'):
                    linjePlass(2);
                    skrivX();
                    break;
                case ('Y'):
                    linjePlass(2);
                    skrivY();
                    break;
                case ('Z'):
                    linjePlass(2);
                    skrivZ();
                    break;
                case ('Æ'):
                    linjePlass(1);
                    skrivAE();
                    break;
                case ('Ø'):
                    linjePlass(1);
                    skrivOE();
                    break;
                case ('Å'):
                    linjePlass(2);
                    skrivAA();
                    break;
                case '%':
                    linjeSkift();
                    break;
                case '1':
                    linjePlass(2);
                    skriv1();
                    break;
                case '2':
                    linjePlass(2);
                    skriv2();
                    break;
                case '3':
                    linjePlass(2);
                    skriv3();
                    break;
                case '4':
                    linjePlass(2);
                    skriv4();
                    break;
                case '5':
                    linjePlass(2);
                    skriv5();
                    break;
                case '6':
                    linjePlass(2);
                    skriv6();
                    break;
                case '7':
                    linjePlass(2);
                    skriv7();
                    break;
                case '8':
                    linjePlass(2);
                    skriv8();
                    break;
                case '9':
                    linjePlass(2);
                    skriv9();
                    break;
                case '0':
                    linjePlass(2);
                    skriv0();
                    break;
                case '-':
                    skrivBindestrek();
                    linjePlass(2);
                    break;
                default:
                    skrivSpace();
                    linjePlass(2);
                    break;
            }//END switch
            nesteBokstav();
        } //END loop

    }

}
