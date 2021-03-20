/** MinRandom.java
* Program som generer et heltall og et desimaltall innenfor et gitt intervall */

import java.util.Random;
class MinRandom{



  /* intervvallet Heltall [nedre, ovre] */
  public static int nesteHeltall(int nedre, int ovre){
    java.util.Random hel = new java.util.Random();
    int nesteHeltall = hel.nextInt(Math.abs(ovre+1-nedre))+nedre;
    return nesteHeltall;
  }

  public static double nesteDesimaltall(){
  java.util.Random des = new java.util.Random();
  double nesteDesimaltall = des.nextDouble();
  double rangeMin =1;
  double rangeMax = 9;
  double randomDesValue = rangeMin + (rangeMax - rangeMin) * des.nextDouble();

  return randomDesValue;
  }


  public static void main(String[] args){
    int nedre = 1;
    int ovre = 9;
    double nedre1=1;
    double ovre1=9.1;

    System.out.println("Neste heltall er: " + nesteHeltall(nedre, ovre));
    System.out.println("Neste desimaltall er: " + nesteDesimaltall());

  }

}
