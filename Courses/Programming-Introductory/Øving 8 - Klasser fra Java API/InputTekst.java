import java.util.Scanner;
class InputTekst{
  public static void main(String[]args){
  Scanner inn = new Scanner(System.in);

  System.out.println("Skriv inn tekst du vil sende inn: ");
  String input = inn.nextLine();
  NyString tekst = new NyString(input);

  System.out.println("\nForkortet tekst: " + tekst.forkortelse());
  System.out.println("Fjernet tegn fra tekst: " + tekst.tegnFjerning()+"\n");
  }
}
