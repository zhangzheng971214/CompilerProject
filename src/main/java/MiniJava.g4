// MiniJava complier implemented by Antlr
grammar MiniJava;

Goal:MainClass(ClassDeclaration)*EOF;

MainClass:'class' Identifier '{' 'public' 'static' 'void' 'main' '(' 'String'
            '[' ']' Identifier ')' '{' Statement '}''}';

ClassDeclaration:'class' Identifier ( 'extends' Identifier )? '{' (
                    VarDeclaration )* ( MethodDeclaration )* '}';

VarDeclaration:Type Identifier;

MethodDeclaration:'public' Type Identifier '(' ( Type Identifier ( ','
                    Type Identifier )* )? ')' '{' ( VarDeclaration )* (
                    Statement )* 'return' Expression ';' '}';

Type:'int' '[' ']'
      | 'boolean'
      | 'int'
      | Identifier;

Statement:'{' ( Statement )* '}'
          |	'if' '(' Expression ')' Statement 'else' Statement
          |	'while' '(' Expression ')' Statement
          |	'System.out.println' '(' Expression ')' ';'
          |	Identifier '=' Expression ';'
          |	Identifier '[' Expression ']' '=' Expression ';';

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

Identifier:Letter(Letter|Digit)*;

fragment
Letter:'a'..'z' | 'A'..'Z' | '_';

INTEGER_LITERAL: Digit+; //定义为1个或多个整数组成

fragment
Digit: '0'..'9';