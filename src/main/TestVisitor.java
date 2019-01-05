package main;

import main.gen.MiniJavaBaseListener;
import main.gen.MiniJavaParser;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.security.PublicKey;
import java.util.Map;

//在已有符号表的基础上，对所有的符号是否定义进行检查
public class TestVisitor extends MiniJavaBaseListener {
    private Map<String, ClassNode> classNodes; //保存AST中所有的结点对象
    private Scope current; //记录当前处理的作用域，可为class也可为method
    private ExceptionHandler exceptionHandler;

    ParseTreeProperty<Integer> values = new ParseTreeProperty<Integer>();

    public TestVisitor(Map<String, ClassNode> classNodes, Scope scope, ExceptionHandler exceptionHandler){
        this.classNodes = classNodes;
        this.current = scope; //指代goal
        this.exceptionHandler = exceptionHandler;
    }

    //重写MiniJavaBaseListener中的enter方法
    @Override
    public void exitPrintStmt(MiniJavaParser.PrintStmtContext ctx) {
        int value = values.get(ctx.expression());
        System.out.println();
        System.out.println();
        System.out.println(value); //TODO:测试输出
    }

    @Override
    public void exitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        //TODO:gaixie
        String id = ctx.name.getText();
        int v = values.get(ctx.expression());
        values.put(ctx, v);
    }

    @Override
    public void exitAssignStmt(MiniJavaParser.AssignStmtContext ctx) {
        //<symbol声明>：检查使用的symbol是否声明过
        //赋值语句，在当前作用域及父类作用域中找符号
        String id = ctx.assignName.getText();
            System.out.println("111111");
            int v = values.get(ctx.expression()); // 获取等号右边的值
            values.put(ctx, v);

    }

    @Override
    public void exitIdExpr(MiniJavaParser.IdExprContext ctx) {
        //TODO:注意是exit
        String id = ctx.idName.getText();

            values.get(ctx);

    }

    @Override
    public void exitIntExpr(MiniJavaParser.IntExprContext ctx) {
        values.put(ctx, Integer.valueOf(ctx.INT().getText()));
    }

    @Override
    public void enterNewIdExpr(MiniJavaParser.NewIdExprContext ctx) {
        //<symbol声明>：检查使用的symbol是否声明过
        //new表达式的符号，符号应该为类名之一

    }

    @Override
    public void enterCallExpr(MiniJavaParser.CallExprContext ctx) {
        //<symbol声明>：检查使用的symbol是否声明过
        //call表达式的符号，符号应该为某个类定义的变量或者方法名
        //TODO:不需要考虑属不属于某某类吗？

    }

}
