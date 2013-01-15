package com.alssoft.timeanalysis.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.alssoft.timeanalysis.MainActivity;
import com.alssoft.timeanalysis.clasesabstractas.Excel;
import com.alssoft.timeanalysis.interfaces.IAlmacenemiento;
import com.alssoft.timeanalysis.interfaces.ITostadas;

public class SD extends Excel implements IAlmacenemiento,ITostadas {
	
	private String NombreArchivo = "";
	private String Extension = ".xlsx";
	private File Dir;
	private File Archivo;
	private FileOutputStream ArchivoOut;
	
	public SD() {
		if(isExternalStorageReadable() && isExternalStorageWritable()) {
			Dir = new File(Environment.getExternalStorageDirectory(), Carpeta);
			if(Dir.mkdir()) {
				MostrarTostada(MainActivity.BuscarTexto(2));
			}
		}else {
			MostrarTostada(MainActivity.BuscarTexto(3));
		}
	}
	
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
	
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
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
		return this.NombreArchivo;
	}

	@Override
	public void setNombreArchivo(String name) {
		this.NombreArchivo = name;
	}

	@Override
	public File getArchivo() {
		return this.Archivo;
	}
	
	@Override
	public void TipoArchivo(String extension) {
		Extension = extension;
	}
	
	@Override
	public void MostrarTostada(String tostada) {
		Toast.makeText(MainActivity.contexto, tostada, Toast.LENGTH_LONG).show();
	}
	
}