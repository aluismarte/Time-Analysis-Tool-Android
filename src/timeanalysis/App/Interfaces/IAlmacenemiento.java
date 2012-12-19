package timeanalysis.App.Interfaces;

import android.net.Uri;

public interface IAlmacenemiento {
	public void Salvar();
	public void Actualizar();
	public void Borrar();
	void DireccionArchivo(Uri direccion);
	public Uri getArchivoUri();
}