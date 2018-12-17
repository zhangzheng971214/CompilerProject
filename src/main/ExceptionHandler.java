package main;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class ExceptionHandler {

    private int exceptionNum;

    ExceptionHandler() {
        exceptionNum = 0;
    }

    public void printException(String txt) {
        exceptionNum++;
        System.err.println(txt);
    }

    public int addException() {
        return ++exceptionNum;
    }

    public int addException(Token offendingToken, String msg) {
        exceptionNum++;
        System.err.println("line " + offendingToken.getLine() + ":" + offendingToken.getCharPositionInLine() + " error: " + msg);
        underlineError(offendingToken);
        return exceptionNum;
    }

    public void checkException() {
        if (exceptionNum != 0) {
            System.err.println(exceptionNum + " exceptions found!");
            System.err.println("Program will exit!");
            System.exit(-1);
        }
    }

    private void underlineError(Token offendingToken) {
        //RecognitionException recognitionException=new RecognitionException();
        //Recognizer recognizer=recognitionException.getRecognizer();
        //CommonTokenStream tokens = (CommonTokenStream) recognizer.getInputStream();
        //String input = tokens.getTokenSource().getInputStream().toString();
        //String[] lines = input.split("\n");
        //String errorLine = lines[line - 1];

        String errorLine = Main.inputLines[offendingToken.getLine() - 1];
        System.err.println(errorLine);
        for (int i = 0; i < offendingToken.getCharPositionInLine(); i++)
            System.err.print(" ");
        int start = offendingToken.getStartIndex();
        int stop = offendingToken.getStopIndex();
        if (start >= 0 && stop >= 0) {
            for (int i = start; i <= stop; i++)
                System.err.print("^");
        }
        System.err.println();
    }

}
