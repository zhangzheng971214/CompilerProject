package main;
import main.*;

import java.util.*;

//TODO:method结点需要与类结点分开实现，因为method中需要单独处理返回类型以及形参列表
public class methodNode extends Symbol implements Scope{
    private String returnType;
    private Scope upperScope;
    public Map<String, Symbol> paraTable = new HashMap<String, Symbol>(); //保存形参中的符号表
    public Map<String, Symbol> varTable = new HashMap<String, Symbol>(); //保存局部变量符号表
    private boolean valid = true; //TODO:CHECK!

    public methodNode(String name, String returnType, Scope upper, boolean valid){
        super(name);
        this.returnType = returnType;
        this.upperScope = upper;
        this.valid = valid;
    }
    public methodNode(String name, String returnType, Scope upper){
        super(name);
        this.returnType = returnType;
        this.upperScope = upper;
    }

    //重写Scope接口中的函数，大致与classNode类似
    public String getName(){
        return super.getName();
    }

    public Scope getNode() {
        return this; //将当前类直接作为Scope类型返回，因为this类已经完整实现了Scope所有接口
    }

    public Scope getUpperScope() {
        return upperScope;
    }

    public void addSymbol(Symbol sym){ //变量声明中的符号加入至varTable中
        varTable.put(sym.getName(), sym);
    }

    public boolean isValid(){
        return valid;
    }

    public Symbol findLocalSym(String name) { //遍历当前结点符号表，根据name，找到symbol对象
        //method结点中找符号需要遍历参数表和变量表
        if(paraTable.containsKey(name))
            return paraTable.get(name);
        return varTable.get(name); //参数表中没有就从var表中查找
    }

    public Symbol findWholeSym(String name){
        //对于method而言，没有parent的说法，所以也返回findLocalSym()的结果即可
        return findLocalSym(name);
    }

    //For Test
    public Map<String, Symbol> getSym() {
        return varTable;
    }

    //methodNode中用于特别处理的函数
    public void addPara(Symbol sym){
        //添加新的参数符号到参数表中
        paraTable.put(sym.getName(), sym);
    }
    public String getReturnType(){
        return returnType;
    }
}