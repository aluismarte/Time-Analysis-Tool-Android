package timeanalysis.App;

import timeanalysis.App.Adaptadores.AdaptadorCronometro;
import timeanalysis.App.Adaptadores.AdaptadorDatos;
import timeanalysis.App.Interfaces.ITostadas;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class CapturaActivity extends Activity implements ITostadas {
	
	//Manejar cada columna a crear y hacer scroll
	//private String[] Header = new String[] {"Ciclo","Op1","Op2","Op.."};
	private String[] Datos = new String[] {"a","b"};
	private GridView gridView;
	private AdaptadorCronometro crono1;
	private AdaptadorCronometro crono2;
	private Button SelecIniciar;
	private Button Vuelta;
	private Button Cancelar;
	
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
		
		AdaptadorDatos adapter = new AdaptadorDatos(this, Datos);
		
		gridView.setAdapter(adapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
				Toast.makeText(getApplicationContext(),((TextView) v.findViewById(R.id.grid_item_label)).getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void PrepararCronometro() {
		crono1 = (AdaptadorCronometro) findViewById(R.id.chronometer1);;
		crono2 = (AdaptadorCronometro) findViewById(R.id.chronometer2);;
		
		//crono1.start();
		//crono2.start();
	}
	
	public void BotonSelecIniciar(View view) {
		if("Seleccionar".equals(SelecIniciar.getText())) {
			MostrarTostada("Seleccione un proceso");
			//Hago el proceso de hacer una seleccion de la operacion.
			SelecIniciar.setText(getString(R.string.Iniciar));
			Vuelta.setEnabled(true);
			Cancelar.setEnabled(true);
		}else {
			//Inicio el Cronometro
			crono1.setBase(SystemClock.elapsedRealtime());
			crono2.setBase(SystemClock.elapsedRealtime());
			crono1.start();
			crono2.start();
			SelecIniciar.setEnabled(false);
		}
	}
	
	public void BotonVuelta(View view) {
		if("Iniciar".equals(SelecIniciar.getText())) {
			//crono2.getText();
			MostrarTostada("Una Captura");
		}
	}
	
	public void BotonCancelar(View view) {
		if("Iniciar".equals(SelecIniciar.getText())) {
			//crono2.getText();
			crono1.stop();
			crono2.stop();
			crono1.setBase(SystemClock.elapsedRealtime());
			crono2.setBase(SystemClock.elapsedRealtime());
			SelecIniciar.setText(getString(R.string.Seleccionar));
			SelecIniciar.setEnabled(true);
			Vuelta.setEnabled(false);
			Cancelar.setEnabled(false);
			MostrarTostada("Cancele");
		}
	}
	
	public String[] getDatos() {
		return this.Datos;
	}
	
	public void setDatos(String dat[]) {
		this.Datos = dat;
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