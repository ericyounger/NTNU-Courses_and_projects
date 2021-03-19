import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

class CodeChecker {

    final static int curlyBracketStart = 123;
    final static int curlyBracketEnd = 125;
    final static int parentesisStart = 40;
    final static int leftSquareBracket = 91;
    final static int parantesisEnd = 41;
    final static int rightSquareBracket = 93;


    public static void main(String[] args) throws Exception {
        File fil = new File("src/Test.txt");
        FileReader fileReader = new FileReader(fil);
        BufferedReader reader = new BufferedReader(fileReader);
        Stack<Integer> stack = new Stack<>();
        int character = 0;

        do{
            character = reader.read();


                if (character == curlyBracketStart) {
                    stack.push(curlyBracketStart);
                }

                else if (character == parentesisStart) {
                    stack.push(parentesisStart);
                }

                else if (character == leftSquareBracket) {
                    stack.push(leftSquareBracket);
                }


                else if (character == parantesisEnd) {
                    if (stack.peek() == parentesisStart) {
                        stack.pop();
                    } else {
                        throw new Exception();
                    }

                }

                else if (character == curlyBracketEnd) {
                    if (stack.peek() == curlyBracketStart) {
                        stack.pop();
                    } else {
                        throw new Exception();
                    }
                }

                else if (character == rightSquareBracket) {
                    if (stack.peek() == leftSquareBracket) {
                        stack.pop();
                    } else {
                        throw new Exception();
                    }
                }


        }while(character>-1);


        if(stack.isEmpty()){
            System.out.println("Success!");
        } else{
            int lastCharacter = stack.peek();

            System.out.println("Reached end while still parsing, expected: " + (char)getReverseCharacter(lastCharacter));
        }
    }

    public static int getReverseCharacter(int character){
        int expectedCharacter = -1;
        switch (character){
            case curlyBracketStart:
                expectedCharacter = curlyBracketEnd;
                break;
            case parentesisStart:
                expectedCharacter = parantesisEnd;
                break;
            case leftSquareBracket:
                expectedCharacter =  rightSquareBracket;
                break;
            default:
                expectedCharacter = -1;
        }
        return expectedCharacter;
    }
}