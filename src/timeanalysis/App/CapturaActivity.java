package timeanalysis.App;

import java.util.ArrayList;
import java.util.List;

import timeanalysis.App.Adaptadores.AdaptadorCronometro;
import timeanalysis.App.Interfaces.ITostadas;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class CapturaActivity extends Activity implements ITostadas {
	
	private List<String> Datos = new ArrayList<String>();
	private GridView gridView;
	private AdaptadorCronometro crono1;
	private AdaptadorCronometro crono2;
	private ArrayAdapter<String> adapter;
	private Button SelecIniciar;
	private Button Vuelta;
	private Button Cancelar;
	private Long TiempoPausa1 = (long) 0;
	private Long TiempoPausa2 = (long) 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_captura);
		
		PrepararBotones();
		PrepararCronometro();
		PrepararView();
	}
	
	public void PrepararBotones() {
		SelecIniciar = (Button) findViewById(R.id.Start);
		Vuelta = (Button) findViewById(R.id.Captura);
		Cancelar = (Button) findViewById(R.id.Cancel);
		Vuelta.setEnabled(false);
		Cancelar.setEnabled(false);
	}
	
	public void PrepararView() {
		gridView = (GridView) findViewById(R.id.capturaDatos);
		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Datos);
		
		gridView.setAdapter(adapter);
		gridView.setNumColumns(4);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
			   MostrarTostada(((TextView) v).getText().toString());
			}
		});
	}
	
	public void PrepararCronometro() {
		crono1 = (AdaptadorCronometro) findViewById(R.id.chronometer1);;
		crono2 = (AdaptadorCronometro) findViewById(R.id.chronometer2);;
	}
	
	public void BotonSelecIniciar(View view) {
		if((getString(R.string.Seleccionar).toString()).equals(SelecIniciar.getText())) {
			MostrarTostada("Seleccione un proceso");
			//Hago el proceso de hacer una seleccion de la operacion.
			SelecIniciar.setText(getString(R.string.Iniciar).toString());
			Vuelta.setEnabled(true);
			Cancelar.setEnabled(true);
		}else if((getString(R.string.Iniciar).toString()).equals(SelecIniciar.getText())) {
			crono1.setBase(SystemClock.elapsedRealtime());
			crono2.setBase(SystemClock.elapsedRealtime());
			crono1.start();
			crono2.start();
			SelecIniciar.setText(getString(R.string.Pausar).toString());
		}else if((getString(R.string.Pausar).toString()).equals(SelecIniciar.getText())) {
			TiempoPausa1 = crono1.getBase() - SystemClock.elapsedRealtime();
			crono1.stop();
			TiempoPausa2 = crono2.getBase() - SystemClock.elapsedRealtime();
			crono2.stop();
			SelecIniciar.setText(getString(R.string.Continuar).toString());
		}else if((getString(R.string.Continuar).toString()).equals(SelecIniciar.getText())) {
			crono1.setBase(SystemClock.elapsedRealtime() + TiempoPausa1);
			crono1.start();
			crono2.setBase(SystemClock.elapsedRealtime() + TiempoPausa2);
			crono2.start();
			SelecIniciar.setText(getString(R.string.Pausar).toString());
		}
	}
	
	public void BotonVuelta(View view) {
		if((getString(R.string.Pausar).toString()).equals(SelecIniciar.getText())) {
			InsertarValor(crono2.getText().toString());
			crono2.setBase(SystemClock.elapsedRealtime());
			adapter.notifyDataSetChanged();
		}else {
			MostrarTostada(getString(R.string.NoSalvarDatos).toString());
		}
	}
	
	public void BotonCancelar(View view) {
		if((getString(R.string.Pausar).toString()).equals(SelecIniciar.getText()) 
				|| (getString(R.string.Continuar).toString()).equals(SelecIniciar.getText()) 
				|| (getString(R.string.Iniciar).toString()).equals(SelecIniciar.getText())) {
			crono1.stop();
			crono2.stop();
			crono1.setBase(SystemClock.elapsedRealtime());
			TiempoPausa1 = (long) 0;
			crono2.setBase(SystemClock.elapsedRealtime());
			TiempoPausa2 = (long) 0;
			SelecIniciar.setText(getString(R.string.Seleccionar).toString());
			SelecIniciar.setEnabled(true);
			Vuelta.setEnabled(false);
			Cancelar.setEnabled(false);
			BorrarModelo();
		}
	}
	
	public void InsertarValor(String dat) {
		Datos.add(dat);
	}
	
	public void BorrarModelo() {
		if(!Datos.isEmpty()) {
			Datos.clear();
			adapter.notifyDataSetChanged();
		}
	}
	
	public List<String> getDatos() {
		return Datos;
	}
	
	public void setDatos(List<String> dat) {
		Datos = dat;
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