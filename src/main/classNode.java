package main;

import com.sun.istack.internal.NotNull;

import java.util.*;

//定义抽象语法树中的每个结点类，实际上就是每个规则rule所对应的类，其中维护了结点
//中的符号表信息
public class classNode extends Symbol implements Scope {  //可将结点也看作上一层结点作用域内的一个符号，
    // 结点中的scope通过继承Scope来实现
    private String superClass; //记录父类的名称
    private Scope upperScope;
    public Map<String, Symbol> symTable = new HashMap<String, Symbol>(); //当前结点作用域中的符号表
    private boolean valid = true; //标明当前结点是否valid, 默认true，TODO：这种默认参数值的用法
    private boolean hasParent; //用于区分有无parent

    //construction func
    public classNode(String name, String superClass, Scope upper, boolean valid) { //带parent结点的构造函数
        super(name);
        this.superClass = superClass;
        this.upperScope = upper;
        this.valid = valid;
        this.hasParent = true;
    }

    public classNode(String name, String superClass, Scope upper) {
        super(name);
        this.superClass = superClass;
        this.upperScope = upper;
        this.hasParent = true;
    }

    public String getSuperClass() {
        return this.superClass;
    }

    public boolean hasParent(){
        return hasParent;
    }

    //实现scope中的接口函数
    public String getText() { //TODO:子类中这个方法是否可以不用实现？ ---不行，作为scope接口，current直接声明为scope类型
        return super.getName();
    }

    public Scope getNode() {
        return this; //将当前类直接作为Scope类型返回，因为this类已经完整实现了Scope所有接口
    }

    public Scope getUpperScope() {
        return upperScope;
    }

    public void addSymbol(Symbol sym) {
        symTable.put(sym.getName(), sym);
    }

    public boolean isValid() {
        return valid;
    }

    public Symbol findLocalSym(String name) {
        return symTable.get(name);
    }

    public Symbol findWholeSym(String name){
        //在当前作用域以及上一级的作用域中查找
        Scope upper;
        upper = getUpperScope();
        if(symTable.containsKey(name)) //在当前符号表中查找
            return symTable.get(name);
        else if(!upper.getText().equals("<Super Scope>"))//未找到则需要递归考察upper作用域中能否找到
            return upper.findWholeSym(name);
        //upper作用域也没有的话，则说明此符号无有效声明，返回null
        return null;
    }

    //For Test
    public Map<String, Symbol> getSym() {
        return symTable;
    }
}
