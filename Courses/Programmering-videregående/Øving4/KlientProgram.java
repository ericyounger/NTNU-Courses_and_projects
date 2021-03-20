import static javax.swing.JOptionPane.*;

class KlientProgram{
    public static void main(String[]args){
        Konferansesenter konferansesenter = new Konferansesenter();
        String[] muligheter = {"Les inn reservasjon", "Skriv ut all reg info", "Rom info", "Avslutt"};
        
        final int LES_INN_RESERVASJON =0;
        final int SKRIV_UT_ALL_REG_INFO =1;
        final int ROM_INFO = 2;
        final int AVSLUTT = 3;
        int valg; 
        

        //MÅ LEGGE INN EN LOOP HER
        //registrer alle rom først,
        String romnrTekst = showInputDialog(null, "Hva er Rom Nr?");
        while(romnrTekst != null){
       
        int romNr = Integer.parseInt(romnrTekst);
        String romStorrelseTekst = showInputDialog(null, "Hvor mange personer er det plass til i rommet?");
        int romStorrelse = Integer.parseInt(romStorrelseTekst);
        
        konferansesenter.leggTilRom(romStorrelse, romNr);
        romnrTekst = showInputDialog(null, "Hva er Rom Nr?");
        }
        
        do { 
        valg = showOptionDialog(null, "Antall rom registrert: " + konferansesenter.getantRom(), "Konferansesenter", YES_OPTION, INFORMATION_MESSAGE, null, muligheter, muligheter[0]);

        switch(valg){
            case LES_INN_RESERVASJON:

            
            String navn = showInputDialog(null, "Hvilket navn skal reservasjonen stå på?");
            String tlfNr = showInputDialog(null, "Mobil nr til "+ navn);
            Kunde kunde = new Kunde(navn, tlfNr);
            String fraTidTekst = showInputDialog(null, "Når skal du booke timen fra? format ååååmmddttmm");
            long fraTid = Long.parseLong(fraTidTekst);
            Tidspunkt fra = new Tidspunkt(fraTid);
            String tilTidTekst = showInputDialog(null, "Hvor lenge skal du ha booket det til? format ååååmmddttmm");
            long tilTid = Long.parseLong(tilTidTekst);
            Tidspunkt til = new Tidspunkt(tilTid);

            Reservasjon e = new Reservasjon(fra, til, kunde);
            String personerTekst = showInputDialog(null, "Hvor mange personer?");
            int personer = Integer.parseInt(personerTekst);
            boolean status = konferansesenter.leggTilReservasjon(personer, e);
            if(status){
                    showMessageDialog(null, "Booking registrert");
            } else{
                    showMessageDialog(null, "Fannt ikke ledig rom på det tidspunktet");
            }
            
                break;
            case SKRIV_UT_ALL_REG_INFO:
                showMessageDialog(null, konferansesenter.toString());
                break;
            case ROM_INFO:
                String romNrLestInn = showInputDialog(null, "Hvilket rom nr vil du ha info om?");
                int romNrTall = Integer.parseInt(romNrLestInn);
                konferansesenter.finnRom(romNrTall);
                break;
            case AVSLUTT:
                break;
        }

            
        } while (valg != AVSLUTT);
    }
}