// MiniJava complier implemented by Antlr
grammar MiniJava;

goal:mainClass(classDeclaration)*EOF;

mainClass:'class' Identifier '{' 'public' 'static' 'void' 'main' '(' 'String'
            '[' ']' Identifier ')' '{' statement '}''}';

classDeclaration:'class' Identifier ( 'extends' Identifier )? '{' (
                    varDeclaration )* ( methodDeclaration )* '}';

varDeclaration:Type Identifier';';

methodDeclaration:'public' Type Identifier '(' ( Type Identifier ( ','
                    Type Identifier )* )? ')' '{' ( varDeclaration )* (
                    statement )* 'return' expression ';' '}';

Type:'int' '[' ']'
      | 'boolean'
      | 'int'
      | Identifier;

statement:'{' ( statement )* '}'
          |	'if' '(' expression ')' statement 'else' statement
          |	'while' '(' expression ')' statement
          |	'System.out.println' '(' expression ')' ';'
          |	Identifier '=' expression ';'
          |	Identifier '[' expression ']' '=' expression ';';
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
     |  '.' Identifier '(' ( expression ( ',' expression )* )? ')';

exp2:INTEGER_LITERAL
    |   'true'
    |   'false'
    |   Identifier
    |   'this'
    |   'new' 'int' '[' expression ']'
    |   'new' Identifier '(' ')'
    |   '!' expression
    |   '(' expression ')';

Identifier:LETTER(LETTER|DIGIT)*;

fragment
LETTER:'a'..'z' | 'A'..'Z' | '_';

INTEGER_LITERAL: DIGIT+; //定义为1个或多个整数组成

fragment
DIGIT: '0'..'9';