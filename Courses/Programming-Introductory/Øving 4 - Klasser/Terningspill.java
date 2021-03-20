
class Terningspill {
  public static void main(String[]args){
  /* Legger til nye spillere */
  Spiller a = new Spiller(0, 0, 0);
  Spiller b = new Spiller(0, 0, 0);

    System.out.println ("Kast A     Kast B       Sum A          Sum B      kast nr:");
    for (int teller=0; teller<101; teller++){
      System.out.print (a.getkastTerningen()+ "            ");
      System.out.print (b.getkastTerningen()+ "            ") ;
      System.out.print (a.getSumPoeng()+ "            ");
      System.out.print (b.getSumPoeng()+"             ");
      System.out.println (teller);
      if (a.getSumPoeng() >=100 || b.getSumPoeng()>=100) {
        break;
      }


    }
    if (a.getSumPoeng()>b.getSumPoeng()){
      System.out.print("A vant! Med " + a.getSumPoeng() + " poeng");
    } else {
      System.out.print("B vant! Med " + b.getSumPoeng() + " poeng");

    }


  }
}
