package main;

import main.gen.MiniJavaBaseVisitor;
import main.gen.MiniJavaParser;

import javax.naming.Context;
import java.util.*;

public class MiniJavaVisitor extends MiniJavaBaseVisitor<Integer> {
    private Map<String, Integer> memory = new HashMap<String, Integer>();

    @Override
    public Integer visitGoal(MiniJavaParser.GoalContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitMainClass(MiniJavaParser.MainClassContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitVarDeclaration(MiniJavaParser.VarDeclarationContext ctx) {
        String id = ctx.name.getText();
        int value = 0;
        memory.put(id, value);
        return visitChildren(ctx);
    }

    @Override
    public Integer visitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        //对于函数，将其函数名和返回值加入到map中
        String id = ctx.name.getText();
        //int a = visitChildren(ctx);
        int a = -1;
        //ListIterator<MiniJavaParser.StatementContext> i = ctx.statement().listIterator();
        /*
        for (int j = 0; j < ctx.statement().size(); j++){
            System.out.println("j="+j);
            //a = visit(ctx.statement(j));
            System.out.println("a="+a);
        }
        */
        int value = visit(ctx.expression());
        System.out.println("Test return: " + value);
        memory.put(id, value);
        return visitChildren(ctx); //访问函数只需要得到其返回值
    }


    @Override
    public Integer visitFormalParameters(MiniJavaParser.FormalParametersContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitNestedStmt(MiniJavaParser.NestedStmtContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitIfStmt(MiniJavaParser.IfStmtContext ctx) {
        /*
        int value = visit(ctx.expression());
        if(value > 0){ //TODO:bool类型
            return visit(ctx.statement(0));
        }

        return visit(ctx.statement(1));*/
        return visitChildren(ctx);
    }

    @Override
    public Integer visitWhileStmt(MiniJavaParser.WhileStmtContext ctx) {
        int value = visit(ctx.expression());
        while(value > 0) //TODO:while的问题
            return visit(ctx.statement());
        return 0;
    }

    @Override
    public Integer visitPrintStmt(MiniJavaParser.PrintStmtContext ctx) {
        //Minijava代码中的println语句
        Integer value = visit(ctx.expression());
        System.out.println();
        System.out.println();
        System.out.println(value); //TODO:测试输出
        return visitChildren(ctx);
    }

    @Override
    public Integer visitAssignStmt(MiniJavaParser.AssignStmtContext ctx) {
        String id = ctx.assignName.getText();
        int value = visit(ctx.expression());
        System.out.println(id+" = "+value);
        memory.put(id, value);
        return visitChildren(ctx);
    }

    @Override
    public Integer visitArrayAssignStmt(MiniJavaParser.ArrayAssignStmtContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitThisExpr(MiniJavaParser.ThisExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitIntExpr(MiniJavaParser.IntExprContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    @Override
    public Integer visitArrayExpr(MiniJavaParser.ArrayExprContext ctx) {
        String id = ctx.expression(0).getText();
        int length = visit(ctx.expression(1));
        memory.put(id, length); //将数组的长度存入map中
        return visitChildren(ctx);
    }

    @Override
    public Integer visitNewIdExpr(MiniJavaParser.NewIdExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitNestedExpr(MiniJavaParser.NestedExprContext ctx) {
        return visit(ctx.expression()); //嵌套语句，其值与内部的expression一致
    }

    @Override
    public Integer visitSubExpr(MiniJavaParser.SubExprContext ctx) {
        int left = visit(ctx.expression(0));
        int right = visit(ctx.expression(1));
        return left - right;
    }

    @Override
    public Integer visitLengthExpr(MiniJavaParser.LengthExprContext ctx) {
        String id =  ctx.expression().getText();
        int value = memory.get(id); //取得数组的长度值
        return value;
    }

    @Override
    public Integer visitNotExpr(MiniJavaParser.NotExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitAddExpr(MiniJavaParser.AddExprContext ctx) {
        int left = visit(ctx.expression(0));
        int right = visit(ctx.expression(1));
        return left + right;
    }

    @Override
    public Integer visitMulExpr(MiniJavaParser.MulExprContext ctx) {
        int left = visit(ctx.expression(0));
        int right = visit(ctx.expression(1));
        return left * right;
    }

    @Override
    public Integer visitBoolExpr(MiniJavaParser.BoolExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitCallExpr(MiniJavaParser.CallExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitNewArrayExpr(MiniJavaParser.NewArrayExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitIdExpr(MiniJavaParser.IdExprContext ctx) {
        //id类型，即变量名或者函数名，直接在map中查找其值
        //TODO:此处需要修改
        String id = ctx.getText();
        //System.out.println(id);
        int value = memory.get(id);
        return value;
    }

    @Override
    public Integer visitCompareExpr(MiniJavaParser.CompareExprContext ctx) {
        int left = visit(ctx.expression(0));
        int right = visit(ctx.expression(1));
        //return left < right;
        //TODO:bool类型怎么处理
        return -1;
    }

    @Override
    public Integer visitAndExpr(MiniJavaParser.AndExprContext ctx) {
        int left = visit(ctx.expression(0));
        int right = visit(ctx.expression(1));
        return left & right;
    }

    @Override
    public Integer visitType(MiniJavaParser.TypeContext ctx) {
        return visitChildren(ctx);
    }
}
