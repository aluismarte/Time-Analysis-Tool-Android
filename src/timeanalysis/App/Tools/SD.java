package timeanalysis.App.Tools;

import java.io.File;
import java.io.IOException;

import jxl.WorkbookSettings;
import jxl.write.WriteException;

import timeanalysis.App.MainActivity;
import timeanalysis.App.R;
import timeanalysis.App.ClasesAbstractas.Excel;
import timeanalysis.App.Interfaces.IAlmacenemiento;
import timeanalysis.App.Interfaces.ITostadas;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

public class SD extends Excel implements IAlmacenemiento,ITostadas {
	
	private String Carpeta = "/TimeAnalysisTool";
	private String NombreArchivo = "";
	private String Extension = ".xls";
	private File Dir;
	private File Archivo;
	
	public SD() {
		if(isExternalStorageReadable() && isExternalStorageWritable()) {
			Dir = new File(Environment.getExternalStorageDirectory(), Carpeta);
			if(Dir.mkdir()) {
				MostrarTostada(getString(R.string.CreoDirectorioSD).toString());
			}
			wb = null;
			wbSettings = new WorkbookSettings();
		}else {
			MostrarTostada(getString(R.string.SDNoDisponible).toString());
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
	public void EscribirDatosRecolectados() {
		
	}

	@Override
	public void EscribirDatosAnalisis() {
		
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

	@Override
	public void MostrarTostada(String tostada) {
		Toast.makeText(MainActivity.contexto, tostada, Toast.LENGTH_SHORT).show();
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
	
}