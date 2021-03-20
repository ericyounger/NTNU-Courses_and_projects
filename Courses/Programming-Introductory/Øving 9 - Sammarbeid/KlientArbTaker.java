import static javax.swing.JOptionPane.*;

class KlientArbTaker{
  public static void main(String[]args){

    /* Lager objekter */
    Person eric = new Person("Eric", "Younger", 1990);
    Person rebekka = new Person("Rebekka", "Oma Gimnes", 1994);
    Person henning = new Person("Henning", "Hansen", 1965);
    ArbTaker a = new ArbTaker(eric, 1001, 2013, 30000, 22);
    ArbTaker b = new ArbTaker(rebekka, 1002, 2015, 33000, 24);
    ArbTaker c = new ArbTaker(henning, 1003, 2010, 37000, 26);

    

    boolean loop= true;
    String[] personliste = {"Eric Younger", "Rebekka Oma Gimnes", "Henning Hansen"};

    while(loop){
      String ansatt = (String) showInputDialog(null, "Ansattprogram", "Velg en ansatt", QUESTION_MESSAGE, null, personliste, personliste[0]);

      switch(ansatt){
        case "Eric Younger":
          a.getBGS();
          break;
        case "Rebekka Oma Gimnes":
          b.getBGS();
          break;
        case "Henning Hansen":
          c.getBGS();
          break;
        default:
          break;

      } // switch end
    } // loop end
  } // main end
} // class end
