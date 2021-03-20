import static javax.swing.JOptionPane.*;
import java.util.ArrayList;
import java.io.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Comparator;

class TestKlient implements java.io.Serializable{
    

    public static Tribune[] sorter(Tribune[] a){
        Arrays.sort(a, new Comparator<Tribune>() {
            public int compare(Tribune a, Tribune b) {
                if (a.finnInntekt() > b.finnInntekt()) {
                    return -1;
                } else if (a.finnInntekt() == b.finnInntekt()) {
                    return 0;
                } else {
                    return 1;
                }
            }


        });
        return a;
    }
   
    public static void skrivTilFil(String filnavn, Object[] register){
        try{
        FileOutputStream fos = new FileOutputStream(filnavn);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(register);
        oos.close();
        showMessageDialog(null, "Skriving til fil vellykket");
        } catch(Exception e){
            showMessageDialog(null, "Noe gikk feil under skriving til fil");
        }
    }

    public static Tribune[] lesFraFil(String filnavn){
        try{
        FileInputStream fis = new FileInputStream(filnavn);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Tribune[] a = (Tribune[]) ois.readObject();
        ois.close();
        showMessageDialog(null, "Lesing fra fil velykket");
        return a;
        } catch(Exception e){
            showMessageDialog(null, "Noe gikk galt under lesing fra fil");
        }
        return null;

    }

    public static void main(String[]args){
        String[] muligheter = {"Ståplass billett", "Sitteplass billett", "VIP plass", "Skriv ut billetter", "Avslutt"};

        String filnavn= "liste.ser";
        final int STAAPLASS = 0;
        final int SITTEPLASS = 1;
        final int VIP_PLASS = 2;
        final int SKRIV_UT_BILLETTER =3;
        final int AVSLUTT = 4;
        
        Staa a = new Staa("A", 25, 100);
        Staa b = new Staa("B", 30, 100);
        Sitte c = new Sitte("Sittearena", 30, 150);
        VIP d = new VIP("VIP", 40, 250, 5);

        Tribune[] liste = lesFraFil(filnavn); // LISTE og LISTE2, må lage noe if setninger for å luke vekk ekstra info.
        
        if(liste == null){
            liste = new Tribune[4];
            liste[0] = a;
            liste[1] = b;
            liste[2] = c;
            liste[3] = d;
        }

        int valg;
       
        liste = sorter(liste);
        do{
        valg = showOptionDialog(null, "Hvilken billett ønsker du?", "Billettkjøp", YES_OPTION, INFORMATION_MESSAGE,
                null, muligheter, muligheter[0]);

        switch(valg){
            case STAAPLASS:
                try{
                String antBillLest = showInputDialog(null, "Hvor mange billetter ønsker du?");
                int antBill = Integer.parseInt(antBillLest);
                Billett[] y = null;
                String billetterKjøp = "Billetter kjøpt: \n";

                for(int q=0; q<liste.length; q++){
                    if(liste[q] instanceof Staa){
                        y = liste[q].kjøpBilletter(antBill);
                        if(y != null){
                            break;
                        } 
                    }
                }
                for(int w=0; w<y.length; w++){
                    billetterKjøp += y[w].toString() +"\n";
                }
                showMessageDialog(null, billetterKjøp);
                // if(a.getDiff(antBill)){
                //    y = liste[0].kjøpBilletter(antBill);
                //    String billetterKjøpt = "Billetter Kjøpt: \n";
                //    for(int i=0; i<y.length; i++){
                //         billetterKjøpt += y[i].toString() +", Tribune A" +"\n";
                //         }
                //    showMessageDialog(null, billetterKjøpt);
                // } else if(b.getDiff(antBill)){
                //     y= b.kjøpBilletter(antBill);
                //         String billetterKjøpt = "Billetter Kjøpt: \n";
                //         for (int i = 0; i < y.length; i++) {
                //             billetterKjøpt += y[i].toString() + ", Tribune B" + "\n";
                //         }
                //         showMessageDialog(null, billetterKjøpt);
                // } else{
                //     throw new NullPointerException();
                // }
                
            } catch(NullPointerException e){
                showMessageDialog(null, "Det er ikke nok ledige billetter til å foreta ditt kjøp");
            }

            break;
            case SITTEPLASS:
                try {
                    String antBillLest = showInputDialog(null, "Hvor mange billetter ønsker du?");
                    int antBill = Integer.parseInt(antBillLest);
                    Billett[] y = null;
                    for(int x=0; x<liste.length; x++){
                        if(liste[x] instanceof Sitte){
                           y = liste[x].kjøpBilletter(antBill);
                            break;
                        }
                    }
                    String billetterKjøpt = "Billetter Kjøpt: \n";
                        for (int i = 0; i < y.length; i++) {
                            billetterKjøpt += y[i].toString() +"\n" ;
                    }
                    showMessageDialog(null, billetterKjøpt);

                } catch (NullPointerException e) {
                    showMessageDialog(null, "Det er ikke nok ledige billetter til å foreta ditt kjøp");
                }
            break;
            case VIP_PLASS:
            try{
            String antBillLest =showInputDialog(null, "Hvor mange billetter vil du ha?");
            int antBill = Integer.parseInt(antBillLest);
            String[] navnBilletter = new String[antBill];
            Billett[] y = null;
            for(int i=0; i<antBill; i++){
                String navn = showInputDialog(null, "Skriv inn navn på billett");
                navnBilletter[i] = navn;
            }
            
            for(int x=0; x<liste.length; x++){
                if(liste[x] instanceof VIP){
                            y = liste[x].kjøpBilletter(navnBilletter);
                            break;
                }
            }
            if(y == null){
                throw new NullPointerException();
            } else{
                String billetterKjøpt = "Billetter Kjøpt: \n";
                for (int i = 0; i < y.length; i++) {
                    billetterKjøpt += y[i].toString() + "\n";
                    }
                showMessageDialog(null, billetterKjøpt);
            }
                    
            } catch(NullPointerException e){
                showMessageDialog(null, "Du har ikke oppgitt navn på billett");
            } catch(NumberFormatException e){
                showMessageDialog(null, "Du skrev inn noe annet enn tall");
            }

            break;
            case SKRIV_UT_BILLETTER:
            // PRINT UT IFRA ARRAY HELLER
            String toStringOut = "";
            for(int i=0; i<liste.length; i++){
                toStringOut += liste[i].toString() +"\n";
            }
            showMessageDialog(null, toStringOut); //FORELØPIG info
            break;
            default:
            break;
        }

        } while(valg != AVSLUTT);

        //KJØP 

        //SKRIV UT (TOSTRING)
        skrivTilFil(filnavn, liste);
    }
}