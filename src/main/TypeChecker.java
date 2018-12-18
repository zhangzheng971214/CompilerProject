package main;

import main.gen.*;

import java.util.*;

public class TypeChecker extends MiniJavaBaseListener {
    private Map<String, ClassNode> classNodes; //保存AST中所有的结点对象
    private Scope current; //记录当前处理的作用域，可为class也可为method
    private ExceptionHandler exceptionHandler;

    public TypeChecker(Map<String, ClassNode> classNodes, Scope scope, ExceptionHandler exceptionHandler) { //构造函数
        this.classNodes = classNodes;
        this.current = scope; //指代goal
        this.exceptionHandler = exceptionHandler;
    }

    /* 退出当前结点作用域 */
    public void exitScope() {
        current = current.getUpperScope(); //转到其parent结点的Scope,classNode也可认为是Scope
    }

    @Override
    public void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        String nodeName = ctx.name.getText();
        current = classNodes.get(nodeName); //当前处理作用域设为mainClass的Scope
    }

    @Override
    public void exitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        exitScope();
    }

    @Override
    public void enterMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        //进入作用域
        String methodName = ctx.name.getText();
        //TODO:CHECK!!!
        current = (MethodNode) current.findLocalSym(methodName); //current作用域中找到method结点，返回出来
    }

    @Override
    public void exitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        //TODO: check return type
        exitScope();
    }

    @Override
    public void enterIfStmt(MiniJavaParser.IfStmtContext ctx) {
        String condition=ctx.condition.getText();
        if(!condition.equals("true")&&!condition.equals("false")){
            exceptionHandler.addException(ctx.getStart(),"If condition type error!");
        }
    }

}
