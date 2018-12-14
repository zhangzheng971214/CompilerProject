package com.antlr.out;

import com.antlr.out.MiniJavaLexer;
import com.antlr.out.MiniJavaParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class Test {
    public static void main(String[] args) throws Exception {
        String stat = "class T T{ public static void main(String [] args){ int a; } }";

        ANTLRInputStream in = new ANTLRInputStream(stat);

        MiniJavaLexer lexer = new MiniJavaLexer(in);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MiniJavaParser parser = new MiniJavaParser(tokens);
        //parser.removeErrorListeners(); // remove ConsoleErrorListener
        //parser.addErrorListener(new VerboseListener()); // add ours
        ParseTree tree = parser.goal();

        System.out.println(tree.toStringTree(parser));
    }

    public static class VerboseListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine, String msg,
                                RecognitionException e) {
            List<String> stack = ((Parser) recognizer).getRuleInvocationStack();
            Collections.reverse(stack);
            System.err.println("rule stack: " + stack);
            System.err.println("line " + line + ":" + charPositionInLine + " at " +
                    offendingSymbol + ": " + msg);
        }
    }

}

/*
public class Test {
    public static void main(String[] args) {
        int a = 3-4*5;
    }
}

 */
