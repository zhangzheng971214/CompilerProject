package main;
import main.gen.MiniJavaBaseListener;

import java.util.*;

//Constructor of Scope Tree, including Scopes and Symbol Tables
public class ScopeTreeCtor extends MiniJavaBaseListener{
    private Map<String, Node> Nodes = new HashMap<String, Node>(); //保存AST中所有的结点对象
    private Node currentNode; //记录当前处理的结点


}
