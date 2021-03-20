/* Tjener klient for tabell.java, lager tilfeldige tall */

public class Random{
  private int randomTall;
  java.util.Random tilfeldig = new java.util.Random();

  public Random(int randomTall){
    this.randomTall = randomTall;
  }

  public int getRandom(){

      randomTall = tilfeldig.nextInt(10);

        return randomTall;
  }







}
