package timeanalysis.App.Tools;

import android.app.Activity;
import android.widget.Toast;

//Esto puede dar problemas

public class Utileria extends Activity {
	
	public static Utileria instancia = null;
	
	private Utileria() {}
	
	public static Utileria getInstancia() {
		if(instancia == null) {
			instancia = new Utileria();
		}
		return instancia;
	}
	
	public void MostrarTostada(final String tostada) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), tostada, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void CrearDireccionArchivo(final String archivo, final int ubicacion) {
		//Creo el path correspondiente del archivo.
		//Esto puede estar en 2 lugares [Interno y SD]
	}
	
}