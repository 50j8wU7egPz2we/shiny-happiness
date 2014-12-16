/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2001 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * This is a modified version of the example from                          *
 *   http://www.lincom-asg.com/~rjamison/byacc/                            *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * This program is free software; you can redistribute it and/or modify    *
 * it under the terms of the GNU General Public License. See the file      *
 * COPYRIGHT for more information.                                         *
 *                                                                         *
 * This program is distributed in the hope that it will be useful,         *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the           *
 * GNU General Public License for more details.                            *
 *                                                                         *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA                 *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%{
  import java.io.*;
  import gen.mat.elem.*;
  import gen.mat.expr.*;
%}
      
%token        NL     /* newline  */
%token <dval> NUM    /* a number */
%token <sval> IDENT  /* a string */
%token        SQRT   /* a square root */

%type <obj> exp
%type <obj> term
%type <obj> factor
%type <obj> neg
%type <obj> liter

%left '-' '+'
%left '*' '/'
%left NEG          /* negation--unary minus */
%right '^'         /* exponentiation        */
      
%%

     
input:   exp NL  { parsed = (Expression)$1; }
       ;
      
exp:     term                { $$ =                          (Expression)$1                                        ;}
       | term '+' exp        { $$ = new Addition(            (Expression)$1,       (Expression)$3                 );}
       | term '-' exp        { $$ = new Subtraction(         (Expression)$1,       (Expression)$3                 );}
       ;                                                   
                                                           
                                                           
                                                           
term:    factor              { $$ =                           (Expression)$1                                        ;}
       | factor '*' term     { $$ = new Multiplication(       (Expression)$1,       (Expression)$3                 );}
       | factor '/' term     { $$ = new Division(             (Expression)$1,       (Expression)$3                 );}
       ;                                                    
                                                            
                                                            
                                                            
factor:  neg                 { $$ =                          (Expression)$1                                        ;}
       | factor '^' neg      { $$ = new Exponent(            (Expression)$1,       (Expression)$3                 );}
       ;                                                  
       
neg:     liter
       | '-' liter           { $$ = new Negation(            (Expression)$2                                       );}
                                                          
                                                          
liter:   NUM                 { $$ = new Real(                            $1                                       );}
       | IDENT               { $$ = new Variable(                        $1                                       );}
       | SQRT '(' exp ')'    { $$ = Exponent.squareRoot(     (Expression)$3                                       );}
       | '(' exp ')'         { $$ = new Paren(               (Expression)$2                                       );}
       ;
       
//factor:  NUM                { $$ = new Real(              (Expression)$1                                       );}
//       | IDENT              { $$ = new Variable(          (Expression)$1                                       );}
//       | SQRT '(' exp ')'   { $$ = Exponent.squareRoot(   (Expression)$3                                       );}
//       | exp '+' exp        { $$ = new Addition(          (Expression)$1,       (Expression)$3                 );}
//       | exp '-' exp        { $$ = new Subtraction(       (Expression)$1,       (Expression)$3                 );}
//       | exp '*' exp        { $$ = new Multiplication(    (Expression)$1,       (Expression)$3                 );}
//       | exp '/' exp        { $$ = new Division(          (Expression)$1,       (Expression)$3                 );}
//       | '-' exp  %prec NEG { $$ = new Negation(          (Expression)$2                                       );}
//       | exp '^' exp        { $$ = new Exponent(          (Expression)$1,       (Expression)$3                 );}
//       | '(' exp ')'        { $$ = new Paren(             (Expression)$2                                       );}
//       ;
%%
///%prec NEG
  Expression parsed;
  
  private Yylex lexer;
  
  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }


  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }


  static boolean interactive;

  public static void main(String args[]) throws IOException {
    System.out.println("BYACC/Java with JFlex Calculator Demo");

    Parser yyparser;
    if ( args.length > 0 ) {
      // parse a file
      yyparser = new Parser(new FileReader(args[0]));
    }
    else {
      // interactive mode
      System.out.println("[Quit with CTRL-D]");
      System.out.print("Expression: ");
      interactive = true;
	    yyparser = new Parser(new InputStreamReader(System.in));
    }

    yyparser.yyparse();
    
    if (interactive) {
      System.out.println();
      System.out.println("Have a nice day");
    }
  }
