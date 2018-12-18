package main;

import java.util.*;

public interface Scope {
    public String getName();

    public Scope getNode(); //返回Scope对应的class或者method结点

    public Scope getUpperScope(); //返回类型为Scope，以便methodNode实现

    public void addSymbol(Symbol sym);

    public boolean isValid();

    public Symbol findSymbol(String name);

    //For Test
    public Map<String, Symbol> getSym();
}
