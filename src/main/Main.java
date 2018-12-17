package main;

import main.gen.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.gui.*;
import org.antlr.v4.runtime.tree.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.*;
import java.util.*;

public class Main {
    public static Map<String, classNode> classes = new HashMap<String, classNode>();
    public static Scope virtualSuperScope = new classNode("<Virtual Super Scope>", "<No Parent Class>", null, true);

    public static void main(String[] args) throws Exception {
        //String stat = "class T{public static void main(String [] args){ System.out.println(1); } }";
        File file = new File("src\\test\\binarytree.java");//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            System.out.println(s);
        }
        bReader.close();
        String stat = sb.toString();

        ANTLRInputStream in = new ANTLRInputStream(stat);

        MiniJavaLexer lexer = new MiniJavaLexer(in);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MiniJavaParser parser = new MiniJavaParser(tokens);

        ExceptionHandler exceptionHandler = new ExceptionHandler(stat);

        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new SyntaxErrorListener(exceptionHandler)); // add ours
        //parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION); // make the parser report all ambiguities

        // begin parsing, try to build AST meanwhile check 词法 and 语法
        ParseTree tree = parser.goal();
        exceptionHandler.checkException();

        // show AST in both console and GUI
        showAST(parser, tree);

        //测试语义分析中的ScopeChecker
        ParseTreeWalker walker = new ParseTreeWalker();
        scopeChecker ScopeChecker = new scopeChecker(classes, virtualSuperScope, exceptionHandler);
        walker.walk(ScopeChecker, tree);
        for(String key:classes.keySet()){
            System.out.println("Node: " + key + ", ");
        }
        for(String key:virtualSuperScope.getSym().keySet()){
            System.out.println("Symbol: " + key + ", ");
        }
        for(String key:classes.get("Tree").getSym().keySet()){
            System.out.println("BS Symbol: " + key + ", ");
            if(key.equals("Compare")){

            }
        }
        System.out.println("Scope Check Success!"); //TODO:Check if it can work

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
