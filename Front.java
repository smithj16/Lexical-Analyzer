import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Front {

    File file;
    Scanner input;
    static int counter = 0;
    int charClass;
    String[] lexeme = new String[100];
    String[] keyword = {"for","if", "else", "while", "do","int", "float", "switch"};
    String nextChar;
    int nextToken;

    // Character Classes
    final int letter = 0, digit = 1, unknown = 99, EOF = -1;

    // Token Codes
    static int INT_LIST = 10,
            IDENT = 11,
            ASSIGN_OP = 20,
            ADD_OP = 21,
            SUB_OP = 22,
            MULT_OP = 23,
            DIV_OP = 24,
            LEFT_PAREN = 25,
            RIGHT_PAREN = 26,
            FOR_CODE = 30,
            IF_CODE = 31,
            ELSE_CODE = 32,
            WHILE_CODE = 33,
            DO_CODE = 34,
            INT_CODE = 35,
            FLOAT_CODE = 36,
            SWITCH_CODE = 37;


    Front(String fileName) throws FileNotFoundException {
        file = new File(fileName);
        input = new Scanner(file);
    }


    public void addChar() {

        lexeme[counter] = nextChar;

    }

    public Boolean validKeyword(){
        for (String i : keyword){
            if (nextChar.equals(i)) {
                return true;
            }
        }
        return false;
    }


    public void getChar() {
        nextChar = input.next();
        if (validKeyword()){
            charClass = unknown;
        }
        else {
            if (Character.isLetter(nextChar.charAt(0)))
                charClass = letter;
            else if (Character.isDigit(nextChar.charAt(0)))
                charClass = digit;
            else
                charClass = unknown;
        }

    }

    public void lex() {

        switch (charClass) {
            case letter:
                addChar();
                if (input.hasNext())
                    getChar();
                nextToken = IDENT;
                break;

            case digit:
                addChar();
                if (input.hasNext())
                    getChar();
                nextToken = INT_LIST;
                break;

            case unknown:
                lookup(nextChar);
                if (input.hasNext())
                    getChar();
                break;

            case EOF:
                nextToken = EOF;
                lexeme[counter] = "EOF";
                break;
        }

        System.out.println("Next token is " + nextToken + ", Next lexeme is " + lexeme[counter]);
        counter++;

    }

    public void lookup(String ch) {
        switch (ch) {
            case "(":
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ")":
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case "+":
                addChar();
                nextToken = ADD_OP;
                break;
            case "-":
                addChar();
                nextToken = SUB_OP;
                break;
            case "*":
                addChar();
                nextToken = MULT_OP;
                break;
            case "/":
                addChar();
                nextToken = DIV_OP;
                break;
            default:
                specialLookUp(nextChar);
                if (input.hasNext())
                    getChar();
                break;
        }
    }

    public void specialLookUp(String string) {
        switch (string) {
            case "for":
                addChar();
                nextToken = FOR_CODE;
                break;
            case "if":
                addChar();
                nextToken = IF_CODE;
                break;
            case "else":
                addChar();
                nextToken = ELSE_CODE;
                break;
            case "while":
                addChar();
                nextToken = WHILE_CODE;
                break;
            case "do":
                addChar();
                nextToken = DO_CODE;
                break;
            case "int":
                addChar();
                nextToken = INT_CODE;
                break;
            case "float":
                addChar();
                nextToken = FLOAT_CODE;
                break;
            case "switch":
                addChar();
                nextToken = SWITCH_CODE;
                break;
            default:
                addChar();
                nextToken = EOF;
                break;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        Front lex = new Front("/Users/Lexical analyzer/expression.txt");


        lex.getChar();
        while (lex.input.hasNext()) {
            lex.lex();
        }
        lex.lex();


    }
}


