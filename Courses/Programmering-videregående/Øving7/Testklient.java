import java.util.*;
import java.time.LocalDate;
import static javax.swing.JOptionPane.*;

class Testklient{
    public static void main(String[]args){
        String[] muligheter = {"Nytt medlem", "Registrer poeng", "Finn poeng", "Sjekk medlem", "Skriv ut medlnr", "Avslutt"};
        Medlemsarkiv a = new Medlemsarkiv();
        Personalia tove = new Personalia("Hansen", "Tove", "tove.hansen@dot.com", "tove");
        LocalDate testdato = LocalDate.of(2008, 2, 10);
        LocalDate dagensDato = LocalDate.of(2008, 2, 10);
        System.out.println("Medlnr nr: "+ a.nyMedlem(tove, testdato));


        int valg = showOptionDialog(null, "Hva ønsker du å gjøre?", "BonusMedlem", YES_NO_OPTION, INFORMATION_MESSAGE, null, muligheter, muligheter[0]);
        final int NYTT_MEDLEM = 0;
        final int REGISTRER_POENG = 1;
        final int FINN_POENG = 2;
        final int SJEKK_MEDLEM = 3;
        final int SKRIV_UT_REG = 4;
        final int AVSLUTT = 5;

        while(valg !=AVSLUTT){
            switch(valg){
              case NYTT_MEDLEM:
              String fornavn = showInputDialog(null, "Skriv inn fornavn");
              String etternavn = showInputDialog(null, "Skriv inn etternavn");
              String epost = showInputDialog(null, "Skriv inn epost adressen");
              String passord = showInputDialog(null, "Skriv inn passord");
              Personalia b = new Personalia(etternavn, fornavn, epost, passord);

             int medlNrRetur = a.nyMedlem(b, dagensDato);
             if(medlNrRetur>=0){
                showMessageDialog(null, "Registrering fullført, kunden har medlnr: " +medlNrRetur);
             } else {
                 showMessageDialog(null, "Noe gikk feil under registering");
             }
              break;
              case REGISTRER_POENG:
              String medlnrRegTekst = showInputDialog(null, "Skriv inn medlnr: ");
              int medlnrReg = Integer.parseInt(medlnrRegTekst);
              String poengTekst = showInputDialog(null, "Hvor mye poeng skal registreres?");
              int poeng = Integer.parseInt(poengTekst);
              boolean status =a.registrerPoeng(medlnrReg, poeng); 
              if(status){
                  showMessageDialog(null, "Registering gjennomført");
              } else{
                  showMessageDialog(null, "Noe gikk feil, registrering ikke gjennomført");
              }
              break;
              case FINN_POENG:
              String medlNummerTekst = showInputDialog(null, "Skriv inn medlemsnr");
              int medlnrInt = Integer.parseInt(medlNummerTekst);
              String passordTekst = showInputDialog(null, "Skriv inn passordet");
              showMessageDialog(null, "Medlnr: " + medlnrInt + ", har " + a.finnPoeng(medlnrInt, passordTekst) + " poeng");

              break;
              case SJEKK_MEDLEM:
              showMessageDialog(null, a.sjekkMedlemmer());
              break;
              case SKRIV_UT_REG:
              showMessageDialog(null,a.toString());
              break;
              case AVSLUTT:
              break;
              default:
              break;
            }
            valg = showOptionDialog(null, "Hva ønsker du å gjøre?", "BonusMedlem", YES_NO_OPTION, INFORMATION_MESSAGE,
                    null, muligheter, muligheter[0]);
        }
       
    }
}