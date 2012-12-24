package com.alssoft.timeanalysis;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.alssoft.timeanalysis.db.Manejador;
import com.alssoft.timeanalysis.interfaces.IAlmacenemiento;
import com.alssoft.timeanalysis.interfaces.ITostadas;
import com.alssoft.timeanalysis.tools.Interno;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements ITostadas {
	
	public static Context contexto;
	private static IAlmacenemiento DispositivoSalvado;
	private static Resources ressources;
	private static TabHost tabHost;
	private static int repeticiones;
	
	//Menu Objetos
	private static Button BotonSalvar;
	private static EditText NombreArchivoSalvar;
	private static Spinner spinner;
	private static ArrayAdapter<CharSequence> adapterSpinner;
	
	static String Carpeta, CrearDirSD, CrearDirInterno, NombreHoja, NoDisSD;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		InstanciarStrings();
		contexto = this;
		
		//Seteo por defecto el interno.
		setDispositivoSalvado(new Interno());
		
		//Preparo tabs a la antigua
		//Es posible hacer una implementacion moderna.
		//Solo hay que usar el chequeo de versiones
		PrepararTabs();
	}
	
	private void InstanciarStrings() {
		Carpeta = getString(R.string.NombreCarpeta).toString();
		CrearDirSD = getString(R.string.CreoDirectorioSD).toString();
		CrearDirInterno = getString(R.string.CreoDirectorioInterno).toString();
		NombreHoja = getString(R.string.NombreHoja).toString();
		NoDisSD = getString(R.string.SDNoDisponible).toString();
	}
	
	private void Abrir() {
		//Este no se agrega al string
		MostrarTostada("Todavia no es posible abrir archivos.");
	}
	
	private void Salvar() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_salvado);
		dialog.setTitle(getString(R.string.SalvarTitulo).toString());
		
		NombreArchivoSalvar = (EditText) dialog.findViewById(R.id.NombreArchivoSalvar);
		BotonSalvar = (Button) dialog.findViewById(R.id.BotonSalvarMenu);
		spinner = (Spinner) dialog.findViewById(R.id.spinner1);
		
		adapterSpinner = ArrayAdapter.createFromResource(this,R.array.TipoArchivos, android.R.layout.simple_spinner_item);
		adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapterSpinner);
		
		//Esto lo convierto y trabajo con el spinner.getSelectedItem();
		
		BotonSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!"".equals(NombreArchivoSalvar.getText())) {
					DispositivoSalvado.setNombreArchivo(NombreArchivoSalvar.getText().toString());
					DispositivoSalvado.Salvar();
					MostrarTostada(getString(R.string.ExitoArchivo).toString());
					NombreArchivoSalvar.setText("");
					dialog.dismiss();
				} else {
					MostrarTostada(getString(R.string.InserteNombre).toString());
				}
			}
		});
		
		if(CapturaActivity.getDatos().isEmpty()) {
			BotonSalvar.setEnabled(false);
		}else {
			BotonSalvar.setEnabled(true);
		}
		
		dialog.show();
	}
	
	private void Acerca() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.about).toString();
		builder.setTitle(R.string.AboutTitulo).toString();
		builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               dialog.dismiss();
	           }
	    });
		builder.show();
	}
	
	private void Configurar() {
		//Este no se agrega al string
		MostrarTostada("Todavia no se tiene posibilidad de configurar.");
	}
	
	public void PrepararTabs() {
		ressources = getResources(); 
		tabHost = getTabHost();
		
		Intent intentCaptura = new Intent().setClass(this, CapturaActivity.class);
		
		TabSpec tabSpecCaptura = tabHost
				.newTabSpec("Captura")
				.setIndicator("", ressources.getDrawable(R.drawable.icon_captura))
				.setContent(intentCaptura);
		
		Intent intentOperacion = new Intent().setClass(this, OperacionActivity.class);
		
		TabSpec tabSpecOperacion = tabHost
				.newTabSpec("Operaciones")
				.setIndicator("", ressources.getDrawable(R.drawable.icon_operacion))
				.setContent(intentOperacion);
		
		Intent intentReporte = new Intent().setClass(this, ReporteActivity.class);
		
		TabSpec tabSpecReporte = tabHost
				.newTabSpec("Reportes")
				.setIndicator("", ressources.getDrawable(R.drawable.icon_reporte))
				.setContent(intentReporte);
		
		Intent intentSalvado = new Intent().setClass(this, SalvadoActivity.class);
		
		TabSpec tabSpecTipoSalvado = tabHost
				.newTabSpec("Tipo de Salvado")
				.setIndicator("", ressources.getDrawable(R.drawable.icon_save))
				.setContent(intentSalvado);
		
		tabHost.addTab(tabSpecCaptura);
		tabHost.addTab(tabSpecOperacion);
		tabHost.addTab(tabSpecReporte);
		tabHost.addTab(tabSpecTipoSalvado);
		
		tabHost.setCurrentTab(0);
	}
	
	private static void setTab(int i) {
		tabHost.setCurrentTab(i);
	}
	
	public static void setTabCaptura() {
		setTab(0);
	}
	
	public static int getRepeticiones() {
		return repeticiones;
	}
	
	public static void setDispositivoSalvado(IAlmacenemiento alma) {
		DispositivoSalvado = alma;
	}
	
	public static IAlmacenemiento getDispositivoSalvado() {
		return DispositivoSalvado;
	}
	
	//Esto es para usar multi idioma.
	public static String BuscarTexto(int a) {
		switch (a) {
		case 1:
			return Carpeta;
		case 2:
			return CrearDirSD;
		case 3:
			return NoDisSD;
		case 4:
			return CrearDirInterno;
		case 5:
			return NombreHoja;
		default:
			return "";
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.Abrir:
				Abrir();
				return true;
			case R.id.SalvarMenu:
				Salvar();
				return true;
			case R.id.About:
				Acerca();
				return true;
			case R.id.menu_settings:
				Configurar();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
    protected void onSaveInstanceState(Bundle save) {
		super.onSaveInstanceState(save);
		//Debo guardar el dispositivo de salvado y sus configuraciones.
	}
	
	@Override
    protected void onRestoreInstanceState(Bundle saved) {
        super.onRestoreInstanceState(saved);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Manejador.getInstancia().close();
//		try {
//			//Debo verificar esto
//			DispositivoSalvado.CerrarArchivo();
//		} catch (WriteException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	@Override
	public void MostrarTostada(final String tostada) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), tostada, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
}