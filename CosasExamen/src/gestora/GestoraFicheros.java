package gestora;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import excepciones.ExcepcionEscritura;
import excepciones.ExcepcionGestionFichero;
import excepciones.ExcepcionLectura;


public class GestoraFicheros{
	public static void main(String[] args){
		Persona p= new Persona("hey",32);
		Persona p2= new Persona("mey",22);
		Persona p3= new Persona("gey",52);
		List<String> lista=null;
		try {
 			anhiadirObjetoAFicheroBinario(p,"hola.dat", true);
			anhiadirObjetoAFicheroBinario(p2,"hola.dat", true);
			anhiadirObjetoAFicheroBinario(p3,"hola.dat", true);
			eliminarPersonaEnFicheroBinario("hola.dat",p);
			p2.setNombre("avocado");
			actualizarPersonaEnFicheroBinario("hola.dat",p2);
			anhiadirObjetoAFicheroBinario(p,"hola.dat", true);
			lista = convertirContenidoFicheroBinarioALista("hola.dat");
		} catch (ExcepcionGestionFichero e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcepcionLectura e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcepcionEscritura e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lista.forEach(x->System.out.println(x));
	}

	/**
	 * @author jjmza
	 * Cabecera:public static boolean anhiadirObjetoAFicheroTexto(Persona p,String path,boolean YaSeQueExiste)
	 * Proposito: Añade un objeto en forma de texto de un fichero de tipo texto
	 * @param:Persona p,String path
	 * @return: boolean anhiadido
	 * Precondición:El objeto persona no debe de ser nulo
	 * Postcondición:Se trata de una función que añade un objeto a un fichero y además devuelve el resultado
	 * con un booleano asociado a su nombre
	 * @throws ExcepcionGestionFichero 
	 * @throws ExcepcionEscritura 
	 * @throws ExcepcionLectura 
	 * */
	public static boolean anhiadirObjetoAFicheroTexto(Persona p,String path,boolean YaSeQueExiste) throws ExcepcionGestionFichero, ExcepcionEscritura, ExcepcionLectura{
		FileWriter fw =null;//declaramos los objetos de las clases necesarias para poder cerrar los flujos en el finally
		BufferedWriter bw=null;
		boolean anhiadido=false;
		if(!YaSeQueExiste || !comprobarSiFicheroExisteTexto(p,path)){//comprobamos si existe el objeto en el fichero fichero para así no tener duplicados
			try {
				fw=new FileWriter(path,true);//abrimos los flujos instanciando los objetos 
				bw=new BufferedWriter(fw);
				bw.write(p.toString());
				anhiadido=true;//si no ha saltado ninguna excepción y por lo tanto el flujo del programa no se ha visto interrumpido, el objeto se habrá anhiadido adecuadamente.
			} catch (IOException e){
				throw new ExcepcionEscritura();
			}finally{//cerramos los flujos comprobando antes si no son nulos
				if(bw!=null){
					try {
						bw.close();
					} catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				if(fw!=null){
					try {
						fw.close();
					} catch (IOException e) {
						throw new ExcepcionGestionFichero();
					}	
				}	
			}
		}
		return anhiadido;
	}
	/**
	 * @author jjmza
	 * Cabecera:public static boolean anhiadirObjetoAFicheroTexto(Persona p,String path,boolean YaSeQueExiste)
	 * Proposito: Añade un objeto en binario a un fichero de tipo binario
	 * @param:Persona p,String path
	 * @return: boolean anhiadido
	 * Precondición:El objeto persona no debe de ser nulo
	 * Postcondición:Se trata de una función que añade un objeto a un fichero y además devuelve el resultado
	 * con un booleano asociado a su nombre
	 * @throws ExcepcionGestionFichero 
	 * @throws ExcepcionEscritura 
	 * @throws ExcepcionLectura 
	 * */
	public static boolean anhiadirObjetoAFicheroBinario(Persona p,String path,boolean YaSeQueExiste) throws ExcepcionGestionFichero, ExcepcionEscritura, ExcepcionLectura{
		File file=null;//declaramos los objetos de las clases necesarias para poder cerrar los flujos en el finally
		FileOutputStream fos = null;
		ObjectOutputStream oos=null;
		boolean anhiadido=false;
		if(!YaSeQueExiste || !comprobarSiFicheroExisteBinario(p,path)){//comprobamos si existe el objeto en el fichero fichero para así no tener duplicados
			try {
				file=new File(path);//abrimos los flujos instanciando los objetos 
				fos=new FileOutputStream(file,true);	
				if(file.length()==0){
					oos=new ObjectOutputStream(fos);
				}else{
					oos=new MyOBjectOutputStream(fos);
				}
				oos.writeObject(p);
				anhiadido=true;//si no ha saltado ninguna excepción y por lo tanto el flujo del programa no se ha visto interrumpido, el objeto se habrá anhiadido adecuadamente.
			} catch (FileNotFoundException e) {
				throw new ExcepcionGestionFichero();
			} catch (IOException e) {
				throw new ExcepcionEscritura();
			}finally{//cerramos los flujos comprobando antes si no son nulos
				if(oos!=null){
					try {
						oos.close();
					} catch (IOException e) {
						throw new ExcepcionGestionFichero();
					}
				}
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {
						throw new ExcepcionGestionFichero();
					}
				}
			}
		}
		return anhiadido; 

	}
	/**
	 * @author jjmza
	 * Cabecera:public static boolean eliminarPersonaEnFicheroBinario(String path,Persona persona)
	 * Proposito: Elimina un objeto de un fichero binario
	 * @param:Persona p,String path
	 * @return: boolean eliminado
	 * Precondición:El objeto persona no debe de ser nulo
	 * Postcondición:Se trata de una función que elimina un objeto de un fichero y además devuelve el resultado
	 * con un booleano asociado a su nombre 
	 * @throws ExcepcionLectura 
	 * @throws ExcepcionEscritura 
	 * @throws ExcepcionGestionFichero 
	 * */
	public static boolean eliminarPersonaEnFicheroBinario(String path,Persona persona) throws ExcepcionGestionFichero, ExcepcionEscritura, ExcepcionLectura{
		FileInputStream fis= null;//declaramos los objetos de las clases necesarias para poder cerrar los flujos en el finally
		ObjectInputStream ois= null;
		File fileO=null;
		File fileF=null;
		Persona personaAux=null;
		String pathAuxiliar=path+"auxiliar";
		boolean eliminado=false;
		try {
			fileO=new File(path);
			fileF=new File(pathAuxiliar);
			fis=new FileInputStream(fileO);
			ois=new ObjectInputStream(fis);
			while((personaAux=(Persona) ois.readObject())!=null){//recorremos y asignamos lo que vamos leyendo del fichero
				if(!personaAux.equals(persona)){
					anhiadirObjetoAFicheroBinario(personaAux, pathAuxiliar,false);
				}
			}
			eliminado=true;//si no ha saltado ninguna excepción y por lo tanto el flujo del programa no se ha visto interrumpido, el objeto se habrá eliminado adecuadamente.
		}catch(EOFException e){
	
		}catch (ClassNotFoundException e){
			throw new ExcepcionGestionFichero();
		}catch (IOException e){
			throw new ExcepcionLectura();
		}
		finally{//cerramos los flujos
				if(ois!=null){
					try {
						ois.close();
					} catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				fileO.delete();
				fileF.renameTo(fileO);
		}
		return eliminado;
	}
	
	/**
	 * @author jjmza
	 * Cabecera:public static boolean eliminarPersonaEnFicheroBinario(String path,Persona persona)
	 * Proposito: Elimina un objeto en un fichero de texto  
	 * @param:Persona p,String path
	 * @return: boolean eliminado
	 * Precondición:El objeto persona no debe de ser nulo
	 * Postcondición:Se trata de una función que elimina un objeto de un fichero y además devuelve el resultado
	 * con un booleano asociado a su nombre 
	 * @throws ExcepcionLectura 
	 * @throws ExcepcionEscritura 
	 * @throws ExcepcionGestionFichero 
	 * */
	public static boolean eliminarObjetoFicheroTexto(Persona persona,String path) throws ExcepcionGestionFichero, ExcepcionEscritura, ExcepcionLectura{
		FileReader fr=null;//declaramos los objetos de las clases necesarias para poder cerrar los flujos en el finally
		BufferedReader br=null;
		String lectura="";
		String pathAuxiliar=path+"auxiliar";
		Persona personaAux=null;
		File fileO=null;
		File fileF=null;
		boolean eliminado=false;
		try {
			fileO=new File(path);
			fr=new FileReader(fileO);
			br=new BufferedReader(fr);
			fileF=new File(pathAuxiliar);
			while((lectura=br.readLine())!=null){
				if(lectura.getClass().getSimpleName().equals("Persona")){
					 personaAux=construirObjeto(lectura);
					if(!personaAux.equals(persona)){
						anhiadirObjetoAFicheroTexto(personaAux, pathAuxiliar,false);
					}
				}
				eliminado=true;//si no ha saltado ninguna excepción y por lo tanto el flujo del programa no se ha visto interrumpido, el objeto se habrá eliminado adecuadamente.
			}
		}catch(IOException e){
			throw new ExcepcionLectura();
		}finally{//cerramos los flujos preguntando previamente si son o no nulos
				if(br!=null){
					try {
						br.close();
					}catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				if(fr!=null){
					try {
						fr.close();
					}catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				fileO.delete();
				fileF.renameTo(fileO);
		}
		return eliminado;
	}
	
	/**
	 * @author jjmza
	 * Cabecera:public static boolean eliminarPersonaEnFicheroBinario(String path,Persona persona)
	 * Proposito: Elimina un objeto de un fichero binario
	 * @param:Persona p,String path
	 * @return: boolean eliminado
	 * Precondición:El objeto persona no debe de ser nulo
	 * Postcondición:Se trata de una función que elimina un objeto de un fichero y además devuelve el resultado
	 * con un booleano asociado a su nombre 
	 * @throws ExcepcionLectura 
	 * @throws ExcepcionEscritura 
	 * @throws ExcepcionGestionFichero 
	 * */
	public static boolean eliminarPersonaEnFicheroBinarioMovimiento(String path,Persona persona) throws ExcepcionGestionFichero, ExcepcionEscritura, ExcepcionLectura{
		FileInputStream fis= null;//declaramos los objetos de las clases necesarias para poder cerrar los flujos en el finally
		ObjectInputStream ois= null;
		File fileO=null;
		File fileF=null;
		Persona personaAux=null;
		String pathAuxiliar=path+"auxiliar";
		boolean eliminado=false;
		try {
			fileO=new File(path);
			fileF=new File(pathAuxiliar);
			fis=new FileInputStream(fileO);
			ois=new ObjectInputStream(fis);
			while((personaAux=(Persona) ois.readObject())!=null){//recorremos y asignamos lo que vamos leyendo del fichero
				if(personaAux.equals(persona)){
					personaAux.setNombre(null);
				}
				anhiadirObjetoAFicheroBinario(personaAux, pathAuxiliar,false);
			}
			eliminado=true;//si no ha saltado ninguna excepción y por lo tanto el flujo del programa no se ha visto interrumpido, el objeto se habrá eliminado adecuadamente.
		}catch(EOFException e){
			
		}catch (ClassNotFoundException e){
			throw new ExcepcionGestionFichero();
		}catch (IOException e){
			throw new ExcepcionLectura();
		}
		finally{//cerramos los flujos
				if(ois!=null){
					try {
						ois.close();
					} catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				fileO.delete();
				fileF.renameTo(fileO);
		}
		return eliminado;
	}
	
	/**
	 * @author jjmza
	 * Cabecera:public static boolean eliminarPersonaEnFicheroBinario(String path,Persona persona)
	 * Proposito: Elimina un objeto en un fichero de texto  
	 * @param:Persona p,String path
	 * @return: boolean eliminado
	 * Precondición:El objeto persona no debe de ser nulo
	 * Postcondición:Se trata de una función que elimina un objeto de un fichero y además devuelve el resultado
	 * con un booleano asociado a su nombre 
	 * @throws ExcepcionLectura 
	 * @throws ExcepcionEscritura 
	 * @throws ExcepcionGestionFichero 
	 * */
	public static boolean eliminarObjetoFicheroTextoMovimiento(Persona persona,String path) throws ExcepcionGestionFichero, ExcepcionEscritura, ExcepcionLectura{
		FileReader fr=null;//declaramos los objetos de las clases necesarias para poder cerrar los flujos en el finally
		BufferedReader br=null;
		String lectura="";
		String pathAuxiliar=path+"auxiliar";
		Persona personaAux=null;
		File fileO=null;
		File fileF=null;
		boolean eliminado=false;
		try {
			fileO=new File(path);
			fr=new FileReader(fileO);
			br=new BufferedReader(fr);
			fileF=new File(pathAuxiliar);
			while((lectura=br.readLine())!=null){
				if(lectura.getClass().getSimpleName().equals("Persona")){
					 personaAux=construirObjeto(lectura);
					if(personaAux.equals(persona)){
						personaAux.setNombre(null);
					}
					anhiadirObjetoAFicheroTexto(personaAux, pathAuxiliar,false);
				}
				eliminado=true;//si no ha saltado ninguna excepción y por lo tanto el flujo del programa no se ha visto interrumpido, el objeto se habrá eliminado adecuadamente.
			}
		}catch(IOException e){
			throw new ExcepcionLectura();
		}finally{//cerramos los flujos preguntando previamente si son o no nulos
				if(br!=null){
					try {
						br.close();
					}catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				if(fr!=null){
					try {
						fr.close();
					}catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				fileF.renameTo(fileO);
				fileO.delete();
		}
		return eliminado;
	}
	
	
	/**
	 * @author jjmza
	 * Cabecera:public static boolean anhiadirObjetoAFicheroTexto(Persona p,String path)
	 * Proposito: Comprueba si un objeto existe en un fichero determinado
	 * @param:Persona p,String path
	 * @return: boolean existe
	 * Precondición:El objeto persona no debe de ser nulo
	 * Postcondición:Se trata de una función que comprueba si un objeto está dentro de un fichero y devuelve el resultado
	 * con un booleano asociado a su nombre
	 * @throws ExcepcionGestionFichero 
	 * @throws ExcepcionLectura 
	 * */
	public static boolean comprobarSiFicheroExisteTexto(Persona p,String path) throws ExcepcionGestionFichero, ExcepcionLectura{//el objeto se puede sustituir por cualquiera
		FileReader fr=null;//declaramos los objetos de las clases necesarias para poder cerrar los flujos en el finally
		BufferedReader br=null;
		boolean existe=false;
		String oLeido=null;
		Object oConstruido=null;
		try {
			fr=new FileReader(path);//abrimos los flujos instanciando los objetos 
			br=new BufferedReader(fr);
			while((oLeido=br.readLine())!=null){
				if(oLeido.split(",")[0].equals(p.getClass().getSimpleName()) && !existe){//comprobamos si dicho objeto es de dicha clase
					oConstruido=construirObjeto(oLeido);//construimos el objeto a partir de la cadena
					if(oConstruido.equals(p)){//comparamos si es igual al objeto introducido por parámetros
						existe=true;//si la condición anterior se ha cumplido significa que ya existe
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw new ExcepcionGestionFichero();
		} catch (IOException e){
			throw new ExcepcionLectura();
		}finally{//cerramos los flujos comprobando antes si no son nulos
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					throw new ExcepcionGestionFichero();
				}
			}
			if(fr!=null){
				try {
					fr.close();
				} catch (IOException e) {
					throw new ExcepcionGestionFichero();
				}

			}
		}
		return existe;
	}
	/**
	 * @author jjmza
	 * Cabecera:public static boolean anhiadirObjetoAFicheroTexto(Persona p,String path)
	 * Proposito: Comprueba si un objeto existe en un fichero determinado
	 * @param:Persona p,String path
	 * @return: boolean existe
	 * Precondición:El objeto persona no debe de ser nulo
	 * Postcondición:Se trata de una función que comprueba si un objeto está dentro de un fichero y devuelve el resultado
	 * con un booleano asociado a su nombre
	 * @throws ExcepcionGestionFichero 
	 * @throws ExcepcionLectura 
	 * */
	public static boolean comprobarSiFicheroExisteBinario(Persona p,String path) throws ExcepcionGestionFichero, ExcepcionLectura{//el objeto se puede sustituir por cualquiera
		File file=null;//declaramos los objetos de las clases necesarias para poder cerrar los flujos en el finally
		FileInputStream fis=null;
		ObjectInputStream ois=null;
		boolean existe=false;
		Object oLeido=null;
		try {
			file=new File(path);//abrimos los flujos instanciando los objetos 
			fis=new FileInputStream(file);
			ois=new ObjectInputStream(fis);
			while((oLeido=(Persona) ois.readObject())!=null && !existe){
				if(oLeido.equals(p)){//comparamos si es igual al objeto introducido por parámetros
					existe=true;
				}
			}
		}catch (FileNotFoundException e) {//si salta file not found y por lo tanto no existe el fichero se crea
			try {
				file.createNewFile();
			} catch (IOException e1) {
				throw new ExcepcionGestionFichero();
			}
		} catch (ClassNotFoundException e){
			throw new ExcepcionGestionFichero();
		}catch(EOFException e){ 
				
		}catch (IOException e) {
			throw new ExcepcionLectura();
		}finally{//cerramos los flujos comprobando antes si no son nulos
			if(ois!=null){
				try {
					ois.close();
				} catch (IOException e) {
					throw new ExcepcionGestionFichero();
				}
			}
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					throw new ExcepcionGestionFichero();
				}
			}

		}
		return existe;
	}
	
	/**
	 * @author jjmza
	 * Cabecera:public static boolean actualizarPersonaEnFicheroBinario(String path,Persona persona)
	 * Proposito: Actualiza un objeto de un fichero binario
	 * @param:String path,Persona persona
	 * @return: boolean actualizado
	 * Precondición:Ninguno
	 * Postcondición:Se trata de una función que actualiza un objeto de un fichero y además devuelve el resultado
	 * con un booleano asociado a su nombre 
	 * @throws ExcepcionLectura 
	 * @throws ExcepcionEscritura 
	 * @throws ExcepcionGestionFichero 
	 * */
	public static boolean actualizarPersonaEnFicheroBinario(String path,Persona persona) throws ExcepcionGestionFichero, ExcepcionEscritura, ExcepcionLectura{
		FileInputStream fis= null;//declaramos los objetos de las clases necesarias para poder cerrar los flujos en el finally
		ObjectInputStream ois= null;
		File fileO=null;
		File fileF=null;
		Persona personaAux=null;
		String pathAuxiliar=path+"auxiliar";
		boolean actualizado=false;
		try {
			fileO=new File(path);
			fileF=new File(pathAuxiliar);
			fis=new FileInputStream(fileO);
			ois=new ObjectInputStream(fis);
			while((personaAux=(Persona) ois.readObject())!=null){//recorremos y asignamos lo que vamos leyendo del fichero
				if(!personaAux.equals(persona)){
					anhiadirObjetoAFicheroBinario(personaAux, pathAuxiliar,false);
				}else{
					anhiadirObjetoAFicheroBinario(persona, pathAuxiliar,false);
				}
			}
			actualizado=true;//si no ha saltado ninguna excepción y por lo tanto el flujo del programa no se ha visto interrumpido, el objeto se habrá actualizado adecuadamente.
		}catch(EOFException e){
	
		}catch (ClassNotFoundException e){
			throw new ExcepcionGestionFichero();
		}catch(FileNotFoundException e){
			throw new ExcepcionGestionFichero();	
		}
		catch (IOException e){
			throw new ExcepcionLectura();
		}
		finally{//cerramos los flujos
				if(ois!=null){
					try {
						ois.close();
					} catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				fileO.delete();
				fileF.renameTo(fileO);
		}
		return actualizado;
	}
	/**
	 * @author jjmza
	 * Cabecera:public static boolean actualizarObjetoFicheroTextoMovimiento(Persona persona,String path)
	 * Proposito: Elimina un objeto en un fichero de texto  
	 * @param:Persona p,String path
	 * @return: boolean actualizado
	 * Precondición:El objeto persona no debe de ser nulo
	 * Postcondición:Se trata de una función que actualiza un objeto de un fichero y además devuelve el resultado
	 * con un booleano asociado a su nombre 
	 * @throws ExcepcionLectura 
	 * @throws ExcepcionEscritura 
	 * @throws ExcepcionGestionFichero 
	 * */
	public static boolean actualizarObjetoFicheroTextoMovimiento(Persona persona,String path) throws ExcepcionGestionFichero, ExcepcionEscritura, ExcepcionLectura{
		FileReader fr=null;//declaramos los objetos de las clases necesarias para poder cerrar los flujos en el finally
		BufferedReader br=null;
		String lectura="";
		String pathAuxiliar=path+"auxiliar";
		Persona personaAux=null;
		File fileO=null;
		File fileF=null;
		boolean eliminado=false;
		try {
			fileO=new File(path);
			fr=new FileReader(fileO);
			br=new BufferedReader(fr);
			fileF=new File(pathAuxiliar);
			while((lectura=br.readLine())!=null){
				if(lectura.getClass().getSimpleName().equals("Persona")){
					 personaAux=construirObjeto(lectura);
					if(!personaAux.equals(persona)){
						anhiadirObjetoAFicheroTexto(personaAux, pathAuxiliar,false);
					}else{
						anhiadirObjetoAFicheroTexto(persona, pathAuxiliar,false);
					}
				}
				eliminado=true;//si no ha saltado ninguna excepción y por lo tanto el flujo del programa no se ha visto interrumpido, el objeto se habrá actualizado adecuadamente.
			}
		}catch(IOException e){
			throw new ExcepcionLectura();
		}finally{//cerramos los flujos preguntando previamente si son o no nulos
				if(br!=null){
					try {
						br.close();
					}catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				if(fr!=null){
					try {
						fr.close();
					}catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
				fileO.delete();
				fileF.renameTo(fileO);
		}
		return eliminado;
	}
	
	
	/**
	 * @author jjmza
	 * Cabecera:public static Persona construirObjeto(String oCadena)
	 * Proposito:Construye una instancia de una clase a partir de una cadena
	 * @param:String oCadena
	 * @return: new Persona()
	 * Precondición:La cadena no debe ser nula o estar vacía
	 * Postcondición:Se trata de una función que devuelve
	 * */
	public static Persona construirObjeto(String oCadena){
		String atributos[]=oCadena.split(",");
		return new Persona(atributos[1],Integer.parseInt(atributos[2]));//Se sustituiria por el constructor del respectivo objeto usando split para dividir la cadena y llamando
		//al constructor por parámetros
	}
	/**
	 * @author jjmza
	 * Cabecera:public static List <String> convertirContenidoFicheroBinarioALista(String path)
	 * Proposito:Se trata de un método que convierte el contenido de un fichero binario a una lista de cadenas
	 * @param:String path
	 * @return: List <String>
	 * Precondición:El path del fichero debe corresponder con uno existente y con contenido binario 
	 * Postcondición:Se trata de una función que devuelve una lista de cadenas
	 * @throws ExcepcionGestionFichero 
	 * @throws ExcepcionLectura 
	 * */
	public static List <String> convertirContenidoFicheroBinarioALista(String path) throws ExcepcionGestionFichero, ExcepcionLectura{//para imprimirlos
		FileInputStream fis=null;//declaramos los objetos de las clases necesarias para poder cerrar los flujos en el finally
		ObjectInputStream ois=null;
		Object o=null;
		List<String> lista=new LinkedList<String>();
		try {
			fis=new FileInputStream(path);//abrimos los flujos instanciando los objetos
			ois=new ObjectInputStream(fis);
			while((o=ois.readObject())!=null){
				lista.add(o.toString());
			}
		} catch (FileNotFoundException e) {
			throw new ExcepcionGestionFichero();
		}catch(EOFException e){ 

		} catch (ClassNotFoundException e) {
			throw new ExcepcionGestionFichero();
		} catch (IOException e) {
			throw new ExcepcionLectura();
		}finally{//cerramos los flujos comprobando antes si no son nulos
			if(ois!=null){
				try {
					ois.close();
				} catch (IOException e) {
					throw new ExcepcionGestionFichero();
				}
			}
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					throw new ExcepcionGestionFichero();
				}
			}
		}
		return lista;
	}
	/**
	 * @author jjmza
	 * Cabecera:public static List<String> convertirContenidoFicheroTextoAList(String path)
	 * Proposito:Se trata de un método que convierte el contenido de un fichero de texto a una lista de cadenas
	 * @param:String path
	 * @return: List <String>
	 * Precondición:El path del fichero debe corresponder con uno existente y con contenido en forma de texto
	 * Postcondición:Se trata de una función que devuelve una lista de cadenas
	 * @throws ExcepcionLectura 
	 * @throws ExcepcionGestionFichero 
	 * */
	public static List<String> convertirContenidoFicheroTextoAList(String path) throws ExcepcionLectura, ExcepcionGestionFichero{
		FileReader fr=null;//declaramos los objetos de las clases necesarias para poder cerrar los flujos en el finally
		BufferedReader br=null;
		List <String> lista=null;
		try {
			fr=new FileReader(path);//abrimos los flujos instanciando los objetos
			br=new BufferedReader(fr);
			lista=br.lines().collect(Collectors.toList());
		}catch(IOException e){
			throw new ExcepcionLectura();
		}finally{//cerramos los flujos comprobando antes si no son nulos
				if(br!=null){
					try {
						br.close();
					} catch (IOException e) {
						throw new ExcepcionGestionFichero();
					}
				}
				if(fr!=null){
					try {
						fr.close();
					} catch (IOException e){
						throw new ExcepcionGestionFichero();
					}
				}
			
		}
		return lista;
	}

}
