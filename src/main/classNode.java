package main;

import com.sun.istack.internal.NotNull;

import java.util.*;

//定义抽象语法树中的每个结点类，实际上就是每个规则rule所对应的类，其中维护了结点
//中的符号表信息
public class classNode extends Symbol implements Scope {  //可将结点也看作上一层结点作用域内的一个符号，
    // 结点中的scope通过继承Scope来实现
    private classNode parent;
    private Scope upperScope;
    public Map<String, Symbol> symTable = new HashMap<String, Symbol>(); //当前结点作用域中的符号表
    private boolean valid = true; //标明当前结点是否valid, 默认true，TODO：这种默认参数值的用法
    private boolean hasParent; //用于区分有无parent

    //construction func
    public classNode(String name, classNode parent, Scope upper, boolean valid) { //带parent结点的构造函数
        super(name);
        this.parent = parent;
        this.upperScope = upper;
        this.valid = valid;
        this.hasParent = true;
    }

    public classNode(String name, classNode parentNode, Scope upper) {
        super(name);
        this.parent = parentNode;
        this.upperScope = upper;
        this.hasParent = true;
    }

    public classNode(String name, @NotNull String noParentMsg, Scope upper, boolean valid) { //没有parent结点的构造函数
        super(name);
        this.upperScope = upper;
        this.valid = valid;
        this.hasParent = false;
    }

    public classNode(String name, @NotNull String noParentMsg, Scope upper) { //没有parent结点的构造函数
        super(name);
        this.upperScope = upper;
        this.hasParent = false;
    }

    public classNode getParent() {
        return parent;
    }

    public boolean hasParent(){
        return hasParent;
    }

    //实现scope中的接口函数
    public String getName() { //TODO:子类中这个方法是否可以不用实现？ ---不行，作为scope接口，current直接声明为scope类型
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

    public Symbol findSymbol(String name) { //遍历当前结点符号表，根据name，找到symbol对象
        return symTable.get(name);
    }

    //For Test
    public Map<String, Symbol> getSym() {
        return symTable;
    }
}
