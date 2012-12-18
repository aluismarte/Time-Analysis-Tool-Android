package timeanalysis.App;

import timeanalysis.App.Interfaces.IAlmacenemiento;
import android.app.TabActivity;
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
	
	private String[] Datos;
	private static IAlmacenemiento DispositivoSalvado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Preparo tabs a la antigua
		//Es posible hacer una implementacion moderna.
		//Solo hay que usar el chequeo de versiones
		PrepararTabs();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void PrepararTabs() {
		Resources ressources = getResources(); 
		TabHost tabHost = getTabHost();
		
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
		
		Intent intentSalvado = new Intent().setClass(this, SalvadoActivity.class);
		
		TabSpec tabSpecTipoSalvado = tabHost
				.newTabSpec("Tipo de Salvado")
				.setIndicator("", ressources.getDrawable(R.drawable.ic_launcher))
				.setContent(intentSalvado);
		
		tabHost.addTab(tabSpecCaptura);
		tabHost.addTab(tabSpecOperacion);
		tabHost.addTab(tabSpecTipoSalvado);
		
		tabHost.setCurrentTab(0);
	}

	public String[] getDatos() {
		return this.Datos;
	}
	
	public void setDatos(String dat[]) {
		this.Datos = dat;
	}
	
	public void setDispositivoSalvado(IAlmacenemiento alma) {
		DispositivoSalvado = alma;
	}
	
	public IAlmacenemiento getDispositivoSalvado() {
		return DispositivoSalvado;
	}
	
}