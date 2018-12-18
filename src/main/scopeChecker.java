package main;

import main.*;
import main.gen.*;

import java.util.*;

//Constructor of Scope Tree, including Scopes and Symbol Tables
public class scopeChecker extends MiniJavaBaseListener { //建立每一个rule的作用域树，并保存好每个结点的符号表，同时初步检查作用域的语义错误
    private Map<String, classNode> classNodes; //保存AST中所有的结点对象
    private Scope current; //记录当前处理的作用域，可为class也可为method
    private ExceptionHandler exceptionHandler;

    public scopeChecker(Map<String, classNode> classNodes, Scope scope, ExceptionHandler exceptionHandler) { //构造函数
        this.classNodes = classNodes;
        this.current = scope; //指代goal
        this.exceptionHandler = exceptionHandler;
    }

    public void exitScope() {
        //退出当前结点作用域
        current = current.getUpperScope(); //转到其parent结点的Scope,classNode也可认为是Scope
    }

    //重写MiniJavaBaseListener中的enter方法
    @Override
    public void enterMainClass(MiniJavaParser.MainClassContext ctx) {
        //将此结点添加至classNodes中,并加入符号表
        String nodeName = ctx.name.getText();
        classNode mainClass = new classNode(nodeName, "<No Parent>", current); //建立Node类，mianClass是没有parent
        //但是有upperScope
        classNodes.put(nodeName, mainClass); //结点加入到Nodes

        current.addSymbol(mainClass); //此结点作为符号加入到current符号表中
        current = mainClass; //当前处理作用域设为mainClass的Scope
    }

    @Override
    public void exitMainClass(MiniJavaParser.MainClassContext ctx) {
        exitScope();
    }

    @Override
    public void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        //将此结点添加至classNodes中
        //TODO:考察parent结点是否是current结点
        String nodeName = ctx.name.getText();
        boolean valid = true; //TODO:current.isValid();
        classNode classDeclaration;
        //类声明的过程中需要考察类是否重复定义
        if (classNodes.containsKey(nodeName)) {
            //System.out.println("类名重复定义");//TODO:错误输出
            exceptionHandler.addException(ctx.name, "类名重复定义");
            valid = false;
        }
        //构造class结点
        if (ctx.parent != null) {
            classDeclaration = new classNode(nodeName, classNodes.get(ctx.parent.getText()), current, valid); //upperScope默认为parent
        } else {
            classDeclaration = new classNode(nodeName, "<No Parent>", current, valid);
        }
        //结点添加到classNode中，以及作为符号加入current的作用域
        if (valid) { //TODO:需不需要考察valid？
            current.addSymbol(classDeclaration);
            classNodes.put(nodeName, classDeclaration); //添加到全局的classNodes中
        }
        current = classDeclaration; //当前处理作用域设为mainClass的Scope

        //<循环继承>：此处在类声明时检查是否有循环继承
        if (ctx.parent != null) {
            //TODO:Check Code!!!
            //不断往上找parent，如果找到自己，则报错；如果找到空parent,则跳出
            classNode parent = classNodes.get(ctx.parent.getText());
            while (true) {
                if (nodeName.equals(parent.getName())) {
                    System.out.println("循环继承！");//TODO:错误输出
                    break;
                }
                if (!parent.hasParent()) //TODO:怎么判断no parent
                    break;
                parent = parent.getParent();
            }
        }
    }

    @Override
    public void exitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        exitScope();
    }

    @Override
    public void enterVarDeclaration(MiniJavaParser.VarDeclarationContext ctx) {
        //将此结点添加至Nodes中
        String varName = ctx.name.getText();
        String varType = ctx.vtype.getText(); //对于变量声明，需要记录其symbol的名字和类型
        boolean valid = current.isValid();
        //变量的声明过程中需要检查其是否已经在当前作用域下被重复声明
        if (current.findSymbol(varName) != null) {
            //System.out.println("变量名重复定义");//TODO:错误输出
            exceptionHandler.addException(ctx.name, "变量名重复定义");
            valid = false;
        }
        if (valid) {
            Symbol var = new Symbol(varName, varType); //新建Symbol对象
            current.addSymbol(var);
        }

        //<类型定义检查>：检查变量声明的类型是否已定义
        if (varType.equals("int")
                || varType.equals("int[]")
                || varType.equals("boolean")
                || classNodes.containsKey(varType)) ;
        else { //未找到type
            exceptionHandler.addException(ctx.name, "变量类型" + varType + "不存在");
        }
    }

    @Override
    public void enterMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        //考察method声明的作用域检查，检查方法是否已被声明
        String nodeName = ctx.name.getText();
        String returnType = ctx.rtype.getText();
        boolean valid = current.isValid();

        //检查方法是否已被声明，即current符号表中是否有method同名符号
        if (current.findSymbol(nodeName) != null) {
            //System.out.println("方法名重复定义");//TODO:错误输出
            exceptionHandler.addException(ctx.name, "方法名重复定义");
            valid = false;
        }
        methodNode method = new methodNode(nodeName, returnType, current, valid);
        if (valid) {
            current.addSymbol(method);
        }
        current = method;

        //<类型定义检查>：检查method返回类型是否已定义
        if (returnType.equals("int")
                || returnType.equals("int[]")
                || returnType.equals("boolean")
                || classNodes.containsKey(returnType)) ;
        else { //未找到type
            exceptionHandler.addException(ctx.name, "返回类型" + returnType + "不存在");
        }
    }

    @Override
    public void exitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        exitScope();
    }

    @Override
    public void enterFormalParameters(MiniJavaParser.FormalParametersContext ctx) {
        //识别到形参结点时，将形参作为符号添加到method中去
        String paraName = ctx.name.getText();
        String paraType = ctx.ptype.getText();
        boolean valid = current.isValid();

        //检查形参是否重复
        if (current.getNode().findSymbol(paraName) != null) {
            System.out.println("形参重复定义");//TODO:错误输出
            exceptionHandler.addException(ctx.name, "形参重复定义");
            valid = false;
        }
        if (valid) {
            Symbol para = new Symbol(paraName, paraType);
            ((methodNode) current.getNode()).addPara(para); //addPara()方法是methodNode特有的
        }

        //<类型定义检查>：检查形参中的类型是否已定义
        //TODO:Check it!!!
        if (paraType.equals("int")
                || paraType.equals("int[]")
                || paraType.equals("boolean")
                || classNodes.containsKey(paraType)) ;
        else { //未找到type
            exceptionHandler.addException(ctx.name, "形参类型" + paraType + "不存在");
        }
    }
}
