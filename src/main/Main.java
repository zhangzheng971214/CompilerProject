package main;

import main.gen.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.gui.*;
import org.antlr.v4.runtime.tree.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        String stat = "class T{public static void main(String [] args){ System.out.println(1); } }";

        ANTLRInputStream in = new ANTLRInputStream(stat);

        MiniJavaLexer lexer = new MiniJavaLexer(in);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MiniJavaParser parser = new MiniJavaParser(tokens);

        ExceptionHandler exceptionHandler = new ExceptionHandler();

        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new SyntaxErrorListener(exceptionHandler)); // add ours
        //parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION); // make the parser report all ambiguities

        // begin parsing, try to build AST meanwhile check 词法 and 语法
        ParseTree tree = parser.goal();
        exceptionHandler.checkException();

        // show AST in both console and GUI
        showAST(parser, tree);
    }

    public static void showAST(MiniJavaParser parser, ParseTree tree) {
        //show AST in console
        System.out.println(tree.toStringTree(parser));

        //show AST in GUI
        JFrame frame = new JFrame("MiniJava AST");
        JPanel panel = new JPanel();
        TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
        viewer.setScale(1.5);
        panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }
}
