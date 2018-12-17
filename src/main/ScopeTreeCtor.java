package main;
import main.gen.MiniJavaBaseListener;
import main.gen.MiniJavaParser;

import java.util.*;

//Constructor of Scope Tree, including Scopes and Symbol Tables
public class ScopeTreeCtor extends MiniJavaBaseListener{ //建立每一个rule的作用域树，并保存好每个结点的符号表，同时初步检查作用域的语义错误
    private Map<String, Node> Nodes = new HashMap<String, Node>(); //保存AST中所有的结点对象
    private Node currentNode; //记录当前处理的结点

    public ScopeTreeCtor(Map<String, Node> Nodes, Node node){ //构造函数
        this.Nodes = Nodes;
        this.currentNode = node; //指代goal
    }

    public void exitScope(){
        //退出当前结点作用域
        currentNode = currentNode.getParent(); //取得其parent结点
    }

    //重写MiniJavaBaseListener中的enter方法
    @Override
    public void enterMainClass(MiniJavaParser.MainClassContext ctx){
        //将此结点添加至Nodes中,并加入符号表
        String nodeName = ctx.name.getText();
        Node mainClass = new Node(nodeName, currentNode); //建立Node类，goal是没有parent的
        Nodes.put(nodeName, mainClass); //结点加入到Nodes

        currentNode.addSymbol(mainClass); //此结点作为符号加入到current符号表中
        currentNode = mainClass; //当前处理结点设为mainClass
    }

    @Override
    public void exitMainClass(MiniJavaParser.MainClassContext ctx){exitScope();}

    @Override
    public void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx){
        //将此结点添加至Nodes中
        //TODO:考察parent结点是否是current结点
        String nodeName = ctx.name.getText();
        boolean valid = currentNode.isValid();
        //String parentName = ctx.parent!=null ? ctx.parent.getText() : "<No Parent>"; //获得parent的名
        //类声明的过程中需要考察类是否重复定义
        if(Nodes.containsKey(nodeName)) {
            System.out.println("类名重复定义");//TODO:错误输出
            valid = false;
        }
        Node classDeclaration = new Node(nodeName, currentNode);
        if(valid) { //TODO:需不需要考察valid？
            currentNode.addSymbol(classDeclaration);
            Nodes.put(nodeName, classDeclaration);
        }
        currentNode = classDeclaration;
    }

}
