// MiniJava complier implemented by Antlr
grammar MiniJava;

goal:mainClass(classDeclaration)*EOF;

mainClass:'class' IDENTIFIER '{' KEYWORD KEYWORD KEYWORD KEYWORD '(' KEYWORD
            '[' ']' IDENTIFIER ')' '{' statement '}''}';

classDeclaration:'class' IDENTIFIER ( 'extends' IDENTIFIER )? '{' (
                    varDeclaration )* ( methodDeclaration )* '}';

varDeclaration:Type IDENTIFIER';';

methodDeclaration:'public' Type IDENTIFIER '(' ( Type IDENTIFIER ( ','
                    Type IDENTIFIER )* )? ')' '{' ( varDeclaration )* (
                    statement )* 'return' expression ';' '}';

Type:'int' '[' ']'
      | 'boolean'
      | 'int'
      | IDENTIFIER;

statement:'{' ( statement )* '}'
          |	'if' '(' expression ')' statement 'else' statement
          |	'while' '(' expression ')' statement
          |	'System.out.println' '(' expression ')' ';'
          |	IDENTIFIER '=' expression ';'
          |	IDENTIFIER '[' expression ']' '=' expression ';';
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
expression:exp2(exp1)*;

exp1:( '&&' | '<' | '+' | '-' | '*' ) expression
     |  '[' expression ']'
     |  '.' 'length'
     |  '.' IDENTIFIER '(' ( expression ( ',' expression )* )? ')';

exp2:INT
    |   'true'
    |   'false'
    |   IDENTIFIER
    |   'this'
    |   'new' 'int' '[' expression ']'
    |   'new' IDENTIFIER '(' ')'
    |   '!' expression
    |   '(' expression ')';

KEYWORD:'class'
        |'public'
        |'static'
        |'void'
        |'main'
        |'String'
        |'extends'
        |'return'
        |'int'
        |'boolean'
        |'if'
        |'else'
        |'while'
        |'length'
        |'true'
        |'false'
        |'this'
        |'new';

IDENTIFIER:('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*;

fragment
WS:(' '|'\t'|'\n'|'\r')+{skip();};

fragment
LETTER:[a-zA-Z];

INT: DIGIT+; //定义为1个或多个整数组成

fragment
DIGIT: '0'..'9';