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
*      -> repeat
*      repeat: el salto hacia el final (luego del cuerpo) del repeat debe estar aqui
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
4:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
5:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
6:       LDC       0,1(0)      cargar constante int: 1
*      <- constante
7:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
8:       ADD       0,1,0      op: +
*      <- Operacion: mas
9:       ST       0,0(5)      asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> escribir
*      -> identificador
10:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
11:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> Operacion: igual
*      -> identificador
12:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
13:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
14:       LDC       0,10(0)      cargar constante int: 10
*      <- constante
15:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
16:       SUB       0,1,0      op: ==
17:       JEQ       0,2(7)      voy dos instrucciones mas alla if verdadero (AC==0)
18:       LDC       0,0(0)      caso de falso (AC=0)
19:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
20:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: igual
21:       JEQ       0,-18(7)      repeat: jmp hacia el inicio del cuerpo
*      <- repeat
*      Fin de la ejecucion.
22:       HALT       0,0,0
