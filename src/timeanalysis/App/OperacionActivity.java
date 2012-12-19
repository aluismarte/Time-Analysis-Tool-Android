package timeanalysis.App;

import java.util.ArrayList;
import java.util.List;

import timeanalysis.App.DB.ManejadorOperacion;
import timeanalysis.App.DB.ManejadorOperadorDescripcion;
import timeanalysis.App.DB.Modelos.OperacionDescripcionModelo;
import timeanalysis.App.DB.Modelos.OperacionModelo;
import timeanalysis.App.Interfaces.ITostadas;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OperacionActivity extends Activity implements ITostadas {
	
	private List<OperacionModelo> operaciones = new ArrayList<OperacionModelo>();
	private List<OperacionDescripcionModelo> elementos = new ArrayList<OperacionDescripcionModelo>();
	private List<String> Operacionesvalues = new ArrayList<String>();
	private List<String> Elementosvalues = new ArrayList<String>();
	private static ListView listView;
	private static ListView listViewElemento;
	private EditText tex;
	private EditText texElemento;
	private ArrayAdapter<String> adapterOperaciones;
	private ArrayAdapter<String> adapterElementos;
	private int IDOperacionElemento;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_operacion);
		
		LlenarModeloOperaciones();
		
		PrepararTextEdit();
		PrepararLista();
	}
	
	public void PrepararTextEdit() {
		tex = (EditText) findViewById(R.id.editText1);
	}
	
	public void PrepararLista() {
		listView = (ListView) findViewById(R.id.ListaOperaciones);
		adapterOperaciones = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Operacionesvalues);
		listView.setAdapter(adapterOperaciones);
		listView.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				  if("No hay nada".equals(((TextView) view).getText().toString())) {
					  MostrarTostada("Inserte una operacion.");
				  }else {
					  LlenarModeloElementos(operaciones.get(position));
					  IDOperacionElemento = position;
					  Elemento();
				  }
			  }
		});
	}
	
	public void NuevaOperacion(View view) {
		OperacionModelo lol = new OperacionModelo();
		if(!"".equals(tex.getText())) {
			lol.setNombre(tex.getText().toString());
			ManejadorOperacion.getInstancia().Preparar(lol);
			ManejadorOperacion.getInstancia().Insertar();
			MostrarTostada("Se inserto con exito");
			tex.setText("");
			LlenarModeloOperaciones();
			adapterOperaciones.notifyDataSetChanged();
		}else {
			MostrarTostada("EL campo no puede estar vacio");
		}
	}
	
	public void NuevoElemento(View view) {
		OperacionDescripcionModelo lol = new OperacionDescripcionModelo();
		if(!"".equals(texElemento.getText())) {
			lol.setNombre(texElemento.getText().toString());
			lol.setIdOperacion(operaciones.get(IDOperacionElemento).getId());
			ManejadorOperadorDescripcion.getInstancia().Preparar(lol);
			ManejadorOperadorDescripcion.getInstancia().Insertar();
			MostrarTostada("Se inserto con exito");
			texElemento.setText("");
			OperacionModelo om = operaciones.get(IDOperacionElemento);
			LlenarModeloElementos(om);
			adapterElementos.notifyDataSetChanged();
		}else {
			MostrarTostada("EL campo no puede estar vacio");
		}
	}
	
	public void LlenarModeloOperaciones() {
		BorrarModeloOperacion();
		operaciones = ManejadorOperacion.getInstancia().Listado();
		if(!operaciones.isEmpty()) {
			for(int i = 0; i < operaciones.size(); i++) {
				Operacionesvalues.add(operaciones.get(i).getNombre());
			}
		}else {
			Operacionesvalues.add("No hay nada");
		}
	}
	
	public void LlenarModeloElementos(OperacionModelo om) {
		BorrarModeloElemento();
		OperacionDescripcionModelo odm = new OperacionDescripcionModelo();
		odm.setIdOperacion(om.getId());
		elementos = ManejadorOperadorDescripcion.getInstancia().Buscar(odm);
		if(!elementos.isEmpty()) {
			for(int i = 0; i < elementos.size(); i++) {
				Elementosvalues.add(elementos.get(i).getNombre());
			}
		}else {
			Elementosvalues.add("No hay nada");
		}
	}
	
	public void BorrarModeloOperacion() {
		Operacionesvalues.clear();
		operaciones.clear();
	}
	
	public void BorrarModeloElemento() {
		elementos.clear();
		Elementosvalues.clear();
	}
	
	public void Elemento() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.activity_elemento);
		dialog.setTitle("Elementos");
		
		listViewElemento = (ListView) dialog.findViewById(R.id.ListaElementos);
		adapterElementos = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Elementosvalues);
		listViewElemento.setAdapter(adapterElementos);
		adapterElementos.notifyDataSetChanged();
		
		texElemento = (EditText) dialog.findViewById(R.id.elemento);
		
		Button ButtonOK = (Button) dialog.findViewById(R.id.OK);
		Button ButtonCancel = (Button) dialog.findViewById(R.id.Cancel);
		ButtonOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NuevoElemento(v);
			}
		});
		ButtonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
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