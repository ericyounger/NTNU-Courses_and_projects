/** tabell.java
* Program som henter ut n-tilfeldig tall og lister det opp hvor mange
* ganger hvert tall har blitt trukket */


class tabell{
  public static void main(String[]args){
    Random tall = new Random(0);
    int antallRunder= 10000;
    int[] antall = new int[10];

    for(int h=0; h<antallRunder; h++){
      int random = tall.getRandom();
      antall[random]++;
    }


    for(int i=0; i<antall.length; i++){ // Henter ut tabell og printer ut
      System.out.println("Antall " + i + ": " + antall[i]);
    }
    System.out.print("\n");
  }


}
