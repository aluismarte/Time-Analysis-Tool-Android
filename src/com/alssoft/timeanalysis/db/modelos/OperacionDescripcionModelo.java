package com.alssoft.timeanalysis.db.modelos;

public class OperacionDescripcionModelo {
	
	private long Id;
	private long IdOperacion;
	private String Nombre;
	
	public void setId(long id) {
		this.Id = id;
	}
	
	public long getId() {
		return this.Id;
	}
	
	public void setNombre(String nombre) {
		this.Nombre = nombre;
	}
	
	public String getNombre() {
		return this.Nombre;
	}
	
	public void setIdOperacion(long idperacion) {
		this.IdOperacion = idperacion;
	}
	
	public long getIdOperacion() {
		return this.IdOperacion;
	}
	
}