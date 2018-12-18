package main;

/**
 * 结点的抽象基类
 * 子类包括ClassNode和MethodNode
 */
abstract public class Node extends Symbol implements Scope {
    protected Scope upperScope;
    protected boolean valid; //TODO：这种默认参数值的用法

    public Node(String name, Scope upper) {
        super(name);
        this.upperScope = upper;
        this.valid = true;
    }

    public Node(String name, Scope upper, boolean valid) {
        super(name);
        this.upperScope = upper;
        this.valid = valid;
    }

    public String getText() {
        return super.getName();
    }

    public Scope getNode() {
        return this; //将当前类直接作为Scope类型返回，因为this类已经完整实现了Scope所有接口
    }

    public Scope getUpperScope() {
        return upperScope;
    }

    public abstract void addSymbol(Symbol sym); //变量声明中的符号加入至varTable中

    public boolean isValid() {
        return valid;
    }

    public abstract Symbol findLocalSym(String name);

    /*在当前作用域以及上一级的作用域中查找*/
    public abstract Symbol findWholeSym(String name);

}
