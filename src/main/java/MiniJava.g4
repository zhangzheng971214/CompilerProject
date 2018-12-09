// MiniJava complier implemented by Antlr
grammar MiniJava;

goal
    :   mainClass classDeclaration* EOF;

mainClass
    :   'class' IDENTIFIER
        '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' IDENTIFIER ')'
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
            (varDeclaration)*
            (statement)*
            'return' expression';'
        '}';

formalParameters
    :   '(' (type IDENTIFIER (',' type IDENTIFIER)*)? ')';

statement
    :   '{' statement* '}'                                  # nestedStatement
    |   'if' '(' expression ')' statement
        'else' statement                                    # ifStatement
    |   'while' '(' expression ')' statement                # whileStatement
    |   'System.out.println' '(' expression ')' ';'         # printStatement
    |   IDENTIFIER '=' expression ';'                       # assignStatement
    |   IDENTIFIER '[' expression ']' '=' expression ';'    # arrayAssignStatement
    ;

/* Expression definition from BNF
Expression:Expression ( '&&' | '<' | '+' | '-' | '*' ) Expression
           |	Expression '[' Expression ']'
           |	Expression '.' 'length'
           |	Expression '.' Identifier '(' ( Expression ( ',' Expression )* )? ')'
           |	INTEGER_LITERAL
           |	'true'
           |	'false'
           |	Identifier
           |	'this'
           |	'new' 'int' '[' Expression ']'
           |	'new' Identifier '(' ')'
           |	'!' Expression
           |	'(' Expression ')';
*/
// Try to solve left-reclusive problem.
expression
    :   exp2(exp1)*;

exp1
    :   ( '&&' | '<' | '+' | '-' | '*' ) expression
    |    '[' expression ']'
    |    '.' 'length'
    |    '.' IDENTIFIER '(' ( expression ( ',' expression )* )? ')';

exp2
    :   INT
    |    'true'
    |    'false'
    |    IDENTIFIER
    |    'this'
    |    'new' 'int' '[' expression ']'
    |    'new' IDENTIFIER '(' ')'
    |    '!' expression
    |    '(' expression ')';

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