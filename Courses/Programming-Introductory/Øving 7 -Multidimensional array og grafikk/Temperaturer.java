import java.util.Scanner;
import java.lang.Math;



public class Temperaturer{
  private int temp = 0;
  private int timer = 24;
  private int dager = 31;
  private int[][] array = new int[dager][timer];
  private double[] sumDagArray = new double[dager];


  public Temperaturer(){
    java.util.Random tilfeldig = new java.util.Random();
    Scanner inn = new Scanner(System.in);
    int nedre = -20;
    int ovre = 25;
    for(int j=0; j<array.length; j++){
      for(int i=0; i<array[j].length; i++){
        array[j][i] = tilfeldig.nextInt(Math.abs(ovre+1-nedre))+nedre; // sender inn tilfeldig tall til array
      }
    }
    System.out.print("      ");
    for(int a=0; a<timer; a++){
      System.out.printf("%5d", a); //printer timer som finnes
    }
    System.out.println("\n      ------------------------------------------------------------------------------------------------------------------------");

    for(int a=0; a<array.length; a++){
      System.out.printf("%5d", a); // printer dager som finnes
      System.out.print("|");
      for(int b=0; b<array[a].length; b++){
        System.out.printf("%5d", array[a][b]); //printer tabell
      }
      System.out.println();
    }
    setMiddelDag();
  }  //end konstruktør

  public void setSumDag(){
    int sum = 0;

    for(int a=0; a<array.length; a++){
      sum =0; //nullstiller sum til neste dag.
      for(int i=0; i<timer; i++){
        sum += array[a][i];
      }
      sumDagArray[a] = sum; //legger summen inn i ny tabell
    }
  }
  private void setMiddelDag(){
    setSumDag(); //setter sum inn i array
    for(int i = 0; i<sumDagArray.length; i++){
      sumDagArray[i] /= timer; //endrer innholdet til array til middelverdi
    }
  }

  public double getMiddelDag(int dag){
    if (dag < 0 || dag>30){
      throw new IllegalArgumentException ("Må velge en dag mellom 0-" +(dager-1));
    }
    return sumDagArray[dag];
  }

  public double getMiddelMnd(){
    double sum = 0 ;
    for(int i=0; i<sumDagArray.length; i++){
      sum += sumDagArray[i];
    }
    double MiddelMnd = sum/dager;
    return MiddelMnd;
  }

  public double getMiddleTime(int time){
    double sumTime = 0;
    for(int i=0; i<array.length; i++){
      sumTime += array[i][time];
    }
    double middelTime = sumTime/dager;
    return middelTime;
  }

  public void intervall(){
    int intervall1 = 0; // mindre enn -5 grader
    int intervall2 = 0; // mellom -5 og 0 grader
    int intervall3 = 0; // mellom 0 og 5 grader
    int intervall4 = 0; // mellom 5 og 10 grader
    int intervall5 = 0; // over 10 grader

    for(int i=0; i<sumDagArray.length; i++){
      if(sumDagArray[i]< -5){
        intervall1++;
      } else if(sumDagArray[i]> -5 && sumDagArray[i]<0){
        intervall2++;
      } else if(sumDagArray[i]>0 && sumDagArray[i]<5){
        intervall3++;
      } else if(sumDagArray[i]>5 && sumDagArray[i]<10){
        intervall4++;
      } else if(sumDagArray[i]>10){
        intervall5++;
      }
    } // end for loop
    System.out.println("\nMindre enn -5: " + intervall1 + "\nMellom -5 og 0 grader: " + intervall2 +
    "\nMellom 0 og 5 grader: " + intervall3 + "\nMellom 5 og 10 grader: " +intervall4 + "\nOver 10 grader: " + intervall5);
  }

  public void bgs(){
    System.out.println("");
    boolean input =true;
    Scanner tallinn = new Scanner(System.in);
    while(input){
      System.out.println("\nVelg ønsket operasjon:\n1:Middeltemperatur dag     2:Middeltemperatur Måned     3:Middeltemperatur time     4:Middeltemperatur intervall     5:Avslutt");

      int tall = tallinn.nextInt();

      switch(tall){
        case 1:

        try{
          System.out.println("Tast inn hvilken dag: ");
          int dag = tallinn.nextInt();
          System.out.println("\n----------------------------\nMiddel Temperatur dag " +dag + ": "
          +getMiddelDag(dag) +"\n----------------------------");
          break;

        }
        catch(Exception e){
          System.out.println("\nUgyldig inntasting, tast dag nr mellom 0-"+(dager-1));
          continue;
        }

        case 2:
          System.out.println("\n-------------------------------\nMiddel Temperatur Mnd "
          + ": " +getMiddelMnd()+"\n-------------------------------");
          break;
        case 3:
          try{
          System.out.println("Tast inn hvilken time: ");
          int time = tallinn.nextInt();
          System.out.println("\n-------------------------------\nMiddel Temperatur Time "
          + time + ": " +getMiddleTime(time)+"\n-------------------------------");
          break;
        }
        catch(Exception e){
          System.out.println("\nUgyldig inntasting, tast time nr mellom 0-"+(timer-1));
          continue;
        }
        case 4:
        intervall();
          break;
        case 5:
          input = false;
          break;
        default:
          System.out.println("\nDet du tastet er utenforbi rekkevidden, tast inn tall mellom 1-5");
          break;
      }
    }
  }

} // end Temperatur class
