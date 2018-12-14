package com.antlr.out;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Test {
    public static void main(String[] args) throws Exception {
        String stat = "class T T{ public static void main(String [] args){ int a; } }";

        ANTLRInputStream in = new ANTLRInputStream(stat);

        MiniJavaLexer lexer = new MiniJavaLexer(in);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MiniJavaParser parser = new MiniJavaParser(tokens);
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new TestErrorListener()); // add ours
        ParseTree tree = parser.goal();

        System.out.println(tree.toStringTree(parser));
    }
}
/*
public class Test {
    public static void main(String[] args) {
        int a = 3-4*5;
    }
}

 */
