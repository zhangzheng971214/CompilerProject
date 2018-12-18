package main;

import java.util.*;

/**
 * 定义抽象语法树中的每个结点类，实际上就是每个规则rule所对应的类，其中维护了结点
 * 中的符号表信息
 */
public class ClassNode extends Node {  //可将结点也看作上一层结点作用域内的一个符号，
    private String superClass; //记录父类的名称
    private Map<String, Symbol> symTable = new HashMap<String, Symbol>(); //当前结点作用域中的符号表
    private boolean hasParent; //用于区分有无parent

    public ClassNode(String name, String superClass, Scope upper, boolean valid) { //带parent结点的构造函数
        super(name, upper, valid);
        this.superClass = superClass;
        this.hasParent = true;
    }

    public ClassNode(String name, String superClass, Scope upper) {
        super(name, upper);
        this.superClass = superClass;
        this.hasParent = true;
    }

    public void addSymbol(Symbol sym) {
        symTable.put(sym.getName(), sym);
    }

    public Symbol findLocalSym(String name) {
        return symTable.get(name);
    }

    public Symbol findWholeSym(String name) {
        Scope upper;
        upper = getUpperScope();
        if (symTable.containsKey(name)) //在当前符号表中查找
            return symTable.get(name);
        else if (!upper.getText().equals("<Super Scope>"))//未找到则需要递归考察upper作用域中能否找到
            return upper.findWholeSym(name);
        else                    //upper作用域也没有的话，则说明此符号无有效声明，返回null
            return null;
    }

    //For Test
    public Map<String, Symbol> getSym() {
        return symTable;
    }

    public String getSuperClass() {
        return this.superClass;
    }

    public boolean hasParent() {
        return hasParent;
    }

}
