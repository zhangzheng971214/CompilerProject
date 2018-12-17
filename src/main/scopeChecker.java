package main;
import main.gen.MiniJavaBaseListener;
import main.gen.MiniJavaParser;

import java.util.*;

//Constructor of Scope Tree, including Scopes and Symbol Tables
public class scopeChecker extends MiniJavaBaseListener{ //建立每一个rule的作用域树，并保存好每个结点的符号表，同时初步检查作用域的语义错误
    private Map<String, classNode> classNodes = new HashMap<String, classNode>(); //保存AST中所有的结点对象
    private Scope current; //记录当前处理的作用域，可为class也可为method

    public scopeChecker(Map<String, classNode> classNodes, classNode classnode){ //构造函数
        this.classNodes = classNodes;
        this.current = classnode; //指代goal
    }

    public void exitScope(){
        //退出当前结点作用域
        current = current.getParent().getScope(); //转到其parent结点的Scope
    }

    //重写MiniJavaBaseListener中的enter方法
    @Override
    public void enterMainClass(MiniJavaParser.MainClassContext ctx){
        //将此结点添加至classNodes中,并加入符号表
        String nodeName = ctx.name.getText();
        classNode mainClass = new classNode(nodeName, "<No Parent>"); //建立Node类，goal是没有parent的
        classNodes.put(nodeName, mainClass); //结点加入到Nodes

        current.addSymbol(mainClass); //此结点作为符号加入到current符号表中
        current = mainClass; //当前处理作用域设为mainClass的Scope
    }

    @Override
    public void exitMainClass(MiniJavaParser.MainClassContext ctx){exitScope();}

    @Override
    public void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx){
        //将此结点添加至classNodes中
        //TODO:考察parent结点是否是current结点
        String nodeName = ctx.name.getText();
        boolean valid = current.isValid();
        String parentName = ctx.parent!=null ? ctx.parent.getText() : "<No Parent>"; //获得parent的名
        //类声明的过程中需要考察类是否重复定义
        if(classNodes.containsKey(nodeName)) {
            System.out.println("类名重复定义");//TODO:错误输出
            valid = false;
        }
        classNode classDeclaration = new classNode(nodeName, classNodes.get(parentName));
        if(valid) { //TODO:需不需要考察valid？
            current.addSymbol(classDeclaration);
            classNodes.put(nodeName, classDeclaration);
        }
        current = classDeclaration; //当前处理作用域设为mainClass的Scope
    }

    @Override
    public void exitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {exitScope(); }

    @Override
    public void enterVarDeclaration(MiniJavaParser.VarDeclarationContext ctx) {
        //将此结点添加至Nodes中
        String varName = ctx.name.getText();
        String varType = ctx.vtype.getText(); //对于变量声明，需要记录其symbol的名字和类型
        boolean valid = current.isValid();
        //变量的声明过程中需要检查其是否已经在当前作用域下被重复声明
        if(current.findSymbol(varName) != null){
            System.out.println("变量名重复定义");//TODO:错误输出
            valid = false;
        }
        if(valid){
            Symbol var = new Symbol(varName, varType); //新建Symbol对象
            current.addSymbol(var);
        }
    }

    @Override
    public void enterMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx){
        //考察method声明的作用域检查
        //TODO:method类的实现

    }

}
