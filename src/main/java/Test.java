package com.antlr.out;

import com.antlr.out.MiniJavaLexer;
import com.antlr.out.MiniJavaParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class Test {
    public static void main(String[] args) throws Exception{
        String stat = "class test{\n" +
                "    public static void main(String [] args){\n" +
                "        a = 4+5;\n" +
                "    }\n" +
                "}";

        ANTLRInputStream in = new ANTLRInputStream(stat);

        MiniJavaLexer lexer = new MiniJavaLexer(in);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MiniJavaParser parser = new MiniJavaParser(tokens);

        ParseTree tree = parser.goal();

        System.out.println(tree.toStringTree(parser));
    }
}
