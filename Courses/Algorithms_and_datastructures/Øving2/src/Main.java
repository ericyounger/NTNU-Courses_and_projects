import java.util.Date;

public class Main {

    public static void main(String[] args) {
        time();
    }


    public static double rekursjon(double tall, int exponent){
        if(exponent == 1) return  tall;
        if(exponent == 0) return 1;
        if(exponent<0) return tall;
        return tall*rekursjon(tall, exponent-1);  //
    }

    public static double rekursjon2(double tall, int exponent){
        if(exponent == 0) return 1;
        if(exponent % 2 != 0) return tall*rekursjon2(tall*tall,(exponent-1)/2);
        if(exponent % 2 == 0) return rekursjon2(tall*tall, exponent/2);
        return 0;
    }

    public static double javaExp(double tall, int exponent){
        return Math.pow(tall,exponent);
    }

    public static void time(){
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            //javaExp(10,5);
            //rekursjon(10,500);
            //rekursjon2(10,500);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime()-start.getTime() < 1000);
        tid = (double)
                (slutt.getTime()-start.getTime()) / runder;
        System.out.println("Millisekund pr. runde:" + tid);
    }
}

