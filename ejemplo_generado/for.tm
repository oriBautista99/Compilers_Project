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
*      -> asignacion
*      -> constante
6:       LDC       0,2(0)      cargar constante int: 2
*      <- constante
7:       ST       0,0(5)      asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> for
*      -> constante
8:       LDC       0,1(0)      cargar constante int: 1
*      <- constante
9:       ST       0,1(5)      for: asignar valor inicial
10:       LD       0,1(5)      for: cargar valor de: i
*      -> constante
11:       LDC       0,5(0)      cargar constante int: 5
*      <- constante
12:       SUB       0,1,0      for: comparar variable con el límite
*      -> asignacion
*      -> Operacion: por
*      -> identificador
14:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
15:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
16:       LDC       0,2(0)      cargar constante int: 2
*      <- constante
17:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
18:       MUL       0,1,0      op: *
*      <- Operacion: por
19:       ST       0,0(5)      asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> escribir
*      -> identificador
20:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
21:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
22:       LD       0,1(5)      cargar valor de identificador: i
*      -> identificador
23:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
24:       LDC       0,1(0)      cargar constante int: 1
*      <- constante
25:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
26:       ADD       0,1,0      op: +
*      <- Operacion: mas
27:       ST       0,1(5)      asignacion: almaceno el valor para el id i
*      <- asignacion
*      -> escribir
*      -> identificador
28:       LD       0,1(5)      cargar valor de identificador: i
*      -> identificador
29:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
30:       LDA       7,-21(7)      for: volver al inicio del for
13:       JGT       0,17(7)      for: salir del ciclo si i > valor final
*      <- for
*      Fin de la ejecucion.
31:       HALT       0,0,0