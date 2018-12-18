package main;

import main.gen.MiniJavaBaseListener;
import main.gen.MiniJavaParser;

import java.util.Map;

//在已有符号表的基础上，对所有的符号是否定义进行检查
public class SymbolChecker extends MiniJavaBaseListener {
    private Map<String, ClassNode> classNodes; //保存AST中所有的结点对象
    private Scope current; //记录当前处理的作用域，可为class也可为method
    private ExceptionHandler exceptionHandler;

    public SymbolChecker(Map<String, ClassNode> classNodes, Scope scope, ExceptionHandler exceptionHandler) { //构造函数
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
        //进入作用域
        String nodeName = ctx.name.getText();
        current = classNodes.get(nodeName); //当前处理作用域设为mainClass的Scope
    }

    @Override
    public void exitMainClass(MiniJavaParser.MainClassContext ctx) {
        exitScope();
    }

    @Override
    public void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        //进入作用域
        String nodeName = ctx.name.getText();
        current = classNodes.get(nodeName); //当前处理作用域设为Class的Scope

        //<循环继承>：此处在类声明时检查是否有循环继承
        if (ctx.parent != null) {
            //System.out.println("test: "+nodeName);
            //TODO:Check Code!!!
            //不断往上找parent，如果找到自己，则报错；如果找到空parent,则跳出
            String superClass = ctx.parent.getText();
            for (int i = 0; i < classNodes.size(); i++) {
                //System.out.println("parent is "+ classNodes.get(parent).getName());
                if (superClass.equals(nodeName)) { //TODO:Error还没解决
                    exceptionHandler.addException(ctx.name, nodeName + "循环继承");
                    exceptionHandler.checkException();
                    break;
                }
                if (classNodes.get(superClass).hasParent()) { //TODO:怎么判断no parent
                    //System.out.println(parent + " has parent.");
                    ClassNode p = classNodes.get(superClass);
                    //System.out.println(p.getText());
                    superClass = p.getSuperClass();
                } else
                    break;
            }
        }
    }

    @Override
    public void exitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        exitScope();
    }

    @Override
    public void enterVarDeclaration(MiniJavaParser.VarDeclarationContext ctx) {
        //进行类型定义的检查
        String varType = ctx.vtype.getText();
        //<类型定义检查>：检查变量声明的类型是否已定义
        if (varType.equals("int")
                || varType.equals("int[]")
                || varType.equals("boolean")
                || classNodes.containsKey(varType)) ;
        else { //未找到type
            exceptionHandler.addException(ctx.vtype.getStart(), "变量类型" + varType + "不存在"); //TODO:能不能弹出type
        }
    }

    @Override
    public void enterMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        //进入作用域
        String methodName = ctx.name.getText();
        String returnType = ctx.rtype.getText();
        //TODO:CHECK!!!
        current = (MethodNode) current.findLocalSym(methodName); //current作用域中找到method结点，返回出来
        //<类型定义检查>：检查method返回类型是否已定义
        if (returnType.equals("int")
                || returnType.equals("int[]")
                || returnType.equals("boolean")
                || classNodes.containsKey(returnType)) ;
        else { //未找到type
            exceptionHandler.addException(ctx.rtype.getStart(), "返回类型" + returnType + "不存在"); //TODO:能不能弹出type
        }
    }

    @Override
    public void exitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        exitScope();
    }

    @Override
    public void enterFormalParameters(MiniJavaParser.FormalParametersContext ctx) {
        //检查类型是否定义
        String paraType = ctx.ptype.getText();

        //<类型定义检查>：检查形参中的类型是否已定义
        //TODO:Check it!!!
        if (paraType.equals("int")
                || paraType.equals("int[]")
                || paraType.equals("boolean")
                || classNodes.containsKey(paraType)) ;
        else { //未找到type
            exceptionHandler.addException(ctx.ptype.getStart(), "形参类型" + paraType + "不存在"); //TODO:能不能弹出type
        }
    }

    @Override
    public void enterAssignStmt(MiniJavaParser.AssignStmtContext ctx) {
        //<symbol声明>：检查使用的symbol是否声明过
        //赋值语句，在当前作用域及父类作用域中找符号
        String assignName = ctx.assignName.getText();
        if (current.findWholeSym(assignName) == null) { //查找整个作用域，包括parent
            exceptionHandler.addException(ctx.assignName, "符号" + assignName + "未定义");
        }
    }

    @Override
    public void enterArrayAssignStmt(MiniJavaParser.ArrayAssignStmtContext ctx) {
        //<symbol声明>：检查使用的symbol是否声明过
        //赋值语句，在当前作用域及父类作用域中找符号
        String aAssignName = ctx.aAssignName.getText();
        if (current.findWholeSym(aAssignName) == null) { //查找整个作用域，包括parent
            exceptionHandler.addException(ctx.aAssignName, "符号" + aAssignName + "未定义");
        }
    }

    @Override
    public void enterIdExpr(MiniJavaParser.IdExprContext ctx) {
        //<symbol声明>：检查使用的symbol是否声明过
        //表达式中的符号，在当前作用域及父类作用域中找符号
        String idName = ctx.idName.getText();
        if (current.findWholeSym(idName) == null) { //查找整个作用域，包括parent
            exceptionHandler.addException(ctx.idName, "符号" + idName + "未定义");
        }
    }

    @Override
    public void enterNewIdExpr(MiniJavaParser.NewIdExprContext ctx) {
        //<symbol声明>：检查使用的symbol是否声明过
        //new表达式的符号，符号应该为类名之一
        String newName = ctx.newName.getText();
        if (!classNodes.containsKey(newName)) { //classNodes中没有newName时报错
            exceptionHandler.addException(ctx.newName, "符号" + newName + "未定义");
        }
    }

    @Override
    public void enterCallExpr(MiniJavaParser.CallExprContext ctx) {
        //<symbol声明>：检查使用的symbol是否声明过
        //call表达式的符号，符号应该为某个类定义的变量或者方法名
        //TODO:不需要考虑属不属于某某类吗？
        String callName = ctx.callName.getText();
        boolean isDefined = false;
        for (String key : classNodes.keySet()) {
            //遍历每个类的符号表
            if (classNodes.get(key).findWholeSym(callName) != null) { //此处查找局部的符号就够了，因为每个类都要遍历到
                isDefined = true;
                break; //找到定义时退出
            }
        }
        if (!isDefined) {
            exceptionHandler.addException(ctx.callName, "符号" + callName + "未定义");
        }
    }

}
