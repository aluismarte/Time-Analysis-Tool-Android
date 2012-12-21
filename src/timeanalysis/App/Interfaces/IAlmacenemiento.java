package timeanalysis.App.Interfaces;

import java.io.File;
import java.io.IOException;

import jxl.write.WriteException;

import android.net.Uri;

public interface IAlmacenemiento {
	public abstract void Salvar();
	public abstract void Actualizar();
	public abstract void Borrar();
	public abstract void DireccionArchivo(Uri direccion);
	public abstract Uri getArchivoUri();
	public abstract String getNombreArchivo();
	public abstract void setNombreArchivo(String name);
	public abstract File getArchivo();
	public abstract void PreparoArchivo();
	public abstract void CerrarArchivo() throws WriteException, IOException;
	public abstract void EscribirDatosRecolectados();
	public abstract void EscribirDatosAnalisis();
}