package com.alssoft.timeanalysis.tools;

import java.io.IOException;

import jxl.write.WriteException;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.alssoft.timeanalysis.R;
import com.alssoft.timeanalysis.interfaces.IAlmacenemiento;
import com.alssoft.timeanalysis.interfaces.IEspecial;
import com.alssoft.timeanalysis.interfaces.ITostadas;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

public class GoogleDrive extends Activity implements IEspecial,IAlmacenemiento,ITostadas {
	
	static final int Peticion_Cuenta = 1;
	static final int Peticion_Autorizacion = 2;
	//Esto es posible que no se use.
	static final String formatoAndroidExport = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static final String formatoArchivo = "application/vnd.ms-excel";
	
	private static Uri ArchivoUri;
	private static Drive servicio;
	private GoogleAccountCredential credencial;
	private Intent data;
	private static String NombreCuenta;
	private static String NombreArchivo;
	
	public GoogleDrive() {
		Configuracion();
	}
	
	private Drive getDriveServicio(GoogleAccountCredential credential) {
	    return new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential).build();
	}
	
	@Override
	public void EscribirDatosRecolectados() {
		
	}

	@Override
	public void EscribirDatosAnalisis() {
		
	}
	
	@Override
	public void PreparoArchivo() {
		//No implemento aqui.
	}
	
	@Override
	public void Salvar() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//Prepara una ruta de llamada de archivo.
					java.io.File contenidoArchivo = new java.io.File(ArchivoUri.getPath());
					
					//Encuentro la extension y el mimeType
					String fileExtension = MimeTypeMap.getFileExtensionFromUrl(ArchivoUri.toString());
					String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
					
					//Esto carga los datos del archivo.
					FileContent ContenidoMedia = new FileContent(mimeType, contenidoArchivo);
					
					//Prepara la metadata del archivo.
					File cuerpo = new File();
					cuerpo.setTitle(contenidoArchivo.getName());
					cuerpo.setMimeType(mimeType);
					
					File file = servicio.files().insert(cuerpo, ContenidoMedia).execute();
					if (file != null) {
						MostrarTostada(getString(R.string.ArchivoSubidaExito).toString());
					}else {
						MostrarTostada(getString(R.string.OcurrioError).toString());
					}
				} catch (UserRecoverableAuthIOException e) {
					Configuracion();
				} catch (IOException e) {
					MostrarTostada(getString(R.string.ErrorGrave).toString());
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	
	@Override
	public void Actualizar() {
		//No esta implementado.
	}
	
	@Override
	public void Borrar() {
		//No esta implementado.
	}
	
	@Override
	public void DireccionArchivo(Uri direccion) {
		ArchivoUri = direccion;
	}
	
	@Override
	public Uri getArchivoUri() {
		return ArchivoUri;
	}
	
	@Override
	public void Configuracion() {
		credencial = GoogleAccountCredential.usingOAuth2(this, DriveScopes.DRIVE);
		data = credencial.newChooseAccountIntent();
		NombreCuenta = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
		if (NombreCuenta != null) {
			credencial.setSelectedAccountName(NombreCuenta);
			servicio = getDriveServicio(credencial);
		}
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
	public java.io.File getArchivo() {
		//No esta implementado.
		return null;
	}
	
	@Override
	public void CerrarArchivo() throws WriteException, IOException {
		//Aqui cierro el archivo.
	}
	
	public void MostrarTostada(final String tostada) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), tostada, Toast.LENGTH_LONG).show();
			}
		});
	}
	
}