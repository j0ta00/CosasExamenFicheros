package menu;

import java.util.Scanner;

public class Menu{
	static Scanner teclado=new Scanner(System.in);
	
	
	/**
	 * Cabecera:public static int MenuPrincipal(int x,int y)
	 * Proposito:Imprime el men� principal y v�lida la respuesta del usuario
	 * @param:int x,int y 
	 * @return:int respuesta
	 * 
	 * Precondici�n:Ninguna
	 * Postcondici�n:Se trata de una funci�n que imprime un men� y valida la respuesta introducida por teclado
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
