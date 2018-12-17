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
        exceptionNum++;
        return exceptionNum;
    }

    public void checkException(){
        if(exceptionNum!=0){
            System.err.println(exceptionNum + " exceptions found!");
            System.err.println("Program will exit!");
            System.exit(-1);
        }
    }
}
