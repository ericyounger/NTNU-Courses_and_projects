import java.util.Scanner;
class TekstInn{
  public static void main(String[]args){
    Scanner inn = new Scanner(System.in);
    System.out.println("\nSkriv inn tekst til analyse: ");
    String input = inn.nextLine();

    Tekstbehandler tekst = new Tekstbehandler(input);

    System.out.println(tekst);

  }

}
