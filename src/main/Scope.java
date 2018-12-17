package main;

public interface Scope {
    public String getName();
    public Scope getScope();
    public Scope getParent(); //返回类型为Scope，以便methodNode实现
    public void addSymbol(Symbol sym);
    public boolean isValid();
    public Symbol findSymbol(String name);
}
