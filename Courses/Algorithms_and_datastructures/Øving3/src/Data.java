import java.util.Random;
import java.util.Date;
public class Data {


    public static void bytt(int []t, int i, int j){
        int k = t[j];
        t[j] = t[i];
        t[i] = k;
    }

    public static int median3sort(int []t, int v, int h){
        int m = (v + h)/2;
        if(t[v] > t[m]) bytt(t,v,m);
        if(t[m]>t[h]){
            bytt(t, m, h);
            if(t[v]>t[m]) bytt(t,v,m);
        }
        return m;
    }

    private static void insertionSort(int[] t, int v, int h) {
        for (int j = v; j <= h; j++) {
            int swap = t[j];
            int i = j - 1;
            while (i >= v && t[i] > swap) {
                t[i + 1] = t[i];
                i--;
            }
            t[i + 1] = swap;
        }
    }


        public static int splitt(int []t, int v, int h){
                int iv, ih;
                int m = median3sort(t, v, h);
                int dv = t[m];
                bytt(t, m, h - 1);
                for (iv = v, ih = h - 1; ; ) {
                    while (t[++iv] < dv) ;
                    while (t[--ih] > dv) ;
                    if (iv >= ih) break;
                    bytt(t, iv, ih);
                }
                bytt(t, iv, h - 1);
                return iv;
    }



    public static void quicksort (int t[], int v, int h) {
        if(h - v >4600) {
            int delepos = splitt(t,v,h);
            quicksort(t,v,delepos-1);
            quicksort(t, delepos +1, h);

        } else insertionSort(t,v,h);
    }


    public static void main(String[] args){
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        int n = 1000000;
        Random rnd = new Random();

        int[] array = new int[n];
        for(int i = 0; i < n; i++){
            array[i]= rnd.nextInt(100);
        }

        int checksum =0;
        for(int i=0; i< array.length; i++){
            checksum += array[i];
        }


        do {
            quicksort(array, 0, n-1);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);

        tid = (double)
                (slutt.getTime()-start.getTime()) / runder;
        System.out.println("Millisekund pr. runde: " + tid);


        //feil test

        for(int i = 0; i < array.length-2; i++){
            if (array[i+1] >= array[i]){

            } else {
                System.out.println("Feil");
            }
        }

        int checkAgainst =0;
        for(int i=0; i<array.length; i++){
            checkAgainst += array[i];
        }

        if(checkAgainst == checksum){
            System.out.println("Same checksum");
        }



    }

}



























