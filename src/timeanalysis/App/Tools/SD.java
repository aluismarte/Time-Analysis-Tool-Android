package timeanalysis.App.Tools;

import java.io.File;
import java.io.IOException;

import jxl.WorkbookSettings;

import timeanalysis.App.MainActivity;
import timeanalysis.App.ClasesAbstractas.Excel;
import timeanalysis.App.Interfaces.IAlmacenemiento;
import timeanalysis.App.Interfaces.ITostadas;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

public class SD extends Excel implements IAlmacenemiento,ITostadas {
	
	private String Carpeta = "/Reportes";
	private String NombreArchivo = "";
	private String Extension = ".xls";
	private File Dir = Environment.getExternalStorageDirectory();
	private File Archivo;
	
	public SD() {
		Dir = new File(MainActivity.contexto.getFilesDir(), Carpeta);
		if(Dir.mkdir()) {
			MostrarTostada("Se creo un directorio en la SD.");
		}
		wb = null;
		wbSettings = new WorkbookSettings();
	}
	
	public void PreparoArchivo() {
		if(!"".equals(NombreArchivo)) {
			String ArchivoCompleto = NombreArchivo + Extension;
			Archivo = new File(Dir, ArchivoCompleto);
		}
	}
	
	@Override
	public void Salvar() {
		if(Archivo == null) {
			PreparoArchivo();
			try {
				EscribirArchivo(Archivo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				EscribirArchivo(Archivo);
			} catch (IOException e) {
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
	public void MostrarTostada(String tostada) {
		Toast.makeText(MainActivity.contexto, tostada, Toast.LENGTH_SHORT).show();
	}
	
}