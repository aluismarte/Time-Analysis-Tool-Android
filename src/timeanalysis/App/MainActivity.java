package timeanalysis.App;

import java.util.List;

import timeanalysis.App.Interfaces.IAlmacenemiento;
import timeanalysis.App.Tools.Interno;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/*
Aqui se elige el tipo de salvado
*/

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	
	public static Context contexto;
	private List<String> Datos;
	private static IAlmacenemiento DispositivoSalvado;
	private Resources ressources;
	private static TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		contexto = this;
		
		setDispositivoSalvado(new Interno());
		
		//Preparo tabs a la antigua
		//Es posible hacer una implementacion moderna.
		//Solo hay que usar el chequeo de versiones
		PrepararTabs();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void Acerca() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.about).setTitle(R.string.AboutTitulo);
		builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               dialog.dismiss();
	           }
	    });
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

	public List<String> getDatos() {
		return this.Datos;
	}
	
	public void setDatos(List<String> dat) {
		this.Datos = dat;
	}
	
	private static void setTab(int i) {
		tabHost.setCurrentTab(i);
	}
	
	public static void setTabCaptura() {
		setTab(0);
	}
	
	public static void setDispositivoSalvado(IAlmacenemiento alma) {
		DispositivoSalvado = alma;
	}
	
	public static IAlmacenemiento getDispositivoSalvado() {
		return DispositivoSalvado;
	}
	
}