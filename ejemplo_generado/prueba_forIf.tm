*      Compilacion TINY para el codigo objeto TM
*      Estudiante: Oriana Bautista C.I: 27.675.632
*      Archivo: ProyectoCompiladores_20241
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> Declaración de variable: x
2:       LDC       0,0(0)      Inicializar x en 0
3:       ST       0,0(5)      Reservar espacio para x
*      <- Declaración de variable: x
*      -> Declaración de variable: i
4:       LDC       0,0(0)      Inicializar i en 0
5:       ST       0,1(5)      Reservar espacio para i
*      <- Declaración de variable: i
*      -> Declaración de variable: op
6:       LDC       0,0(0)      Inicializar op en 0
7:       ST       0,2(5)      Reservar espacio para op
*      <- Declaración de variable: op
*      -> asignacion
*      -> constante
8:       LDC       0,1(0)      cargar constante int: 1
*      <- constante
9:       ST       0,0(5)      asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> for
*      -> constante
10:       LDC       0,1(0)      cargar constante int: 1
*      <- constante
11:       ST       0,1(5)      for: asignar valor inicial
12:       LD       0,1(5)      for: cargar valor de: i
*      -> constante
13:       LDC       0,5(0)      cargar constante int: 5
*      <- constante
14:       SUB       0,1,0      for: comparar variable con el límite
*      -> asignacion
*      -> Operacion: por
*      -> identificador
16:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
17:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
18:       LDC       0,2(0)      cargar constante int: 2
*      <- constante
19:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
20:       MUL       0,1,0      op: *
*      <- Operacion: por
21:       ST       0,0(5)      asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> escribir
*      -> identificador
22:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
23:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
24:       LD       0,1(5)      cargar valor de identificador: i
*      -> identificador
25:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
26:       LDC       0,1(0)      cargar constante int: 1
*      <- constante
27:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
28:       ADD       0,1,0      op: +
*      <- Operacion: mas
29:       ST       0,1(5)      asignacion: almaceno el valor para el id i
*      <- asignacion
*      -> escribir
*      -> identificador
30:       LD       0,1(5)      cargar valor de identificador: i
*      -> identificador
31:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> if
*      -> Operacion: menorIgual
*      -> identificador
32:       LD       0,1(5)      cargar valor de identificador: i
*      -> identificador
33:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
34:       LDC       0,3(0)      cargar constante int: 3
*      <- constante
35:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
36:       SUB       0,1,0      op: <=
37:       JLE       0,2(7)      voy dos instrucciones mas alla if verdadero (AC<=0)
38:       LDC       0,0(0)      caso de falso (AC=0)
39:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
40:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: menorIgual
*      If: el salto hacia el else debe estar aqui
*      -> asignacion
*      -> constante booleana
42:       LDC       0,0(0)      cargar constante boolean: 0
*      <- constante booleana
43:       ST       0,2(5)      asignacion: almaceno el valor para el id op
*      <- asignacion
*      -> escribir
*      -> identificador
44:       LD       0,2(5)      cargar valor de identificador: op
*      -> identificador
45:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      If: el salto hacia el final debe estar aqui
41:       JEQ       0,5(7)      if: jmp hacia else
46:       LDA       7,0(7)      if: jmp hacia el final
*      <- if
47:       LDA       7,-36(7)      for: volver al inicio del for
15:       JGT       0,32(7)      for: salir del ciclo si i > valor final
*      <- for
*      Fin de la ejecucion.
48:       HALT       0,0,0