package ve.edu.unet;

import com.sun.org.apache.xml.internal.utils.NodeVector;
import ve.edu.unet.nodosAST.*;

public class Generador {
	/* Ilustracion de la disposicion de la memoria en
	 * este ambiente de ejecucion para el lenguaje Tiny
	 *
	 * |t1	|<- mp (Maxima posicion de memoria de la TM
	 * |t1	|<- desplazamientoTmp (tope actual)
	 * |free|
	 * |free|
	 * |...	|
	 * |x	|
	 * |y	|<- gp
	 * 
	 * */
	
	
	
	/* desplazamientoTmp es una variable inicializada en 0
	 * y empleada como el desplazamiento de la siguiente localidad
	 * temporal disponible desde la parte superior o tope de la memoria
	 * (la que apunta el registro MP).
	 * 
	 * - Se decrementa (desplazamientoTmp--) despues de cada almacenamiento y
	 * 
	 * - Se incrementa (desplazamientoTmp++) despues de cada eliminacion/carga en 
	 *   otra variable de un valor de la pila.
	 * 
	 * Pudiendose ver como el apuntador hacia el tope de la pila temporal
	 * y las llamadas a la funcion emitirRM corresponden a una inserccion 
	 * y extraccion de esta pila
	 */
	private static int desplazamientoTmp = 0;
	private static TablaSimbolos tablaSimbolos = null;
	
	public static void setTablaSimbolos(TablaSimbolos tabla){
		tablaSimbolos = tabla;
	}
	
	public static void generarCodigoObjeto(NodoBase raiz){
		System.out.println();
		System.out.println();
		System.out.println("------ CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
		System.out.println();
		System.out.println();
		generarPreludioEstandar();
		generar(raiz);
		/*Genero el codigo de finalizacion de ejecucion del codigo*/   
		UtGen.emitirComentario("Fin de la ejecucion.");
		UtGen.emitirRO("HALT", 0, 0, 0, "");
		System.out.println();
		System.out.println();
		System.out.println("------ FIN DEL CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
	}
	
	//Funcion principal de generacion de codigo
	//prerequisito: Fijar la tabla de simbolos antes de generar el codigo objeto 
	private static void generar(NodoBase nodo){
	if(tablaSimbolos!=null){
		if (nodo instanceof  NodoIf){
			generarIf(nodo);
		}else if (nodo instanceof  NodoRepeat){
			generarRepeat(nodo);
		}else if (nodo instanceof  NodoDeclaracion){
			generarDeclaracion(nodo);
		}else if (nodo instanceof  NodoDeclaracionVector){
			generarDeclaracionVector(nodo);
		}else if (nodo instanceof  NodoAsignacion){
			generarAsignacion(nodo);
		}else  if (nodo instanceof NodoAsignacionVector){
			generarVector(nodo);
		}else if (nodo instanceof  NodoLeer){
			generarLeer(nodo);
		}else if (nodo instanceof  NodoEscribir){
			generarEscribir(nodo);
		}else if (nodo instanceof NodoValor){
			generarValor(nodo);
		}else if (nodo instanceof NodoBool){
			generarValor(nodo);
		}else if (nodo instanceof NodoIdentificador){
			generarIdentificador(nodo);
		}else if (nodo instanceof NodoFor){
			generarFor(nodo);
		}else if (nodo instanceof NodoOperacion){
			generarOperacion(nodo);
		}else{
			System.out.println("BUG: Tipo de nodo a generar desconocido");
		}
		/*Si el hijo de extrema izquierda tiene hermano a la derecha lo genero tambien*/
		if(nodo.TieneHermano())
			generar(nodo.getHermanoDerecha());
	}else
		System.out.println("¡¡¡ERROR: por favor fije la tabla de simbolos a usar antes de generar codigo objeto!!!");
}

	private static void generarDeclaracion(NodoBase nodo){
		NodoDeclaracion n = (NodoDeclaracion)nodo;
		String nombre = n.getName();
		int direc = tablaSimbolos.getDireccion(nombre);
		// Generar código para reservar espacio en memoria
		UtGen.emitirComentario("-> Declaración de variable: " + nombre);
		UtGen.emitirRM("LDC", UtGen.AC, 0, 0, "Inicializar " + nombre + " en 0");
		UtGen.emitirRM("ST", UtGen.AC, direc, UtGen.GP, "Reservar espacio para " + nombre);
		UtGen.emitirComentario("<- Declaración de variable: " + nombre);
	}

	private static void generarDeclaracionVector(NodoBase nodo){
		NodoDeclaracionVector n = (NodoDeclaracionVector)nodo;
		if (UtGen.debug) {
			UtGen.emitirComentario("-> Declaración de vector: " + n.getName());
		}

		// Generar código para la expresión del tamaño del vector
		generar(n.getSizeVector());

		// Obtener el tamaño del vector del acumulador (AC)
		UtGen.emitirRM("ST", UtGen.AC, UtGen.MP, UtGen.GP, "Almacenar tamaño del vector en memoria temporal");

		// Calcular la dirección de inicio del vector
		int direccionInicio = tablaSimbolos.getDireccion(n.getName());

		// Asignar espacio en memoria para el vector
		UtGen.emitirRM("LD", UtGen.AC, UtGen.MP, UtGen.GP, "Cargar tamaño del vector desde memoria temporal");
		UtGen.emitirRM("LDA", UtGen.GP, UtGen.AC, UtGen.GP, "Reservar espacio para el vector");

		if (UtGen.debug) {
			UtGen.emitirComentario("<- Declaración de vector: " + n.getName());
		}
	}

	private static void generarIf(NodoBase nodo){
    	NodoIf n = (NodoIf)nodo;
		int localidadSaltoElse,localidadSaltoEnd,localidadActual;
		if(UtGen.debug)	UtGen.emitirComentario("-> if");
		/*Genero el codigo para la parte de prueba del IF*/
		generar(n.getPrueba());
		localidadSaltoElse = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el else debe estar aqui");
		/*Genero la parte THEN*/
		generar(n.getParteThen());
		localidadSaltoEnd = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el final debe estar aqui");
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoElse);
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual, "if: jmp hacia else");
		UtGen.restaurarRespaldo();
		/*Genero la parte ELSE*/
		if(n.getParteElse()!=null){
			generar(n.getParteElse());
    	}
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoEnd);
		UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadActual, "if: jmp hacia el final");
		UtGen.restaurarRespaldo();
		if(UtGen.debug)	UtGen.emitirComentario("<- if");
	}
	
	private static void generarRepeat(NodoBase nodo){
    	NodoRepeat n = (NodoRepeat)nodo;
		int localidadSaltoInicio;
		if(UtGen.debug)	UtGen.emitirComentario("-> repeat");
			localidadSaltoInicio = UtGen.emitirSalto(0);
			UtGen.emitirComentario("repeat: el salto hacia el final (luego del cuerpo) del repeat debe estar aqui");
			/* Genero el cuerpo del repeat */
			generar(n.getCuerpo());
			/* Genero el codigo de la prueba del repeat */
			generar(n.getPrueba());
			UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadSaltoInicio, "repeat: jmp hacia el inicio del cuerpo");
		if(UtGen.debug)	UtGen.emitirComentario("<- repeat");
	}

	private static void generarFor(NodoBase nodo) {
		NodoFor n = (NodoFor)nodo;
		int initLoop;
		int endLoop;
		int direccionVar;

		if (UtGen.debug) {
			UtGen.emitirComentario("-> for");
		}

		// Dirección de la variable de control
		direccionVar = tablaSimbolos.getDireccion(n.getVariable());

		// Generar código para la inicialización y asignarlo
		generar(n.getInit());
		UtGen.emitirRM("ST", UtGen.AC, direccionVar, UtGen.GP, "for: asignar valor inicial");

		// Etiquetar inicio del bucle
		initLoop = UtGen.emitirSalto(0);

		// Evaluar condición: variable <= final
		UtGen.emitirRM("LD", UtGen.AC, direccionVar, UtGen.GP, "for: cargar valor de: " + n.getVariable());
		// Código para el valor final
		generar(n.getEnd());
		UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "for: comparar variable con el límite");

		// Salto condicional a la salida
		endLoop = UtGen.emitirSalto(1);

		// Generar código para el cuerpo
		generar(n.getSent());

		// Saltar al inicio
		UtGen.emitirRM_Abs("LDA", UtGen.PC, initLoop, "for: volver al inicio del for");

		// Poner etiqueta de salida
		int locationActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(endLoop);
		UtGen.emitirRM_Abs("JGT", UtGen.AC, locationActual, "for: salir del ciclo si " + n.getVariable() + " > valor final");
		UtGen.restaurarRespaldo();

		if (UtGen.debug) {
			UtGen.emitirComentario("<- for");
		}
	}
	
	private static void generarAsignacion(NodoBase nodo){
		if( nodo instanceof  NodoAsignacionVector) {
			NodoAsignacionVector nodoVec = (NodoAsignacionVector)nodo;

			if(UtGen.debug) UtGen.emitirComentario("-> asignacion vector");
			// genera codigo para la empresion del indice
			generar(nodoVec.getIndex());
			// carga direccion base del vector
			UtGen.emitirRM("LD",UtGen.AC1, tablaSimbolos.getDireccion(nodoVec.getIdentificador()), UtGen.GP, "Carga direccion base del vector");

			// Calcular direccion real del vector


			//Generar codigo para la expresion a asignar
			generar(nodoVec.getExpresion());
			//Guardar el valor
			UtGen.emitirRM("ST", UtGen.AC,0,UtGen.AC, "Guardar valor en vector");

			if(UtGen.debug) UtGen.emitirComentario("<- asignacion vector");
		} else {
			NodoAsignacion n = (NodoAsignacion)nodo;
			int direccion;
			if(UtGen.debug)	UtGen.emitirComentario("-> asignacion");
			/* Genero el codigo para la expresion a la derecha de la asignacion */
			generar(n.getExpresion());
			/* Ahora almaceno el valor resultante */
			direccion = tablaSimbolos.getDireccion(n.getIdentificador());
			UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "asignacion: almaceno el valor para el id "+n.getIdentificador());
			if(UtGen.debug)	UtGen.emitirComentario("<- asignacion");
		}
	}
	
	private static void generarLeer(NodoBase nodo){
		NodoLeer n = (NodoLeer)nodo;
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> leer");
		UtGen.emitirRO("IN", UtGen.AC, 0, 0, "leer: lee un valor ");
		direccion = tablaSimbolos.getDireccion(n.getIdentificador());
		UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "leer: almaceno el valor leido en el id "+n.getIdentificador());
		if(UtGen.debug)	UtGen.emitirComentario("<- leer");
	}
	
	private static void generarEscribir(NodoBase nodo){
		NodoEscribir n = (NodoEscribir)nodo;
		if (n.getExpresion() == null) {
			System.out.println("Error: La expresión en WRITE es nula.");
			return;
		}
		if(UtGen.debug)	UtGen.emitirComentario("-> escribir");
		/* Genero el codigo de la expresion que va a ser escrita en pantalla */
		generar(n.getExpresion());
		/* Ahora genero la salida */
		UtGen.emitirRO("OUT", UtGen.AC, 0, 0, "escribir: genero la salida de la expresion");
		if(UtGen.debug)	UtGen.emitirComentario("<- escribir");
	}
	
	private static void generarValor(NodoBase nodo){
		if( nodo instanceof NodoValor){
			NodoValor n = (NodoValor)nodo;
			if(UtGen.debug)	UtGen.emitirComentario("-> constante");
			UtGen.emitirRM("LDC", UtGen.AC, n.getValor(), 0, "cargar constante int: "+n.getValor());
			if(UtGen.debug)	UtGen.emitirComentario("<- constante");
		}else if (nodo instanceof NodoBool) {
			NodoBool n = (NodoBool) nodo;
			int valorBool = n.getValor() ? 1 : 0; // Convertir boolean a entero
			if (UtGen.debug) UtGen.emitirComentario("-> constante booleana");
			UtGen.emitirRM("LDC", UtGen.AC, valorBool, 0, "cargar constante boolean: " + valorBool);
			if (UtGen.debug) UtGen.emitirComentario("<- constante booleana");
		} else {
			System.err.println("Error en generarValor: Nodo desconocido");
		}

	}
	
	private static void generarIdentificador(NodoBase nodo){
		NodoIdentificador n = (NodoIdentificador)nodo;
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> identificador");
		direccion = tablaSimbolos.getDireccion(n.getNombre());
		UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP, "cargar valor de identificador: "+n.getNombre());
		if(UtGen.debug)	UtGen.emitirComentario("-> identificador");
	}

	private static void generarVector(NodoBase nodo){
		NodoAsignacionVector n = (NodoAsignacionVector)nodo;
		// genero codigo de exprecion -> el indice
		generar(n.getExpresion());
		//cargo direccion base del vector
		UtGen.emitirRM("LD", UtGen.AC1, tablaSimbolos.getDireccion(n.getIdentificador()), UtGen.GP, "Cargo direccion base del vector");
		//Calculo direccion real

		// Cargar el valor del vector
		UtGen.emitirRM("LD", UtGen.AC, 0, UtGen.AC, "Cargar valor del vector");
	}

	private static void generarOperacion(NodoBase nodo){
		NodoOperacion n = (NodoOperacion) nodo;
		if(UtGen.debug)	UtGen.emitirComentario("-> Operacion: " + n.getOperacion());
		/* Genero la expresion izquierda de la operacion */
		generar(n.getOpIzquierdo());
		/* Almaceno en la pseudo pila de valor temporales el valor de la operacion izquierda */
		UtGen.emitirRM("ST", UtGen.AC, desplazamientoTmp--, UtGen.MP, "op: push en la pila tmp el resultado expresion izquierda");
		/* Genero la expresion derecha de la operacion */
		generar(n.getOpDerecho());
		/* Ahora cargo/saco de la pila el valor izquierdo */
		UtGen.emitirRM("LD", UtGen.AC1, ++desplazamientoTmp, UtGen.MP, "op: pop o cargo de la pila el valor izquierdo en AC1");
		switch(n.getOperacion()){
			case	mas:
							UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC1, UtGen.AC, "op: +");
							break;
			case	menos:
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: -");
							break;
			case	por:
							UtGen.emitirRO("MUL", UtGen.AC, UtGen.AC1, UtGen.AC, "op: *");
							break;
			case	entre:
							UtGen.emitirRO("DIV", UtGen.AC, UtGen.AC1, UtGen.AC, "op: /");
							break;
			case	menor:
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <");
							UtGen.emitirRM("JLT", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;
			case	igual:
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: ==");
							UtGen.emitirRM("JEQ", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC==0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;
			case	mayor:
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >");
							UtGen.emitirRM("JGT", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC>0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;
			case	menorIgual:
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <=");
							UtGen.emitirRM("JLE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<=0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;
			case 	mayorIgual:
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >=");
							UtGen.emitirRM("JGE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC>0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;

			case  	diferente:
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <>");
							UtGen.emitirRM("JNE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<>0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;

				/* Logicos */
				case and:
					UtGen.emitirRO("AND", UtGen.AC, UtGen.AC1, UtGen.AC, "OP: AND");
					break;
				case or:
					UtGen.emitirRO("OR", UtGen.AC, UtGen.AC1, UtGen.AC, "OP: OR");
					break;

			default:
				UtGen.emitirComentario("BUG: tipo de operacion desconocida");
		}
		if(UtGen.debug)	UtGen.emitirComentario("<- Operacion: " + n.getOperacion());
	}
	
	//TODO: enviar preludio a archivo de salida, obtener antes su nombre
	private static void generarPreludioEstandar(){
		UtGen.emitirComentario("Compilacion TINY para el codigo objeto TM");
		UtGen.emitirComentario("Estudiante: "+ "Oriana Bautista C.I: 27.675.632");
		UtGen.emitirComentario("Archivo: "+ "ProyectoCompiladores_20241");
		/*Genero inicializaciones del preludio estandar*/
		/*Todos los registros en tiny comienzan en cero*/
		UtGen.emitirComentario("Preludio estandar:");
		UtGen.emitirRM("LD", UtGen.MP, 0, UtGen.AC, "cargar la maxima direccion desde la localidad 0");
		UtGen.emitirRM("ST", UtGen.AC, 0, UtGen.AC, "limpio el registro de la localidad 0");
	}

}
