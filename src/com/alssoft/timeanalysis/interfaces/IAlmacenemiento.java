package com.alssoft.timeanalysis.interfaces;

import java.io.File;

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
	public abstract void TipoArchivo(String extension);
}