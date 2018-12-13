// MiniJava complier implemented by Antlr
grammar MiniJava;

goal
    :   mainClass classDeclaration* EOF
    ;

mainClass
    :   'class' ID
        '{'
            'public' 'static' 'void' 'main' '(' 'String' '[' ']' ID ')'
            '{' statement '}'
        '}'
        {System.out.println("class "+$ID.text);}
    ;

classDeclaration
    :   'class' ID ('extends' ID)?
        '{' varDeclaration* methodDeclaration* '}'
        {System.out.println("class "+$ID.text);}
    ;

varDeclaration
    :   type ID';'
    ;

methodDeclaration
    :   'public' type ID formalParameters
        '{'
            varDeclaration*
            statement*
            'return' expression ';'
        '}'
    ;

formalParameters
    :   '(' (type ID (',' type ID)*)? ')'
    ;

statement
    :   '{' statement* '}'                                  # nestedStmt
    |   'if' '(' expression ')' statement 'else' statement  # ifStmt
    |   'while' '(' expression ')' statement                # whileStmt
    |   'System.out.println' '(' expression ')' ';'         # printStmt
    |   ID '=' expression ';'                               # assignStmt
    |   ID '[' expression ']' '=' expression ';'            # arrayAssignStmt
    ;

expression
    :   expression '[' expression ']'                               # arrayExpr
    |   expression '.' 'length'                                     # lengthExpr
    |   expression '.' ID '(' (expression (',' expression)*)? ')'   # callExpr
    |   expression '*'  expression                                  # mulExpr
    |   expression '+'  expression                                  # addExpr
    |   expression '-'  expression                                  # subExpr
    |   expression '<'  expression                                  # compareExpr
    |   expression '&&' expression                                  # andExpr
    |   INT                                                         # intExpr
    |   ('true' | 'false')                                          # boolExpr
    |   ID                                                          # idExpr
    |	'this'                                                      # thisExpr
    |	'new' 'int' '[' expression ']'                              # newArrayExpr
    |	'new' ID '(' ')'                                            # newIdExpr
    |	'!' expression                                              # notExpr
    |	'(' expression ')'                                          # nestedExpr
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