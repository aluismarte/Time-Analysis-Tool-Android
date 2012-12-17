package timeanalysis.App.DB.Modelos;

public class OperacionModelo {
	
	private long Id;
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
	
}