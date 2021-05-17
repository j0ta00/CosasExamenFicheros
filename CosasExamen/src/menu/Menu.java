package menu;

import java.util.Scanner;

public class Menu{
	static Scanner teclado=new Scanner(System.in);
	static final String ERROR1="Se ha producido un fallo en la lectura del fichero";
	static final String ERROR2="Se ha producido un fallo en la escritura del fichero";
	static final String ERROR3="Se ha producido un fallo en la gestión del fichero";
	
	/**
	 * Cabecera:public static int MenuPrincipal(int x,int y)
	 * Proposito:Imprime el menú principal y válida la respuesta del usuario
	 * @param:int x,int y 
	 * @return:int respuesta
	 * 
	 * Precondición:Ninguna
	 * Postcondición:Se trata de una función que imprime un menú y valida la respuesta introducida por teclado
	 * */
	public static int MenuPrincipal(int x,int y){
		int respuesta=0;
		while(respuesta<x && respuesta>y){
			System.out.println();
			System.out.println();
			respuesta=teclado.nextInt();
		}
		return respuesta;
	}

}

public static void imprimirError(int numeroError){
switch(numeroError){
	case 1->System.out.println(ERROR1);
	case 2->System.out.println(ERROR2);
	case 3->System.out.println(ERROR3);
}

}
