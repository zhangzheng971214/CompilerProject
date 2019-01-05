package main;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class ExceptionHandler {

    private int exceptionNum;
    private String[] inputLines;

    ExceptionHandler(String input) {
        exceptionNum = 0;
        inputLines = input.split("\n");
    }

    public boolean hasException() {
        return exceptionNum > 0;
    }

    public void printException(String txt) {
        exceptionNum++;
        System.err.println(txt);
    }

    public int addException() {
        return ++exceptionNum;
    }

    public int addSemanticException(Token offendingToken, String msg) {
        exceptionNum++;
        System.err.println("line " + offendingToken.getLine() + ":" + offendingToken.getCharPositionInLine() + " " + msg);
        underlineError(offendingToken);
        return exceptionNum;
    }

    public int addException(Token offendingToken, String msg) {
        exceptionNum++;
        System.err.println("line " + offendingToken.getLine() + ":" + offendingToken.getCharPositionInLine() + " Syntax Error: " + msg); //TODO：区分词法、语法错误
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

    public void checkExceptionNoExit() {
        if (exceptionNum != 0) {
            System.err.println(exceptionNum + " exceptions found!");
            System.err.println("Program will exit!");
        }
    }

    private void underlineError(Token offendingToken) {
        String errorLine = inputLines[offendingToken.getLine() - 1];
        System.err.println(errorLine);
        for (int i = 0; i < offendingToken.getCharPositionInLine(); i++) {
            if (errorLine.charAt(i) == '\t')
                System.err.print("\t");
            else
                System.err.print(" ");
        }
        int start = offendingToken.getStartIndex();
        int stop = offendingToken.getStopIndex();
        if (start >= 0 && stop >= 0) {
            for (int i = start; i <= stop; i++)
                System.err.print("^");
        }
        System.err.println();
    }

}
