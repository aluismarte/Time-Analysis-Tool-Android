package com.alssoft.timeanalysis.tools;

import java.io.File;
import java.io.IOException;

import com.alssoft.timeanalysis.MainActivity;
import com.alssoft.timeanalysis.clasesabstractas.Excel;
import com.alssoft.timeanalysis.interfaces.IAlmacenemiento;
import com.alssoft.timeanalysis.interfaces.ITostadas;

import jxl.WorkbookSettings;
import jxl.write.WriteException;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

public class SD extends Excel implements IAlmacenemiento,ITostadas {
	
	private String NombreArchivo = "";
	private String Extension = "";
	private File Dir;
	private File Archivo;
	
	public SD() {
		if(isExternalStorageReadable() && isExternalStorageWritable()) {
			Dir = new File(Environment.getExternalStorageDirectory(), Carpeta);
			if(Dir.mkdir()) {
				MostrarTostada(MainActivity.BuscarTexto(2));
			}
			wb = null;
			wbSettings = new WorkbookSettings();
		}else {
			MostrarTostada(MainActivity.BuscarTexto(3));
		}
		
	}
	
	public void PreparoArchivo() {
		if(!"".equals(NombreArchivo)) {
			String ArchivoCompleto = NombreArchivo + Extension;
			Archivo = new File(Dir, ArchivoCompleto);
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
				EscribirArchivo(Archivo);
				//EscribirDatosRecolectados();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			try {
				EscribirArchivo(Archivo);
				//EscribirDatosRecolectados();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void Actualizar() {
		//Falta por implementar.
		//En la siguiente version.
	}

	@Override
	public void Borrar() {
		//Falta por implementar.
		//En la siguiente version.
	}
	
	@Override
	public void DireccionArchivo(Uri direccion) {
		//Aqui no lo implemento.
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
	public void CerrarArchivo() throws WriteException, IOException {
		wb.close();
	}
	
	@Override
	public void TipoArchivo(String extension) {
		//Esta funcion debe permitir poner la extension.
	}
	
	@Override
	public void MostrarTostada(String tostada) {
		Toast.makeText(MainActivity.contexto, tostada, Toast.LENGTH_LONG).show();
	}
	
}