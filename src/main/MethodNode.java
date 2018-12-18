package main;

import java.util.*;

//TODO:method结点需要与类结点分开实现，因为method中需要单独处理返回类型以及形参列表
public class MethodNode extends Node {
    private String returnType;
    private Map<String, Symbol> paraTable = new HashMap<String, Symbol>(); //保存形参中的符号表
    private Map<String, Symbol> varTable = new HashMap<String, Symbol>(); //保存局部变量符号表

    public MethodNode(String name, String returnType, Scope upper, boolean valid) {
        super(name, upper, valid);
        this.returnType = returnType;
    }

    /*变量声明中的符号加入至varTable中*/
    public void addSymbol(Symbol sym) {
        varTable.put(sym.getName(), sym);
    }

    public Symbol findLocalSym(String name) { //遍历当前结点符号表，根据name，找到symbol对象
        //method结点中找符号需要遍历参数表和变量表
        if (paraTable.containsKey(name))
            return paraTable.get(name);
        else
            return varTable.get(name); //参数表中没有就从var表中查找
    }

    public Symbol findWholeSym(String name) {
        Scope upper;
        upper = getUpperScope();
        if (varTable.containsKey(name))         //在var符号表中查找
            return varTable.get(name);
        else if (paraTable.containsKey(name))   //在para符号表中查找
            return paraTable.get(name);
        else if (!upper.getText().equals("<Super Scope>"))//未找到则需要递归考察upper作用域中能否找到
            return upper.findWholeSym(name);
        else
            return null;  //upper作用域也没有的话，则说明此符号无有效声明，返回null
    }

    //For Test
    public Map<String, Symbol> getSym() {
        return varTable;
    }

    /*methodNode中用于特别处理的函数*/
    public void addPara(Symbol sym) {
        //添加新的参数符号到参数表中
        paraTable.put(sym.getName(), sym);
    }

    public String getReturnType() {
        return returnType;
    }
}