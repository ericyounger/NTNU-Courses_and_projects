
import java.io.*;
class Transaksjon{
  public static void main(String[]args) throws IOException{
    String saldo = "saldo.txt";
    String transaksjoner = "transaksjoner.txt";
    Konto eric = new Konto(saldo, transaksjoner);

    eric.writeOutput(saldo, transaksjoner);
    eric.readInput(transaksjoner);

  }
}
