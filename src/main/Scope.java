package main;

public interface Scope {
    public String getName();

    public Scope getScope();

    public Scope getParentScope();

    public void addSymbol(Symbol sym);

    public boolean isValid();

    public Symbol findSymbol(String name);
}
