package timeanalysis.App;

import timeanalysis.App.Adaptadores.AdaptadorDatos;
import android.app.TabActivity;
import android.content.res.Resources;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

/*
Aqui se elige el tipo de salvado
*/

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	
	private GridView gridView;
	
	private String[] Datos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_captura);
		
		//Preparo tabs a la antigua
		
		
		//Visor de la data;
		PrepararView();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void PrepararView() {
		gridView = (GridView) findViewById(R.id.capturaDatos);
		
		AdaptadorDatos adapter = new AdaptadorDatos(this, Datos);
		
		gridView.setAdapter(adapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
				Toast.makeText(getApplicationContext(),((TextView) v.findViewById(R.id.grid_item_label)).getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void PrepararTabs() {
		Resources ressources = getResources(); 
		TabHost tabHost = getTabHost();
		
		Intent intentAndroid = new Intent().setClass(this, OperacionActivity.class);
		
		TabSpec tabSpecAndroid = tabHost
				.newTabSpec("Operaciones")
				.setIndicator("", ressources.getDrawable(R.drawable.))
				.setContent(intentAndroid);
		
		tabHost.setCurrentTab(0);
	}
	
	public String[] getDatos() {
		return this.Datos;
	}
	
	public void setDatos(String dat[]) {
		this.Datos = dat;
	}
	
}