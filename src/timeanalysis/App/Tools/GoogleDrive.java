package timeanalysis.App.Tools;

import timeanalysis.App.Interfaces.IAlmacenemiento;
import timeanalysis.App.Interfaces.IEspecial;
import java.io.IOException;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

public class GoogleDrive extends Activity implements IEspecial,IAlmacenemiento {
	
	static final int Peticion_Cuenta = 1;
	static final int Peticion_Autorizacion = 2;
	static final String formatoArchivo = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	private static Uri ArchivoUri;
	private static Drive servicio;
	private GoogleAccountCredential credencial;
	private Intent data;
	private String NombreCuenta;
	
	public GoogleDrive() {
		Configuracion();
	}
	
	private Drive getDriveServicio(GoogleAccountCredential credential) {
	    return new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential).build();
	}
	
	@Override
	public void Salvar() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//Prepara una ruta de llamada de archivo.
					java.io.File contenidoArchivo = new java.io.File(ArchivoUri.getPath());
					//Esto carga los datos del archivo.
					FileContent ContenidoMedia = new FileContent(formatoArchivo, contenidoArchivo);
					
					//Prepara la metadata del archivo.
					File cuerpo = new File();
					cuerpo.setTitle(contenidoArchivo.getName());
					cuerpo.setMimeType(formatoArchivo);
					
					File file = servicio.files().insert(cuerpo, ContenidoMedia).execute();
					if (file != null) {
						Utileria.getInstancia().MostrarTostada("Se subio el archivo con exito.");
					}else {
						Utileria.getInstancia().MostrarTostada("Ocurio un error.");
					}
				} catch (UserRecoverableAuthIOException e) {
					Configuracion();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	
	@Override
	public void Actualizar() {
		
	}
	
	@Override
	public void Borrar() {
		
	}
	
	@Override
	public void Archivo(Uri direccion) {
		ArchivoUri = direccion;
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
	
}