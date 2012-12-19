package timeanalysis.App.Tools;

import android.annotation.SuppressLint;
import android.app.Activity;

@SuppressLint("Instantiatable")
public class Utileria extends Activity {
	
	public static Utileria instancia = null;
	
	private Utileria() {}
	
	public static Utileria getInstancia() {
		if(instancia == null) {
			instancia = new Utileria();
		}
		return instancia;
	}
	
	public void CrearDireccionArchivo(final String archivo, final int ubicacion) {
		//Creo el path correspondiente del archivo.
		//Esto puede estar en 2 lugares [Interno y SD]
	}
	
}