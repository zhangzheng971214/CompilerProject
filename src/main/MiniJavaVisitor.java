package main;

import main.gen.MiniJavaBaseVisitor;
import main.gen.MiniJavaParser;

import java.util.*;

public class MiniJavaVisitor extends MiniJavaBaseVisitor<Integer> {
    Map<String, Integer> memory = new HashMap<String, Integer>();

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
        return visitChildren(ctx);
    }

    @Override
    public Integer visitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        return visitChildren(ctx);
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
        return visitChildren(ctx);
    }

    @Override
    public Integer visitWhileStmt(MiniJavaParser.WhileStmtContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Integer visitPrintStmt(MiniJavaParser.PrintStmtContext ctx) {
        //Minijava代码中的println语句
        Integer value = visit(ctx.expression());
        System.out.println();
        System.out.println();
        System.out.println(value); //TODO:测试输出
        return value;
    }

    @Override
    public Integer visitAssignStmt(MiniJavaParser.AssignStmtContext ctx) {
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
        return length;
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
        int value = memory.get(id);
        return value;
    }

    @Override
    public Integer visitCompareExpr(MiniJavaParser.CompareExprContext ctx) {
        int left = visit(ctx.expression(0));
        int right = visit(ctx.expression(1));
        //return left < right;
        //TODO:bool类型怎么处理
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
