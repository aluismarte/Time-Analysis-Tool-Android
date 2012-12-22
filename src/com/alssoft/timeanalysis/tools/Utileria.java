package com.alssoft.timeanalysis.tools;

public class Utileria {
	
	public static Utileria instancia = null;
	
	private Utileria() {}
	
	public static Utileria getInstancia() {
		if(instancia == null) {
			instancia = new Utileria();
		}
		return instancia;
	}
	
	public void ObtenerListadoArchivos() {
		
	}
	
	public void ExplorarCarpeta(String carpeta) {
		
	}
	
	public void CrearDireccionArchivo(String archivo) {
		//Creo el path correspondiente del archivo.
		//Esto puede estar en 2 lugares [Interno y SD]
	}
	
}