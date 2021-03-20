import java.util.ArrayList;
import static javax.swing.JOptionPane.*;

class Rom{
    private final int storrelse;
    private final int romNr;
    private ArrayList<Reservasjon> reservasjon = new ArrayList<Reservasjon>();

    public Rom(int storrelse, int romNr){
        this.storrelse = storrelse;
        this.romNr = romNr;
    }
    public int getRomNr(){
        return romNr;
    }

    public int getStorrelse(){
        return storrelse;
    }

    public boolean leggTilReservasjon(Reservasjon e){
        System.out.println("Før loop");
       if(reservasjon.isEmpty()){
            reservasjon.add(e);
            return true;
       }
            for (int i = 0; i < reservasjon.size(); i++) { // VIL IKKE GÅ INN I LOOP??
                boolean opptatt = reservasjon.get(i).overlapp(e.getFraTid(), e.getTilTid());
                if (!opptatt) {
                    reservasjon.add(e);
                    return true;
                }
            }       
        return false;
        
    }
    

    public String toString(){
        String tmp = "Rom Nr " + getRomNr() + ". Plass til: " + getStorrelse() + "stk\n";
        for(int i=0; i<reservasjon.size(); i++){
            tmp += reservasjon.get(i).toString() + "\n";
        }
        return tmp;
    }
// TEST KLIENT
// HAR BARE TATT COPY PASTE AV EN ANNEN TEST METODE, MÅ ENDRE PÅ DETTE

/******************************************************* */
    public static void main(String[] args) {
        Kunde k = new Kunde("Anne Hansen", "12345678");
        System.out.println("Totalt antall tester: ");
        Reservasjon r1 = new Reservasjon(new Tidspunkt(200302011000L), new Tidspunkt(200302011100L), k);
        Reservasjon r2 = new Reservasjon(new Tidspunkt(200301211000L), new Tidspunkt(200301211030L), k);
        Reservasjon r3 = new Reservasjon(new Tidspunkt(200302011130L), new Tidspunkt(200302011300L), k);
        Reservasjon r4 = new Reservasjon(new Tidspunkt(200302010900L), new Tidspunkt(200302011100L), k);
        if (r1.toString().equals("Kunde: Anne Hansen, tlf: 12345678, fra 01-02-2003 kl 1000, til 01-02-2003 kl 1100")
                && r2.toString()
                        .equals("Kunde: Anne Hansen, tlf: 12345678, fra 21-01-2003 kl 1000, til 21-01-2003 kl 1030")
                && r3.toString()
                        .equals("Kunde: Anne Hansen, tlf: 12345678, fra 01-02-2003 kl 1130, til 01-02-2003 kl 1300")
                && r4.toString()
                        .equals("Kunde: Anne Hansen, tlf: 12345678, fra 01-02-2003 kl 0900, til 01-02-2003 kl 1100")) {
            System.out.println("Reservasjon: Test 1 vellykket.");
        }

        if (r1.overlapp(new Tidspunkt(200302011000L), new Tidspunkt(200302011100L))
                && !r1.overlapp(new Tidspunkt(200302021000L), new Tidspunkt(200302021100L))
                && r1.overlapp(new Tidspunkt(200302011030L), new Tidspunkt(200302011100L))
                && r1.overlapp(new Tidspunkt(200302010930L), new Tidspunkt(200302011030L))) {
            System.out.println("Reservasjon: Test 2 vellykket.");
        }
        // Flg. setning kaster exception (fra-tid lik til-tid)
        // Reservasjon r5 = new Reservasjon(new Tidspunkt(200302011100L), new
        // Tidspunkt(200302011100L), k);
        // Flg. setning kaster exception (fra-tid > til-tid)
        // Reservasjon r5 = new Reservasjon(new Tidspunkt(200302011130L), new
        // Tidspunkt(200302011100L), k);
    }
}