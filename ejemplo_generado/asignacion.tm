*      Compilacion TINY para el codigo objeto TM
*      Estudiante: Oriana Bautista C.I: 27.675.632
*      Archivo: ProyectoCompiladores_20241
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> Declaración de variable: i
2:       LDC       0,0(0)      Inicializar i en 0
3:       ST       0,0(5)      Reservar espacio para i
*      <- Declaración de variable: i
*      -> asignacion
*      -> constante
4:       LDC       0,3(0)      cargar constante int: 3
*      <- constante
5:       ST       0,0(5)      asignacion: almaceno el valor para el id i
*      <- asignacion
*      -> Declaración de variable: t
6:       LDC       0,0(0)      Inicializar t en 0
7:       ST       0,1(5)      Reservar espacio para t
*      <- Declaración de variable: t
*      -> asignacion
*      -> constante booleana
8:       LDC       0,0(0)      cargar constante boolean: 0
*      <- constante booleana
9:       ST       0,1(5)      asignacion: almaceno el valor para el id t
*      <- asignacion
*      -> escribir
*      -> identificador
10:       LD       0,0(5)      cargar valor de identificador: i
*      -> identificador
11:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> escribir
*      -> identificador
12:       LD       0,1(5)      cargar valor de identificador: t
*      -> identificador
13:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
14:       HALT       0,0,0