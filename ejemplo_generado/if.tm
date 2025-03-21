
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
*      -> asignacion
*      -> constante
4:       LDC       0,20(0)      cargar constante int: 20
*      <- constante
5:       ST       0,0(5)      asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> if
*      -> Operacion: mayorIgual
*      -> identificador
6:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
7:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
8:       LDC       0,20(0)      cargar constante int: 20
*      <- constante
9:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
10:       SUB       0,1,0      op: >=
11:       JGE       0,2(7)      voy dos instrucciones mas alla if verdadero (AC>0)
12:       LDC       0,0(0)      caso de falso (AC=0)
13:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
14:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: mayorIgual
*      If: el salto hacia el else debe estar aqui
*      -> asignacion
*      -> Operacion: menos
*      -> identificador
16:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
17:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
18:       LDC       0,5(0)      cargar constante int: 5
*      <- constante
19:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
20:       SUB       0,1,0      op: -
*      <- Operacion: menos
21:       ST       0,0(5)      asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> escribir
*      -> identificador
22:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
23:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      If: el salto hacia el final debe estar aqui
15:       JEQ       0,9(7)      if: jmp hacia else
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
25:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
26:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
27:       LDC       0,5(0)      cargar constante int: 5
*      <- constante
28:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
29:       ADD       0,1,0      op: +
*      <- Operacion: mas
30:       ST       0,0(5)      asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> escribir
*      -> identificador
31:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
32:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
24:       LDA       7,8(7)      if: jmp hacia el final
*      <- if
*      Fin de la ejecucion.
33:       HALT       0,0,0      
