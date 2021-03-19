import java.util.Date;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int antallTall = 1000;

        int[] array = {-1,3,-9,2,2,-1,2,-1,-5}; //1

        int[] testData = new int[antallTall];
        Random randGen = new Random();

        for(int i=0; i<antallTall; i++){
            testData[i] = randGen.nextInt(20)-10;
        }

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            algoritme(testData);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)
                (slutt.getTime()-start.getTime()) / runder;
        System.out.println("Millisekund pr. runde:" + tid);
    }

    public static void algoritme(int[] array){
        int buyDay=0; //1
        int sellDay=0; //1
        int bestDeal=0; //1

        int temp =0; //1

        for(int buy=0; buy<array.length; buy++){ //1+2n
            temp=0; //1n
            for(int sell=buy+1; sell<array.length; sell++){  //1n + 2n^2
                temp += array[sell]; //2n^2

                if(temp>bestDeal){ //1n^2
                    buyDay=buy+1; // For correcting array starts at 0  //1n^2
                    sellDay=sell+1; // For correcting array starts at 0 // 1n^2
                    bestDeal= temp; // 1n^2
                }
            }
        }
    }
}

// System.out.println("Buy Day: " + buyDay+  ".  Sell day: " + sellDay + ". Stock rate change: " + bestDeal +".");

// 1.2 --  n^2 er algoritmens kompleksitet

