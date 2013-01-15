package com.alssoft.timeanalysis.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.net.Uri;
import android.widget.Toast;

import com.alssoft.timeanalysis.MainActivity;
import com.alssoft.timeanalysis.clasesabstractas.Excel;
import com.alssoft.timeanalysis.interfaces.IAlmacenemiento;
import com.alssoft.timeanalysis.interfaces.ITostadas;

public class Interno extends Excel implements IAlmacenemiento,ITostadas {
	
	private String base = "/data/data/" + MainActivity.contexto.getPackageName() + "/";
	private String NombreArchivo = "";
	private String Extension = ".xlsx";
	private File Dir;
	private File Archivo;
	private FileOutputStream ArchivoOut;
	
	public Interno() {
		Dir = new File(base, Carpeta);
		if(Dir.mkdir()) {
			MostrarTostada(MainActivity.BuscarTexto(4));
		}
	}
	
	@Override
	public void PreparoArchivo() {
		if(!"".equals(NombreArchivo)) {
			String ArchivoCompleto = NombreArchivo + Extension;
			Archivo = new File(Dir, ArchivoCompleto);
			try {
				ArchivoOut = new FileOutputStream(Archivo);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void Salvar() {
		if(Archivo == null) {
			PreparoArchivo();
			try {
				EscribirArchivoExcel2007(ArchivoOut);
				//EscribirDatosRecolectados();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			try {
				EscribirArchivoExcel2007(ArchivoOut);
				//EscribirDatosRecolectados();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void Actualizar() {
		// Falta por implementar.
		// En la siguiente version.
	}

	@Override
	public void Borrar() {
		// Falta por implementar.
		// En la siguiente version.
	}
	
	@Override
	public void DireccionArchivo(Uri direccion) {
		// Aqui no lo implemento.
	}
	
	@Override
	public Uri getArchivoUri() {
		return Uri.fromFile(Archivo);
	}
	
	@Override
	public String getNombreArchivo() {
		return NombreArchivo;
	}
	
	@Override
	public void setNombreArchivo(String name) {
		NombreArchivo = name;
	}
	
	@Override
	public File getArchivo() {
		return Archivo;
	}
	
	@Override
	public void TipoArchivo(String extension) {
		Extension = extension;
	}
	
	@Override
	public void MostrarTostada(final String tostada) {
		Toast.makeText(MainActivity.contexto, tostada, Toast.LENGTH_LONG).show();
	}
	
}