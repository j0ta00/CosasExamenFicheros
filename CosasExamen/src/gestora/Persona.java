package gestora;

import java.io.Serializable;

public class Persona implements Serializable,Comparable<Persona>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private int edad;
	
	public Persona(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}
	
	public String getNombre() {
		return nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}

	
	@Override
	public boolean equals(Object obj) {
		boolean igual=false;
		Persona p=(Persona) obj;
		if (this == obj){
			igual=true;
		}else if(obj instanceof Persona && p.edad==this.edad){
			igual=true;
		}	
		return igual;
	}
	@Override
	public int compareTo(Persona p) {
		return p.edad==this.edad ? 0 : p.edad<this.edad  ? 1 : -1;
	}

	@Override
	public String toString() {
		return new StringBuilder(this.getClass().getSimpleName()).append(",").
				append(nombre).append(",").append(edad).toString();
	}
	

}
