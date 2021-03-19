package distributedProgramming;

public class MathServices {

    enum Operations {
        ADD,
        SUBTRACT
    }

    public static int addNumbers(int no1, int no2){
        return no1+no2;
    }

    public static int subtractNumbers(int no1, int no2){
        return no1-no2;
    }
}
