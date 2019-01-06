package main;

import main.gen.*;
import org.antlr.v4.gui.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Main {
    public static Map<String, ClassNode> classes = new HashMap<String, ClassNode>();
    public static Scope SuperScope = new ClassNode("<Super Scope>", "<No Parent Class>", null, true);

    public static void main(String[] args) throws Exception {

        //String stat = readFromFile("src\\test\\factorial.java"); //For Windows
        //ANTLRInputStream in = new ANTLRInputStream(stat);
        ANTLRInputStream in = new ANTLRInputStream(System.in);

        MiniJavaLexer lexer = new MiniJavaLexer(in);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MiniJavaParser parser = new MiniJavaParser(tokens);

        ExceptionHandler exceptionHandler = new ExceptionHandler(in.toString());

        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new SyntaxErrorListener(exceptionHandler)); // add ours
        // begin parsing, try to build AST meanwhile check 词法 and 语法
        ParseTree tree = parser.goal();
        exceptionHandler.checkExceptionNoExit();
        showAST(parser, tree);


        //测试语义分析中的scopeChecker
        ParseTreeWalker walker = new ParseTreeWalker();
        if(!exceptionHandler.hasException()) {
            ScopeChecker ScopeChecker = new ScopeChecker(classes, SuperScope, exceptionHandler);
            walker.walk(ScopeChecker, tree);
            exceptionHandler.checkExceptionNoExit();
        }

        //测试语义分析中的symbolChecker
        if(!exceptionHandler.hasException()) {
            SymbolChecker SymbolChecker = new SymbolChecker(classes, SuperScope, exceptionHandler);
            walker.walk(SymbolChecker, tree);
            exceptionHandler.checkExceptionNoExit();
        }

        if(!exceptionHandler.hasException()) {
            String str = args[0];
            if (str.equalsIgnoreCase("-Visitor")) {
                System.out.println();
                System.out.println();
                MiniJavaVisitor visitor = new MiniJavaVisitor();
                visitor.visit(tree);
            }
        }
    }

    public static void showAST(MiniJavaParser parser, ParseTree tree) {
        //show AST in console
        System.out.println(tree.toStringTree(parser));

        //show AST in GUI
        TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
        JFrame frame = new JFrame("MiniJava AST");
        viewer.setScale(1.5);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 500, 500);
        scrollPane.getViewport().add(viewer);
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, 800);
        frame.setVisible(true);
    }
}
