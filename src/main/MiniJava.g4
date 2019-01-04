// MiniJava complier implemented by Antlr
grammar MiniJava;

options { language = Java;
           output = AST;
           ASTLabelType = CommonTree;}

goal
    :   mainClass classDeclaration* EOF
    ;

mainClass
    :   'class' name=ID
        '{'
            'public' 'static' 'void' 'main' '(' 'String' '[' ']' ID ')'
            '{' statement '}'
        '}'
        //{System.out.println("class "+$ID.text);}
    ;   //finally{System.out.println("class ERROR!");}

classDeclaration
    :   'class' name=ID ('extends' parent=ID)?
        '{' varDeclaration* methodDeclaration* '}'
        //{System.out.println("class "+$ID.text);}
    ;

varDeclaration
    :vtype=type name=ID';'
    ;

methodDeclaration
    :   'public' rtype=type name=ID '('formalParameters?')'
        '{'
            varDeclaration*
            statement*
            'return' expression ';'
        '}'
    ;
//修改formalParameters以便于ScopeChecker匹配
formalParameters
    :   ptype=type name=ID
    |   ptype=type name=ID ',' formalParameters
    ;

statement
    :   '{' statement* '}'                                              # nestedStmt
    |   'if' '(' condition=expression ')' statement 'else' statement    # ifStmt
    |   'while' '(' expression ')' statement                            # whileStmt
    |   'System.out.println' '(' expression ')' ';'                     # printStmt
    |   assignName=ID '=' expression ';'                                # assignStmt
    |   aAssignName=ID '[' expression ']' '=' expression ';'            # arrayAssignStmt
    ;

expression
    :   expression '[' expression ']'                                       # arrayExpr
    |   expression '.' 'length'                                             # lengthExpr
    |   expression '.' callName=ID '(' (expression (',' expression)*)? ')'  # callExpr
    |   expression '*'  expression                                          # mulExpr
    |   expression '+'  expression                                          # addExpr
    |   expression '-'  expression                                          # subExpr
    |   expression '<'  expression                                          # compareExpr
    |   expression '&&' expression                                          # andExpr
    |   INT                                                                 # intExpr
    |   ('true' | 'false')                                                  # boolExpr
    |   idName=ID                                                           # idExpr
    |	'this'                                                              # thisExpr
    |	'new' 'int' '[' expression ']'                                      # newArrayExpr
    |	'new' newName=ID '(' ')'                                            # newIdExpr
    |	'!' expression                                                      # notExpr
    |	'(' expression ')'                                                  # nestedExpr
    ;

type
    :    'int' '[' ']'
    |    'boolean'
    |    'int'
    |    ID
    ;

ID
    :   [a-zA-Z_][0-9a-zA-Z_]*
    ;

INT
    :   [0-9]+      //允许0开头,负数怎么办？
    ;

WS
    :   [ \r\t\n]+ -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;