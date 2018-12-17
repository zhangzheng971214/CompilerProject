package main;
import com.sun.istack.internal.NotNull;

import java.util.*;

//定义抽象语法树中的每个结点类，实际上就是每个规则rule所对应的类，其中维护了结点
//中的符号表信息
public class Node extends Symbol implements Scope{  //可将结点也看作上一层结点作用域内的一个符号，
                                                    // 结点中的scope通过继承Scope来实现
    private Node parentNode;
    private Map<String, Symbol> symTable = new HashMap<String, Symbol>(); //当前结点作用域中的符号表
    private Scope scope;//TODO:直接保存自己的scope？
    private boolean valid = true; //标明当前结点是否valid, 默认true，TODO：这种默认参数值的用法
    private boolean hasParent = true;
    //construction func
    public Node(String name, Node parentNode, boolean valid){ //带parent结点的构造函数
        super(name);
        this.parentNode = parentNode;
        this.valid = valid;
    }
    public Node(String name, Node parentNode){
        super(name);
        this.parentNode = parentNode;
    }
    public Node(String name, @NotNull String noParentMsg, boolean valid){ //没有parent结点的构造函数
        super(name);
        this.valid = valid;
        hasParent = false;
    }
    public Node(String name, @NotNull String noParentMsg){ //没有parent结点的构造函数
        super(name);
        hasParent = false;
    }

    //实现scope中的接口函数
    @Override
    public String getName(){ //TODO:scope中是否需要getName()操作
        return super.getName();
    }

    public Scope getScope() {
        return scope;
    }

    public Scope getParentScope() {
        return parentNode.scope;
    }

    public void addSymbol(Symbol sym){
        symTable.put(sym.getName(), sym);
    }

    public boolean isValid(){
        return valid;
    }

    public Symbol findSymbol(String name) { //遍历当前结点符号表，根据name，找到symbol对象
        return symTable.get(name);
    }
}
