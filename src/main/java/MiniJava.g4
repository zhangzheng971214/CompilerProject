// MiniJava complier implemented by Antlr
grammar MiniJava;

goal
    :   mainClass classDeclaration* EOF;

mainClass
    :   'class' IDENTIFIER
        '{'
            'public' 'static' 'void' 'main' '(' 'String' '[' ']' IDENTIFIER ')'
            '{' statement '}'
        '}';

classDeclaration
    :   'class' IDENTIFIER ('extends' IDENTIFIER)?
        '{' (varDeclaration)* (methodDeclaration)* '}';

varDeclaration
    :   type IDENTIFIER';';

methodDeclaration
    :   'public' type IDENTIFIER formalParameters
        '{'
            varDeclaration*
            statement*
            'return' expression ';'
        '}';

formalParameters
    :   '(' (type IDENTIFIER (',' type IDENTIFIER)*)? ')';

statement
    :   '{' statement* '}'                                  # nestedStmt
    |   'if' '(' expression ')' statement 'else' statement                                    # ifStmt
    |   'while' '(' expression ')' statement                # whileStmt
    |   'System.out.println' '(' expression ')' ';'         # printStmt
    |   IDENTIFIER '=' expression ';'                       # assignStmt
    |   IDENTIFIER '[' expression ']' '=' expression ';'    # arrayAssignStmt
    ;

expression
    :   expression '[' expression ']'   # arrayExpr
    |   expression '.' 'length'         # lenExpr
    |   expression '.' IDENTIFIER '(' (expression (',' expression)*)? ')'   # callExpr
    |   expression '*'  expression      # mulExpr
    |   expression '+'  expression      # addExpr
    |   expression '-'  expression      # subExpr
    |   expression '<'  expression      # compareExpr
    |   expression '&&' expression      # andExpr
    |   INT                             # intExpr
    |   ('true' | 'false')              # boolExpr         //负号运算怎么办？
    |   IDENTIFIER                      # idExpr
    |	'this'                          # thisExpr
    |	'new' 'int' '[' expression ']'  # newArrayExpr
    |	'new' IDENTIFIER '(' ')'        # newIdExpr
    |	'!' expression                  # notExpr
    |	'(' expression ')'              # nestedExpr
    ;

type
    :    'int' '[' ']'
    |    'boolean'
    |    'int'
    |    IDENTIFIER;

IDENTIFIER
    :   [a-zA-Z_][0-9a-zA-Z_]*;

INT
    :   [0-9]+; //允许0开头

WS
    :   [ \r\t\n]+ -> skip;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip;