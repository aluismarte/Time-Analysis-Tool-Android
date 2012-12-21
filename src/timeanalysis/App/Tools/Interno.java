package timeanalysis.App.Tools;

import java.io.File;
import java.io.IOException;

import jxl.WorkbookSettings;
import jxl.write.WriteException;

import timeanalysis.App.MainActivity;
import timeanalysis.App.ClasesAbstractas.Excel;
import timeanalysis.App.Interfaces.IAlmacenemiento;
import timeanalysis.App.Interfaces.ITostadas;
import android.net.Uri;
import android.widget.Toast;

public class Interno extends Excel implements IAlmacenemiento,ITostadas {
	
	private String base = "/data/data/" + MainActivity.contexto.getPackageName() + "/";
	private String NombreArchivo = "";
	private String Extension = ".xls";
	private File Dir;
	private File Archivo;
	
	public Interno() {
		Dir = new File(base, Carpeta);
		if(Dir.mkdir()) {
			MostrarTostada(MainActivity.BuscarTexto(4));
		}
		wb = null;
		wbSettings = new WorkbookSettings();
	}
	
	@Override
	public void EscribirDatosRecolectados() {
		CrearHoja(wb, MainActivity.BuscarTexto(5), 0);
//		Aqui me valgo de la config para poner esos datos correctamente.
//		for(int i = 0; i < 2; i++) {
//			for(int j = 0; j < 2; j++) {
//				
//			}
//		}
	}
	
	@Override
	public void EscribirDatosAnalisis() {
		//Aqui supone que pongo los calculos estadisticos.
	}
	
	@Override
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
		EscribirDatosRecolectados();
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
	public void CerrarArchivo() throws WriteException, IOException {
		wb.close();
	}
	
	@Override
	public void MostrarTostada(final String tostada) {
		Toast.makeText(MainActivity.contexto, tostada, Toast.LENGTH_SHORT).show();
	}
	
}