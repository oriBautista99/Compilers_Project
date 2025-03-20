package ve.edu.unet;

import java_cup.runtime.*;
import java.io.Reader;
//import otros.*;

%%
/* Habilitar la compatibilidad con el interfaz CUP para el generador sintactico*/
%cup
/* Llamar Scanner a la clase que contiene el analizador Lexico */
%class Lexico

/*-- DECLARACIONES --*/
%{
	public Lexico(Reader r, SymbolFactory sf){
        this(r);
		this.sf=sf;
		lineanum=0;
		debug=true;
	}
	private SymbolFactory sf;
	private int lineanum;
	private boolean debug;



%}

/*REVISAR PARA QUE SIRVE*/
%eofval{
    return sf.newSymbol("EOF",sym.EOF);
%eofval}

/* Acceso a la columna y fila actual de analisis CUP */
%line
%column

/* Expresiones regulares */
digito		= [0-9]
numero		= {digito}+
letra			= [a-zA-Z]
identificador	= {letra} ({letra} | {digito})*
nuevalinea		= \n | \n\r | \r\n
espacio		= [ \t\r\n]+
%%

/* Palabras Claves */

"if"            {	if(debug) System.out.println("token IF");
			return sf.newSymbol("IF",sym.IF);
			}
"then"          { if(debug) System.out.println("token THEN");
			return sf.newSymbol("THEN",sym.THEN);
			}
"else"          {	if(debug) System.out.println("token ELSE");
			return sf.newSymbol("ELSE",sym.ELSE);
			}
"end"           {	if(debug) System.out.println("token END");
			return sf.newSymbol("END",sym.END);
			}
"repeat"        {	if(debug) System.out.println("token REPEAT");
			return sf.newSymbol("REPEAT",sym.REPEAT);
			}
"until"         {	if(debug) System.out.println("token UNTIL");
			return sf.newSymbol("UNTIL",sym.UNTIL);
			}
"for"           {  if(debug) System.out.println("token FOR");
            return sf.newSymbol("FOR",sym.FOR);
            }
"boolean"           {  if(debug) System.out.println("token BOOLEAN");
            return sf.newSymbol("BOOLEAN",sym.BOOLEAN);
            }
"int"           {  if(debug) System.out.println("token INT");
                        return sf.newSymbol("INT",sym.INT);
      }
"vector"           {  if(debug) System.out.println("token VECTOR");
            return sf.newSymbol("VECTOR",sym.VECTOR);
            }
"to"           {  if(debug) System.out.println("token TO");
            return sf.newSymbol("TO",sym.TO);
            }
"do"           {  if(debug) System.out.println("token DO");
            return sf.newSymbol("DO",sym.DO);
            }
/*  Lista de Operadores  */
/* Asignacion */
":="            {	if(debug) System.out.println("token ASSIGN");
			return sf.newSymbol("ASSIGN",sym.ASSIGN);
			}
"true"       {	if(debug) System.out.println("token TRUE");
          return sf.newSymbol("TRUE",sym.TRUE);
      }
"false"      {	if(debug) System.out.println("token FALSE");
          return sf.newSymbol("FALSE",sym.FALSE);
      }
/* Matematicos */
"+"             {	if(debug) System.out.println("token PLUS");
			return sf.newSymbol("PLUS",sym.PLUS);
			}
"-"             {	if(debug) System.out.println("token MINUS");
			return sf.newSymbol("MINUS",sym.MINUS);
			}
"*"             {	if(debug) System.out.println("token TIMES");
			return sf.newSymbol("TIMES",sym.TIMES);
			}
"/"             {	if(debug) System.out.println("token OVER");
			return sf.newSymbol("OVER",sym.OVER);
			}
"mod"             {	if(debug) System.out.println("token MOD");
			return sf.newSymbol("MOD",sym.MOD);
			}
/* Relaciones */
"="             {	if(debug) System.out.println("token EQ");
			return sf.newSymbol("EQ",sym.EQ);
			}
"<"             {	if(debug) System.out.println("token LT");
			return sf.newSymbol("LT",sym.LT);
			}
"<="             {	if(debug) System.out.println("token LE");
			return sf.newSymbol("LE",sym.LE);
			}
">"             { if(debug) System.out.println("token GT");
            return sf.newSymbol("GT", sym.GT);
            }
">="             { if(debug) System.out.println("token GE");
            return sf.newSymbol("GE", sym.GE);
            }
"<>"             { if(debug) System.out.println("token NE");
            return sf.newSymbol("NE", sym.NE);
            }
/* Logicos */
"and"             { if(debug) System.out.println("token AND");
            return sf.newSymbol("AND", sym.AND);
            }
"or"             { if(debug) System.out.println("token OR");
            return sf.newSymbol("OR", sym.OR);
            }
"not"             { if(debug) System.out.println("token NOT");
            return sf.newSymbol("NOT", sym.NOT);
            }
/* Vector */
"["             { if(debug) System.out.println("token LBRACKET");
            return sf.newSymbol("LBRACKET", sym.LBRACKET);
            }
"]"             { if(debug) System.out.println("token RBRACKET");
            return sf.newSymbol("RBRACKET", sym.RBRACKET);
            }

/* Comentarios */
"{" [^}]* "}"  { /* salto comentarios */ if(debug) System.out.println("token COMENTARIO"); }

/* Extras */
"read"          {	if(debug) System.out.println("token READ");
			return sf.newSymbol("READ",sym.READ);
			}
"write"         {	if(debug) System.out.println("token WRITE");
			return sf.newSymbol("WRITE",sym.WRITE);
			}
"("             {	if(debug) System.out.println("token LPAREN");
			return sf.newSymbol("LPAREN",sym.LPAREN);
			}
")"             {	if(debug) System.out.println("token RPAREN");
			return sf.newSymbol("RPAREN",sym.RPAREN);
			}
";"             {	if(debug) System.out.println("token SEMI");
			return sf.newSymbol("SEMI",sym.SEMI);
}
/* numero e Identificador */
{numero}        {	if(debug) System.out.println("token NUM");
			return sf.newSymbol("NUM",sym.NUM,new String(yytext()));
			}
{identificador}	{	if(debug) System.out.println("token ID");
				return sf.newSymbol("ID",sym.ID,new String(yytext()));
			}
/* Espacios */
{espacio}    { /* saltos espacios en blanco*/}

/* Manejo de Errores*/
.               {System.err.println("Caracter Ilegal encontrado en analisis lexico: " + yytext() + "\n");}

/* Extra */
{nuevalinea}       {lineanum++;}