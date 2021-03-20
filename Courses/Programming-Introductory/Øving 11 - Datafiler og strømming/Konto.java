import java.io.*;
import java.lang.*;
import static javax.swing.JOptionPane.*;
public class Konto{
  double startsaldo;
  double saldo1;
  double sluttsum = (startsaldo+saldo1); //funker ikke


  public Konto(String saldo, String transaksjoner)throws IOException{
     // Saldo innlesing
     FileReader leseforbTilFil2 = new FileReader(saldo);
     BufferedReader leser2 = new BufferedReader(leseforbTilFil2);
     String etBelop2= leser2.readLine();
     double startS = Double.parseDouble(etBelop2);
     this.startsaldo = startS;
     leser2.close();
     System.out.print("\033[H\033[2J");
     System.out.flush();

     // try{
     //
     // }
     // catch(IOException | NumberFormatException e) {
     //   System.out.println(e.getMessage());
     // }
     // catch(Exception e) {
     //   e.printStackTrace();
     // }

     //Transaksjoner innlesing
     FileReader leseforbTilFil = new FileReader(transaksjoner);
     BufferedReader leser = new BufferedReader(leseforbTilFil);
     String etBelop= leser.readLine();
     if(etBelop.charAt(0) == 'I' && etBelop !=null){
       String belop = etBelop.substring(2);
       double tallBelop = Double.parseDouble(belop);
       setSaldo(tallBelop);
     } else if(etBelop.charAt(0) == 'U' && etBelop !=null){
       String belop = etBelop.substring(2);
       double tallBelop = Double.parseDouble(belop);
       setSaldo((-tallBelop));
     }
     String innlesteTrans = "Følgende transaksjoner er registrert";

     while(etBelop != null){
       innlesteTrans += ("\n" + etBelop);
       etBelop = leser.readLine();
       if(etBelop != null){
         if(etBelop.charAt(0) == 'I'){
           String belop = etBelop.substring(2);
           double tallBelop = Double.parseDouble(belop);
           setSaldo(tallBelop);
        } else if(etBelop.charAt(0) == 'U'){
           String belop = etBelop.substring(2);
           double tallBelop = Double.parseDouble(belop);
           setSaldo((-tallBelop));
          }
     }

     }
     leser.close();
     System.out.print("\033[H\033[2J");
     System.out.flush();
     System.out.println(innlesteTrans);




   }

  public double getSaldo(){
    return saldo1;
  }

  public double getStartSaldo(){
    return startsaldo;
  }

  public double getSluttSaldo(){
    return sluttsum;
  }

  public void setSaldo(double nyTranstall){
    this.saldo1 += nyTranstall;
  }



  //leser transaksjoner
  public void readInput(String transaksjoner) throws IOException{
    FileReader leseforbTilFil = new FileReader(transaksjoner);
    BufferedReader leser = new BufferedReader(leseforbTilFil);
    String etBelop= leser.readLine();
    String innlesteTrans = "Følgende transaksjoner er registrert";
    while(etBelop != null){
      innlesteTrans += ("\n" + etBelop);
      etBelop = leser.readLine();
    }
    leser.close();
    System.out.println(innlesteTrans);
    if(getStartSaldo()+getSaldo()<0){
      System.out.println("\nNy saldo: " + startsaldo);
    } else if(getStartSaldo()+getSaldo()>0)
    System.out.println("\nNy saldo: " + (startsaldo+saldo1));
  }

  public void writeOutput(String saldo, String transaksjoner) throws IOException{
    System.out.println("\n-------------------------------------\n");
    System.out.println("Saldo: " +getStartSaldo() +"\n");
    int svar = showConfirmDialog(null, "Skal det utføres transaksjoner?", "Transaksjoner", YES_NO_OPTION);

    //transaksjoner skriv til fil
    FileWriter skriveforbTilFil = new FileWriter(transaksjoner, true);
    PrintWriter skriver = new PrintWriter(new BufferedWriter(skriveforbTilFil));
    while(svar == YES_OPTION){
      String nytrans = showInputDialog("Oppgi en transaksjon: ");
      double nyTranstall = Double.parseDouble(nytrans);
      if(nyTranstall>= 0){
        skriver.println("I " +nyTranstall);
        setSaldo(nyTranstall);
      } else if(nyTranstall<=0){
        setSaldo(nyTranstall);
        skriver.println("U " +nyTranstall*(-1));
      }
      svar = showConfirmDialog(null, "Skal flere transaksjoner registreres?", "Transaksjoner", YES_NO_OPTION);
    }

  //Saldo skriv til fil hvis saldo er over 0

      FileWriter skriveforbTilFil2 = new FileWriter(saldo, false); //endre denne til false når det stemmer
      PrintWriter skriver2 = new PrintWriter(new BufferedWriter(skriveforbTilFil2));
      if(getStartSaldo()+getSaldo()<0){
        skriver2.println(getStartSaldo());
        System.out.println("Ikke dekning, transaksjon ikke fullført\n");
        skriver2.close();
      } else{
        skriver2.println(getStartSaldo()+getSaldo());
      skriver2.close();
      skriver.close();
    }

  }


} //class end
