package main;

import main.gen.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.gui.*;
import org.antlr.v4.runtime.tree.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;

public class Main {
    public static Map<String, classNode> classes = new HashMap<String, classNode>();
    public static Scope virtualSuperScope = new classNode("<Virtual Super Scope>", "<No Parent Class>", null, true);

    public static void main(String[] args) throws Exception {
        String stat = "class T{public static void main(String [] args){ System.out.println(1); } }";
        stat = "class BinarySearch{\n" +
                "    public static void main(String[] a){\n" +
                "\tSystem.out.println(new BS().Start(20));\n" +
                "    }\n" +
                "}\n" +
                "// This class contains an array of integers and\n" +
                "// methods to initialize, print and search the array\n" +
                "// using Binary Search\n" +
                "\n" +
                "class BS{\n" +
                "    int[] number ;\n" +
                "    int size ;\n" +
                "\n" +
                "    // Invoke methods to initialize, print and search\n" +
                "    // for elements on the array\n" +
                "    public int Start(int sz){\n" +
                "\tint aux01 ;\n" +
                "\tint aux02 ;\n" +
                "\taux01 = this.Init(sz);\n" +
                "\taux02 = this.Print();\n" +
                "\tif (this.Search(8)) System.out.println(1) ;\n" +
                "\telse System.out.println(0) ;\n" +
                "\tif (this.Search(19)) System.out.println(1) ;\n" +
                "\telse System.out.println(0) ;\n" +
                "\tif (this.Search(20)) System.out.println(1) ;\n" +
                "\telse System.out.println(0) ;\n" +
                "\tif (this.Search(21)) System.out.println(1) ;\n" +
                "\telse System.out.println(0) ;\n" +
                "\tif (this.Search(37)) System.out.println(1) ;\n" +
                "\telse System.out.println(0) ;\n" +
                "\tif (this.Search(38)) System.out.println(1) ;\n" +
                "\telse System.out.println(0) ;\n" +
                "\tif (this.Search(39)) System.out.println(1) ;\n" +
                "\telse System.out.println(0) ;\n" +
                "\tif (this.Search(50)) System.out.println(1) ;\n" +
                "\telse System.out.println(0) ;\n" +
                "\n" +
                "\treturn 999 ;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    // Search for a specific value (num) using\n" +
                "    // binary search\n" +
                "    public boolean Search(int num){\n" +
                "\tboolean bs01 ;\n" +
                "\tint right ;\n" +
                "\tint left ;\n" +
                "\tboolean var_cont ;\n" +
                "\tint medium ;\n" +
                "\tint aux01 ;\n" +
                "\tint nt ;\n" +
                "\n" +
                "\taux01 = 0 ;\n" +
                "\tbs01 = false ;\n" +
                "\tright = number.length ;\n" +
                "\tright = right - 1 ;\n" +
                "\tleft = 0 ;\n" +
                "\tvar_cont = true ;\n" +
                "\twhile (var_cont){\n" +
                "\t    medium = left + right ;\n" +
                "\t    medium = this.Div(medium);\n" +
                "\t    aux01 = number[medium] ;\n" +
                "\t    if (num < aux01) right = medium - 1 ;\n" +
                "\t    else left = medium + 1 ;\n" +
                "\t    if (this.Compare(aux01,num)) var_cont = false ;\n" +
                "\t    else var_cont = true ;\n" +
                "\t    if (right < left) var_cont = false ;\n" +
                "\t    else nt = 0 ;\n" +
                "\t}\n" +
                "\n" +
                "\tif (this.Compare(aux01,num)) bs01 = true ;\n" +
                "\telse bs01 = false ;\n" +
                "\treturn bs01 ;\n" +
                "    }\n" +
                "\n" +
                "    // This method computes and returns the\n" +
                "    // integer division of a number (num) by 2\n" +
                "    public int Div(int num){\n" +
                "\tint count01 ;\n" +
                "\tint count02 ;\n" +
                "\tint aux03 ;\n" +
                "\n" +
                "\tcount01 = 0 ;\n" +
                "\tcount02 = 0 ;\n" +
                "\taux03 = num - 1 ;\n" +
                "\twhile (count02 < aux03) {\n" +
                "\t    count01 = count01 + 1 ;\n" +
                "\t    count02 = count02 + 2 ;\n" +
                "\t}\n" +
                "\treturn count01 ;\t\n" +
                "    }\n" +
                "\n" +
                "    \n" +
                "    // This method compares two integers and\n" +
                "    // returns true if they are equal and false\n" +
                "    // otherwise\n" +
                "    public boolean Compare(int num1 , int num2){\n" +
                "\tboolean retval ;\n" +
                "\tint aux02 ;\n" +
                "\n" +
                "\tretval = false ;\n" +
                "\taux02 = num2 + 1 ;\n" +
                "\tif (num1 < num2) retval = false ;\n" +
                "\telse if (!(num1 < aux02)) retval = false ;\n" +
                "\telse retval = true ;\n" +
                "\treturn retval ;\n" +
                "    }\n" +
                "\n" +
                "    // Print the integer array\n" +
                "    public int Print(){\n" +
                "\tint j ;\n" +
                "\n" +
                "\tj = 1 ;\n" +
                "\twhile (j < (size)) {\n" +
                "\t    System.out.println(number[j]);\n" +
                "\t    j = j + 1 ;\n" +
                "\t}\n" +
                "\tSystem.out.println(99999);\n" +
                "\treturn 0 ;\n" +
                "    }\n" +
                "    \n" +
                "\n" +
                "    // Initialize the integer array\n" +
                "    public int Init(int sz){\n" +
                "\tint j ;\n" +
                "\tint k ;\n" +
                "\tint aux02 ;\n" +
                "\tint aux01 ;\n" +
                "\n" +
                "\tsize = sz ;\n" +
                "\tnumber = new int[sz] ;\n" +
                "\t\n" +
                "\tj = 1 ;\n" +
                "\tk = size + 1 ;\n" +
                "\twhile (j < (size)) {\n" +
                "\t    aux01 = 2 * j ;\n" +
                "\t    aux02 = k - 3 ;\n" +
                "\t    number[j] = aux01 + aux02 ;\n" +
                "\t    j = j + 1 ;\n" +
                "\t    k = k - 1 ;\n" +
                "\t}\n" +
                "\treturn 0 ;\t\n" +
                "    }\n" +
                "\n" +
                "}\n";
        ANTLRInputStream in = new ANTLRInputStream(stat);

        MiniJavaLexer lexer = new MiniJavaLexer(in);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MiniJavaParser parser = new MiniJavaParser(tokens);

        ExceptionHandler exceptionHandler = new ExceptionHandler();

        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new SyntaxErrorListener(exceptionHandler)); // add ours
        //parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION); // make the parser report all ambiguities

        // begin parsing
        ParseTree tree = parser.goal();
        exceptionHandler.checkException();


        showAST(parser, tree);

        //测试语义分析中的ScopeChecker
        ParseTreeWalker walker = new ParseTreeWalker();
        scopeChecker ScopeChecker = new scopeChecker(classes, virtualSuperScope);
        walker.walk(ScopeChecker, tree);
        for(String key:classes.keySet()){
            System.out.println("Node: " + key + ", ");
        }
        for(String key:virtualSuperScope.getSym().keySet()){
            System.out.println("Symbol: " + key + ", ");
        }
        for(String key:classes.get("BS").getSym().keySet()){
            System.out.println("BS Symbol: " + key + ", ");
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
