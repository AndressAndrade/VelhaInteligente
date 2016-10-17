%{
    /* Alunas: Andressa Andrade e Renata Antunes
       Classes possíveis para os tokens da linguagem:
       T_NAME, T_NUMBER,T_AND, T_NOT, T_OR, T_ELSEIF, T_WHILE, T_DO, T_FUNCTION,
       T_END, T_FOR, T_ELSE, T_IF, T_THEN, T_RETURN, T_LOCAL, T_NIL, T_PLUS, T_MINUS,
       T_TIMES, T_DIV, T_COMMA, T_OPENPAR, T_CLOSEPAR, T_SEMICOL, T_ASSIGN,T_EQ, T_NEQ,
       T_LTEQ, T_GTEQ, T_LT, T_GT
    */
    #include <ctype.h>
    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>

    int yywrap(){
        return 1;
    }

    extern FILE *yyout;
%}

%union{
    int ival;
    char sval[1000];
};

%token <sval>  T_OPENPAR T_CLOSEPAR T_COMMA T_SEMICOL T_ASSIGN  T_DO T_ELSE T_ELSEIF T_END T_FOR T_FUNCTION T_IF T_LOCAL T_NIL T_NOT T_RETURN T_THEN T_WHILE T_NAME
%token <ival>  T_NUMBER
%left <sVal>   T_LT T_GT T_EQ  T_NEQ T_LTEQ T_GTEQ 
%left <sVal>   T_PLUS T_MINUS
%left <sVal>   T_TIMES T_DIV
%left <sVal>   T_AND T_OR

%type <sval>   exp termo fator program bloco comando comandos comandoret elseifthen listaexp listadenomes opunaria chamadadefuncao virgulanomes virgulaexp

%%

/* Sintaxe da Linguagem */

program         : bloco                  { fprintf(yyout, "[programa%s]\n", $1); }
                ;

bloco           : comandos               { sprintf($$," [bloco%s]", $1); }  
                | comandos comandoret    { sprintf($$," [bloco%s [comandoret %s]]", $1, $2); }
                ;

comandos        : /* Empty */        { strcpy($$,""); }
                | comando comandos   { sprintf($$," [comando %s]%s", $1, $2); }  
                ;

comando         : T_SEMICOL           { sprintf($$,"[T_SEMICOL ;]"); }
		        | listadenomes T_ASSIGN listaexp { sprintf($$,"%s [T_ASSIGN =] %s", $1, $3); }
		        | chamadadefuncao     { sprintf($$,"%s", $1); }
		        | T_DO bloco T_END    { sprintf($$,"[T_DO do] %s [T_END end]", $2); }
		        | T_WHILE exp T_DO    { sprintf($$,"[T_WHILE while] %s [T_DO do]", $2); }
		        | T_FOR T_NAME T_ASSIGN exp T_COMMA exp T_DO bloco T_END              { sprintf($$,"[T_FOR for] %s [T_ASSIGN =] %s [T_COMMA ,] %s [T_DO do]%s [T_END end]", $2, $4, $6, $8); }
		        | T_FOR T_NAME T_ASSIGN exp T_COMMA exp T_COMMA exp T_DO bloco T_END  { sprintf($$,"[T_FOR for] %s [T_ASSIGN =] %s [T_COMMA ,] %s [T_COMMA ,] %s [T_DO do]%s [T_END end]", $2, $4, $6, $8, $10); }
		        | T_IF exp T_THEN bloco elseifthen T_ELSE bloco T_END                 { sprintf($$,"[T_IF if] %s [T_THEN then]%s%s [T_ELSE else]%s [T_END end]", $2, $4, $5, $7); }
                | T_IF exp T_THEN bloco elseifthen T_END                              { sprintf($$,"[T_IF if] %s [T_THEN then]%s%s [T_END end]", $2, $4, $5); }
		        | T_FUNCTION T_NAME T_OPENPAR T_CLOSEPAR bloco T_END                  { sprintf($$,"[T_FUNCTION function] [T_NAME %s] [T_OPENPAR (] [T_CLOSEPAR )]%s [T_END end]", $2, $5); }
		        | T_FUNCTION T_NAME T_OPENPAR listadenomes T_CLOSEPAR bloco T_END     { sprintf($$,"[T_FUNCTION function] [T_NAME %s] [T_OPENPAR (] %s [T_CLOSEPAR )]%s [T_END end]", $2, $4, $6); }
                | T_LOCAL listadenomes                      { sprintf($$,"[T_LOCAL local] %s", $2); }
                | T_LOCAL listadenomes T_ASSIGN listaexp    { sprintf($$,"[T_LOCAL local] %s [T_ASSIGN =] %s", $2, $4); }
		        ;

elseifthen      : /* Empty */                           { strcpy($$,""); }
                | T_ELSEIF exp T_THEN bloco elseifthen  { sprintf($$," [T_ELSEIF elseif] %s [T_THEN then]%s%s", $2, $4, $5); }
                ;

comandoret      : T_RETURN                      { sprintf($$,"[T_RETURN return]"); }
                | T_RETURN listaexp             { sprintf($$,"[T_RETURN return] %s", $2); }
                | T_RETURN T_SEMICOL            { sprintf($$,"[T_RETURN return] [T_SEMICOL ;]"); }
                | T_RETURN listaexp T_SEMICOL   { sprintf($$,"[T_RETURN return] %s [T_SEMICOL ;]", $2); }
                ;

exp  : exp T_PLUS termo                  { sprintf($$,"[exp %s [opbin [T_PLUS +]] %s]", $1, $3); }
     | exp T_MINUS termo                 { sprintf($$,"[exp %s [opbin [T_MINUS -]] %s]", $1,$3); }
     //| exp T_ASSIGN termo                { sprintf($$,"[exp %s [opbin [T_ASSIGN =]] %s]", $1, $3); } 
     | exp T_EQ termo                    { sprintf($$,"[exp %s [opbin [T_EQ ==]] %s]", $1,$3); }
     | termo T_NEQ fator                 { sprintf($$,"[exp %s [opbin [T_NEQ ~=]] %s]", $1, $3); } 
     | termo                             { sprintf($$,"%s", $1); }
     ;

termo: termo T_TIMES fator                 { sprintf($$,"[exp %s [opbin [T_TIMES *]] %s]", $1,$3); }
     | termo T_DIV fator                   { sprintf($$,"[exp %s [opbin [T_DIV /]] %s]", $1, $3); }
     | termo T_LT fator                    { sprintf($$,"[exp %s [opbin [T_LT <]] %s]", $1, $3); }   
     | termo T_LTEQ fator                  { sprintf($$,"[exp %s [opbin [T_LTEQ <=]] %s]", $1, $3); }        
     | termo T_GT fator                    { sprintf($$,"[exp %s [opbin [T_GT >]] %s]", $1, $3); }        
     | termo T_GTEQ fator                  { sprintf($$,"[exp %s [opbin [T_GTEQ >=]] %s]", $1, $3); }            
     | termo T_AND fator                   { sprintf($$,"[exp %s [opbin [T_AND and]] %s]", $1, $3); }     
     | termo T_OR fator                    { sprintf($$,"[exp %s [opbin [T_OR or]] %s]", $1, $3); }
     | fator                               { sprintf($$,"%s", $1); }
     ;

fator: T_NUMBER                      { sprintf($$,"[exp [T_NUMBER %d]]", $1); }
     | T_NAME                        { sprintf($$,"[exp [T_NAME %s]]", $1); }
     | T_NIL                         { sprintf($$,"[exp [T_NIL nil]]"); }
     | chamadadefuncao               { sprintf($$,"[exp %s]", $1); }
     | opunaria                      { sprintf($$,"[exp %s]", $1); }
     ;

//exp opbin exp { sprintf($$,"[exp %s [opbin %s] %s]", $1, $2, $3); }

chamadadefuncao : T_NAME T_OPENPAR T_CLOSEPAR           { sprintf($$,"[chamadadefuncao [T_NAME %s] [T_OPENPAR (] [T_CLOSEPAR )]]", $1); }
                | T_NAME T_OPENPAR listaexp T_CLOSEPAR  { sprintf($$,"[chamadadefuncao [T_NAME %s] [T_OPENPAR (] %s [T_CLOSEPAR )]]", $1, $3); }
                ;

listadenomes    : T_NAME virgulanomes           { sprintf($$,"[listadenomes [T_NAME %s]%s]", $1, $2); }
                ;

virgulanomes    : /* Empty */                   { strcpy($$,""); }
                | T_COMMA T_NAME virgulanomes   { sprintf($$," [T_COMMA ,] [T_NAME %s]%s]", $2, $3); }
                ;

listaexp        : exp virgulaexp                { sprintf($$,"[listaexp %s%s]", $1, $2); }//-----
                ;

virgulaexp      : /* Empty */                   { strcpy($$,""); }
                | T_COMMA exp virgulaexp        { sprintf($$," [T_COMMA ,] %s [exp%s]", $2, $3); }
                ;

opunaria        : T_MINUS                       { strcpy($$,"[T_MINUS -]");   }
                | T_NOT                         { strcpy($$,"[T_NOT not]");     }
                ;

%%




