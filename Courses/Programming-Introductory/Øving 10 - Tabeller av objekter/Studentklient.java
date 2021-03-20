import static javax.swing.JOptionPane.*;
import java.lang.*;
class Studentklient{
  public static void main(String[]args){
    Oppgaveoversikt a = new Oppgaveoversikt(3);


    // Lage ny student med input
    a.nyStudent("Eric", 4);
    BGS.meny(a);


  } // main end
} // class end
