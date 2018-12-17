package main;
import java.util.*;

//定义符号的类，用于保存每个symbol的信息，包括name, type等
public class Symbol {
    private String name;
    private String type;
    //构造函数
    public Symbol(String name){
        this.name = name;
    }

    public Symbol(String name, String type){
        this.name = name;
        this.type = type;
    }
    //获取name, type的函数
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

}
