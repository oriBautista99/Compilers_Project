*      Compilacion TINY para el codigo objeto TM
*      Estudiante: Oriana Bautista C.I: 27.675.632
*      Archivo: ProyectoCompiladores_20241
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> escribir
*      -> Operacion: mas
*      -> constante
2:       LDC       0,1(0)      cargar constante int: 1
*      <- constante
3:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> Operacion: por
*      -> constante
4:       LDC       0,2(0)      cargar constante int: 2
*      <- constante
5:       ST       0,-1(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
6:       LDC       0,3(0)      cargar constante int: 3
*      <- constante
7:       LD       1,-1(6)      op: pop o cargo de la pila el valor izquierdo en AC1
8:       MUL       0,1,0      op: *
*      <- Operacion: por
9:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
10:       ADD       0,1,0      op: +
*      <- Operacion: mas
11:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
12:       HALT       0,0,0
