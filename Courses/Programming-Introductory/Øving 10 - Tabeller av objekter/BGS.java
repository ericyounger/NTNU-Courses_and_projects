import static javax.swing.JOptionPane.*;

public class BGS{
  public static void meny(Oppgaveoversikt a){
    String[] array = {"Finn antall Studenter", "Finn oppg elev har løst", "Reg ny student", "Endre oppg løst"};
    boolean loop=true;
    while (loop){
      int valg = showOptionDialog(null, "Velg operasjon: ", "Velg en av disse", 0, PLAIN_MESSAGE, null, array, array[0]);
      String antStudentReg = "";
      for(int i=0; i<a.ant; i++){
        antStudentReg +=i +": " + a.studenter[i].getNavn() +"\n";
      }

      switch(valg){
        case -1:
        loop = false;
        break;
        case 0:
          showMessageDialog(null, "Antall Studenter registrert: "+ a.getantStud() +"\n"+  a);
          break;
        case 1:
          String studentvalg = showInputDialog(null, a + "\nSkriv inn nr på den du vil se oppgaver som er gjort:");
          int studentvalgnr = Integer.parseInt(studentvalg);
          showMessageDialog(null,a.studenter[studentvalgnr].getNavn() + " " +a.studenter[studentvalgnr].getAntOppg()); //Finner Første elev i array, hvordan omskrive dette
          break;
        case 2:
          String navn = showInputDialog(null, "Tast inn navnet på ny student: ");
          String oppgaveLest = showInputDialog(null, "Tast inn antall oppgaver elev har gjort: ");
          int oppgave = Integer.parseInt(oppgaveLest);
          a.nyStudent(navn, oppgave);
          break;
        case 3:
          String person = showInputDialog(null, a + "\nSkriv inn nr på den du vil endre antall oppgaver gjort:");
          int personnr = Integer.parseInt(person);
          String menyLest = showInputDialog(null, "\n" +"\nHvor mangen nye oppgaver har eleven gjort?");
          int meny = Integer.parseInt(menyLest);
          a.studenter[personnr].okAntOppg(meny); //endrer på verdien til første elev i array, hvordan omskrive dette.
          break;
        default:
        break;
      }
    } //while end
  }
}
