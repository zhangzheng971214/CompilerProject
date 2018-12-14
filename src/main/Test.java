package main;

import main.gen.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.*;

public class Test {
    public static void main(String[] args) throws Exception {
        String stat = "class T { public static void main(String [] args){ System.main.println(1); } }";

        ANTLRInputStream in = new ANTLRInputStream(stat);

        MiniJavaLexer lexer = new MiniJavaLexer(in);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MiniJavaParser parser = new MiniJavaParser(tokens);

        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new TestErrorListener()); // add ours
        parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION); // make the parser report all ambiguities

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
