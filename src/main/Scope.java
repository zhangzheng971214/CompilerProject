package main;

public interface Scope {
    public String getName();
    public Scope getScope();
    public classNode getParent(); //获得parent的classNode结点
    public void addSymbol(Symbol sym);
    public boolean isValid();
    public Symbol findSymbol(String name);
}
